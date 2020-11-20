// 
// Decompiled by Procyon v0.5.36
// 

package com.yikecited.clipbrd;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build$VERSION;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.PowerManager;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.text.ClipboardManager;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;

import java.lang.ref.WeakReference;

import javax.crypto.BadPaddingException;

public class ClipbrdService extends Service
{
    private static final String[] OLD_NOTIFICATION_CHANNEL_IDS;
    private final BroadcastReceiver mBroadcastReceiver;
    private ClipboardListener mCL;
    private MonitorHandler mMH;
    private String mOldClipboard;
    
    static {
        OLD_NOTIFICATION_CHANNEL_IDS = new String[] { "clipbrd_main" };
    }
    
    public ClipbrdService() {
        this.mOldClipboard = null;
        this.mBroadcastReceiver = new BroadcastReceiver() {
            private void handleWrongPassword(final Context context) {
                Utils.forgetCredentials(context);
                ClipbrdService.showSignInAgainNotification(context);
            }
            
            public void onReceive(final Context context, final Intent intent) {
                if (intent.getAction().equals("com.clipbrd.action.INCOMING_CLIPBOARD")) {
                    final SharedPreferences config = Utils.getConfig(context);
                    if (!config.getBoolean("enabled", false)) {
                        Log.v("ClipbrdService", "C2D: got clipboard, but sync is disabled - skipping.");
                    }
                    else {
                        final String stringExtra = intent.getStringExtra("deviceId");
                        if (stringExtra != null) {
                            final String token = FirebaseInstanceId.getInstance().getToken();
                            if (token != null && token.equals(stringExtra)) {
                                Log.w("ClipbrdService", "C2D: received own clipboard - skipping");
                                return;
                            }
                        }
                        final String stringExtra2 = intent.getStringExtra("iv");
                        final String stringExtra3 = intent.getStringExtra("ct");
                        if (stringExtra2 == null) {
                            Log.e("ClipbrdService", "no_iv");
                            return;
                        }
                        if (stringExtra3 == null) {
                            Log.e("ClipbrdService", "no_ct");
                            return;
                        }
                        final String string = config.getString("crypto_key", (String)null);
                        if (string == null) {
                            Log.e("ClipbrdService", "no_crypto_key");
                            return;
                        }
                        String decrypt;
                        try {
                            decrypt = Crypto.decrypt(string, stringExtra2, stringExtra3);
                            if (decrypt == null) {
                                Log.e("ClipbrdService", "C2D: both ciphertext and plaintext failed, can't process clipboard sync");
                                Log.e("ClipbrdService", intent.toString());
                                return;
                            }
                        }
                        catch (BadPaddingException ex) {
                            Log.w("ClipbrdService", ex.toString());
                            this.handleWrongPassword(context);
                            return;
                        }
                        catch (Exception ex2) {
                            Log.e("ClipbrdService", ex2.toString());
                            return;
                        }
                        Log.i("ClipbrdService", "C2D: got clipboard (" + decrypt.length() + ") chars.");
                        if (ClipbrdService.this.mOldClipboard != null && ClipbrdService.this.mOldClipboard.equals(decrypt)) {
                            Log.w("ClipbrdService", "C2D: incoming clipboard is identical to local clipboard, ignoring");
                            return;
                        }
                        ((ClipboardManager)ClipbrdService.this.getSystemService("clipboard")).setText((CharSequence)decrypt);
                        ClipbrdService.this.mOldClipboard = decrypt;
                        final SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(ClipbrdService.this.getApplicationContext());
                        if (!defaultSharedPreferences.getBoolean("notification_enabled", true)) {
                            Log.v("ClipbrdService", "Skipping notifications - disabled");
                            return;
                        }
                        if (!((PowerManager)context.getSystemService("power")).isScreenOn()) {
                            Log.v("ClipbrdService", "Skipping notifications - screen is off");
                            return;
                        }
                        if (defaultSharedPreferences.getBoolean("notification_toast", true)) {
                            Log.v("ClipbrdService", "Using toast notification");
                            Toast.makeText(context, (CharSequence)String.format(context.getString(2131361860), decrypt.length()), 0).show();
                        }
                        else {
                            Log.v("ClipbrdService", "Skipping toast notification - disabled");
                        }
                        final String string2 = defaultSharedPreferences.getString("notification_ringtone", "content://settings/system/notification_sound");
                        if (!string2.equals("")) {
                            Log.v("ClipbrdService", "Using audio notification");
                            new PlayRingtoneTask().execute((Object[])new Ringtone[] { RingtoneManager.getRingtone(ClipbrdService.this.getApplicationContext(), Uri.parse(string2)) });
                        }
                        else {
                            Log.v("ClipbrdService", "Skipping audio notification - silent ringtone");
                        }
                        if (defaultSharedPreferences.getBoolean("notification_vibrate", true)) {
                            Log.v("ClipbrdService", "Using vibrator notification");
                            ((Vibrator)ClipbrdService.this.getSystemService("vibrator")).vibrate(100L);
                            return;
                        }
                        Log.v("ClipbrdService", "Skipping vibrator notification - disabled");
                    }
                }
            }
        };
    }
    
    private void prepareAndStartForeground() {
        if (Build$VERSION.SDK_INT >= 26) {
            final NotificationManager notificationManager = (NotificationManager)this.getSystemService("notification");
            if (notificationManager != null) {
                final String[] old_NOTIFICATION_CHANNEL_IDS = ClipbrdService.OLD_NOTIFICATION_CHANNEL_IDS;
                for (int length = old_NOTIFICATION_CHANNEL_IDS.length, i = 0; i < length; ++i) {
                    notificationManager.deleteNotificationChannel(old_NOTIFICATION_CHANNEL_IDS[i]);
                }
                final NotificationChannel notificationChannel = new NotificationChannel("background", (CharSequence)this.getString(2131361794), 2);
                notificationChannel.enableLights(false);
                notificationChannel.enableVibration(false);
                notificationManager.createNotificationChannel(notificationChannel);
                final PendingIntent activity = PendingIntent.getActivity((Context)this, 0, new Intent((Context)this, (Class)MainActivity.class), 0);
                final Intent intent = new Intent((Context)this, (Class)MainActivity.class);
                intent.setAction("STOP");
                this.startForeground(42, new NotificationCompat.Builder((Context)this, "background").setSmallIcon(2131034146).setContentTitle(this.getString(2131361800)).setContentText(this.getString(2131361799)).setPriority(-1).setContentIntent(activity).addAction(new NotificationCompat.Action.Builder(17301539, this.getString(2131361792), PendingIntent.getActivity((Context)this, 0, intent, 0)).build()).build());
                return;
            }
            Log.e("ClipbrdService", "Could not get NotificationManager system service");
        }
    }
    
    public static void showSignInAgainNotification(final Context context) {
        final NotificationCompat.Builder setContentText = new NotificationCompat.Builder(context).setAutoCancel(true).setSmallIcon(2131034133).setContentTitle(context.getString(2131361868)).setContentText(context.getString(2131361857));
        final Intent intent = new Intent(context, (Class)SignInActivity.class);
        final TaskStackBuilder create = TaskStackBuilder.create(context);
        create.addParentStack(SignInActivity.class);
        create.addNextIntent(intent);
        setContentText.setContentIntent(create.getPendingIntent(0, 134217728));
        ((NotificationManager)context.getSystemService("notification")).notify(42, setContentText.build());
    }
    
    public IBinder onBind(final Intent intent) {
        return null;
    }
    
    public void onCreate() {
        Log.d("ClipbrdService", "onCreate()");
        this.mCL = new ClipboardListener((Context)this);
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.clipbrd.action.INCOMING_CLIPBOARD");
        this.registerReceiver(this.mBroadcastReceiver, intentFilter);
        (this.mMH = new MonitorHandler(this)).sendEmptyMessageDelayed(0, 250L);
        Log.d("ClipbrdService", "Starting MonitorHandler");
        this.prepareAndStartForeground();
    }
    
    public void onDestroy() {
        Log.d("ClipbrdService", "onDestroy()");
        this.unregisterReceiver(this.mBroadcastReceiver);
        this.mMH.removeMessages(0);
    }
    
    private static class MonitorHandler extends Handler
    {
        private final WeakReference<ClipbrdService> mService;
        
        MonitorHandler(final ClipbrdService referent) {
            this.mService = new WeakReference<ClipbrdService>(referent);
        }
        
        private void tick() {
            final ClipbrdService clipbrdService = this.mService.get();
            if (clipbrdService == null) {
                Log.e("MonitorHandler", "ClipbrdService is null");
            }
            else {
                final ClipboardManager clipboardManager = (ClipboardManager)clipbrdService.getSystemService("clipboard");
                if (clipboardManager == null) {
                    Log.e("MonitorHandler", "ClipboardManager is null");
                    return;
                }
                CharSequence text;
                try {
                    text = clipboardManager.getText();
                    if (text == null) {
                        Log.w("MonitorHandler", "clipboardChars is null");
                        return;
                    }
                }
                catch (Exception ex) {
                    Log.e("MonitorHandler", "could not get clipboard text", (Throwable)ex);
                    return;
                }
                final String string = text.toString();
                if (string.equals("")) {
                    Log.w("MonitorHandler", "newClipboard is an empty string");
                    return;
                }
                if (clipbrdService.mOldClipboard == null) {
                    Log.e("MonitorHandler", "mOldClipboard is null");
                    clipbrdService.mOldClipboard = string;
                    return;
                }
                if (!string.equals(clipbrdService.mOldClipboard)) {
                    clipbrdService.mOldClipboard = string;
                    clipbrdService.mCL.onPrimaryClipChanged();
                }
            }
        }
        
        public void dispatchMessage(final Message message) {
            this.tick();
            this.sendEmptyMessageDelayed(0, 250L);
        }
    }
}
