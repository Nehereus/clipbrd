// 
// Decompiled by Procyon v0.5.36
// 

package com.yikecited.clipbrd;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface$OnDismissListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences$Editor;
import android.os.Build$VERSION;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View$OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.common.GoogleApiAvailability;
import com.google.firebase.iid.FirebaseInstanceId;

public class MainActivity extends Activity
{
    private boolean _isSignedIn() {
        final SharedPreferences config = Utils.getConfig((Context)this);
        return config.getString("email", (String)null) != null && config.getString("hash", (String)null) != null;
    }
    
    private boolean checkPlayServices(final boolean b) {
        final GoogleApiAvailability instance = GoogleApiAvailability.getInstance();
        final int googlePlayServicesAvailable = instance.isGooglePlayServicesAvailable((Context)this);
        if (!b) {
            if (googlePlayServicesAvailable != 0) {
                return false;
            }
        }
        else if (googlePlayServicesAvailable != 0) {
            if (instance.isUserResolvableError(googlePlayServicesAvailable)) {
                final Dialog errorDialog = instance.getErrorDialog(this, googlePlayServicesAvailable, 9000);
                errorDialog.setOnDismissListener((DialogInterface$OnDismissListener)new DialogInterface$OnDismissListener() {
                    public void onDismiss(final DialogInterface dialogInterface) {
                        MainActivity.this.finish();
                    }
                });
                errorDialog.show();
            }
            else {
                Log.e("MainActivity", "Unsupported device");
            }
            return false;
        }
        return true;
    }
    
    private void disableSync() {
        Log.d("MainActivity", "disableSync()");
        final SharedPreferences$Editor edit = Utils.getConfig((Context)this).edit();
        edit.putBoolean("enabled", false);
        edit.commit();
        this.stopClipbrdService();
    }
    
    private void enableSync() {
        Log.d("MainActivity", "enableSync()");
        final SharedPreferences$Editor edit = Utils.getConfig((Context)this).edit();
        edit.putBoolean("enabled", true);
        edit.commit();
        this.startClipbrdService();
    }
    
    private void startClipbrdService() {
        final Intent intent = new Intent((Context)this, (Class)ClipbrdService.class);
        ComponentName componentName;
        if (Build$VERSION.SDK_INT >= 26) {
            Log.d("MainActivity", "startForegroundService()");
            componentName = this.startForegroundService(intent);
        }
        else {
            Log.d("MainActivity", "startService()");
            componentName = this.startService(intent);
        }
        if (componentName == null) {
            Log.e("MainActivity", "Failed to start ClipbrdService");
        }
    }
    
    private void stopClipbrdService() {
        this.stopService(new Intent((Context)this, (Class)ClipbrdService.class));
    }
    
    private void updateInterfaceAndService() {
        final SharedPreferences config = Utils.getConfig((Context)this);
        final TextView textView = (TextView)this.findViewById(2131099711);
        final ToggleButton toggleButton = (ToggleButton)this.findViewById(2131099661);
        textView.setText((CharSequence)config.getString("email", (String)null));
        final boolean boolean1 = config.getBoolean("enabled", false);
        toggleButton.setChecked(boolean1);
        if (boolean1) {
            this.startClipbrdService();
            return;
        }
        this.stopClipbrdService();
    }
    
    protected void onActivityResult(final int n, final int n2, final Intent intent) {
        Log.d("MainActivity", "onActivityResult");
        if (n == 1) {
            if (n2 != 0) {
                this.updateInterfaceAndService();
                return;
            }
            this.finish();
        }
    }
    
    public void onCreate(final Bundle bundle) {
        super.onCreate(bundle);
        if (!this.checkPlayServices(false)) {
            return;
        }
        this.setContentView(2131230723);
        ((ToggleButton)this.findViewById(2131099661)).setOnClickListener((View$OnClickListener)new View$OnClickListener() {
            public void onClick(final View view) {
                MainActivity.this.toggleMasterSwitch(view);
            }
        });
        ((Button)this.findViewById(2131099664)).setOnClickListener((View$OnClickListener)new View$OnClickListener() {
            public void onClick(final View view) {
                MainActivity.this.signout();
            }
        });
    }
    
    public boolean onCreateOptionsMenu(final Menu menu) {
        this.getMenuInflater().inflate(2131296256, menu);
        return true;
    }
    
    protected void onNewIntent(final Intent intent) {
        super.onNewIntent(intent);
        Log.d("MainActivity", "onNewIntent");
        final String action = intent.getAction();
        if (action != null && action.equals("STOP")) {
            this.disableSync();
            this.finish();
        }
    }
    
    public boolean onOptionsItemSelected(final MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            default: {
                return super.onOptionsItemSelected(menuItem);
            }
            case 2131099686: {
                this.startActivity(new Intent((Context)this, (Class)SettingsActivity.class));
                return true;
            }
            case 2131099685: {
                this.startActivity(new Intent((Context)this, (Class)ChangePasswordActivity.class));
                return true;
            }
        }
    }
    
    public void onResume() {
        super.onResume();
        Log.d("MainActivity", "onResume");
        if (!this.checkPlayServices(true)) {
            return;
        }
        if (!this._isSignedIn()) {
            this.startActivityForResult(new Intent((Context)this, (Class)SplashActivity.class), 1);
            return;
        }
        this.updateInterfaceAndService();
    }
    
    public void signout() {
        final Credentials credentials = Utils.getCredentials((Context)this);
        final SharedPreferences$Editor edit = Utils.getConfig((Context)this).edit();
        edit.remove("email");
        edit.remove("crypto_key");
        edit.remove("hash");
        edit.commit();
        this.disableSync();
        if (credentials == null) {
            Log.w("MainActivity", "No credentials - not sure why we're trying to sign out :)");
            return;
        }
        new DeviceUnregisterTask((Context)this, new AsyncTaskCompleteListener<String>() {
            final /* synthetic */ ProgressDialog val$progressDialog = ProgressDialog.show((Context)this, (CharSequence)"", (CharSequence)MainActivity.this.getString(2131361874), true);
            
            @Override
            public void onTaskComplete(final String str) {
                if (this.val$progressDialog.isShowing()) {
                    this.val$progressDialog.dismiss();
                }
                if (str == null) {
                    Toast.makeText((Context)MainActivity.this, (CharSequence)"Signed out", 1).show();
                    return;
                }
                Toast.makeText((Context)MainActivity.this, (CharSequence)("Error: " + str), 1).show();
            }
        }).execute(new Object[] { credentials, FirebaseInstanceId.getInstance().getToken() });
        this.onResume();
    }
    
    public void toggleMasterSwitch(final View view) {
        if (((ToggleButton)view).isChecked()) {
            this.enableSync();
            return;
        }
        this.disableSync();
    }
}
