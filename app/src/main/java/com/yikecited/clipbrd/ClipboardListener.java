// 
// Decompiled by Procyon v0.5.36
// 

package com.yikecited.clipbrd;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.ClipboardManager;
import android.util.Log;
import android.widget.Toast;

public class ClipboardListener
{
    private Context mContext;
    
    public ClipboardListener(final Context mContext) {
        this.mContext = mContext;
    }
    
    public void onPrimaryClipChanged() {
        Log.d("ClipboardListener", "onPrimaryClipChanged()");
        final SharedPreferences config = Utils.getConfig(this.mContext);
        if (!config.getBoolean("enabled", false)) {
            Log.v("ClipboardListener", "Sync is disabled - skipping clipboard push.");
        }
        else {
            if (Utils.getCredentials(this.mContext) == null) {
                Log.v("ClipboardListener", "Not logged in - skipping clipboard push.");
                return;
            }
            final CharSequence text = ((ClipboardManager)this.mContext.getSystemService("clipboard")).getText();
            if (text != null) {
                final String string = text.toString();
                Log.i("ClipboardListener", "D2C: got clipboard (" + string.length() + ") chars.");
                String string2 = string;
                if (string.length() > 1000) {
                    Log.w("ClipboardListener", "D2C: truncating clipboard to 1000 chars.");
                    string2 = string.substring(0, 997) + "...";
                }
                new PushClipboardTask(this.mContext, new AsyncTaskCompleteListener<Integer>() {
                    @Override
                    public void onTaskComplete(final Integer n) {
                        if (n >= 0) {
                            Toast.makeText(ClipboardListener.this.mContext, (CharSequence)String.format(ClipboardListener.this.mContext.getString(2131361864), string2.length()), 0).show();
                            return;
                        }
                        switch (n) {
                            default: {
                                Toast.makeText(ClipboardListener.this.mContext, (CharSequence)"ERROR: Unknown (check logs).", 0).show();
                            }
                            case -5: {}
                            case -1: {
                                Toast.makeText(ClipboardListener.this.mContext, (CharSequence)"ERROR: No Registration ID.", 0).show();
                            }
                            case -2: {
                                Toast.makeText(ClipboardListener.this.mContext, (CharSequence)"ERROR: Not signed in.", 0).show();
                            }
                            case -3: {
                                Toast.makeText(ClipboardListener.this.mContext, (CharSequence)"ERROR: Please sign into Clipbrd.", 1).show();
                                Utils.forgetCredentials(ClipboardListener.this.mContext);
                                ClipbrdService.showSignInAgainNotification(ClipboardListener.this.mContext);
                            }
                        }
                    }
                }).execute(new Object[] { Crypto.encrypt(config.getString("crypto_key", (String)null), string2) });
            }
        }
    }
}
