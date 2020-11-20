// 
// Decompiled by Procyon v0.5.36
// 

package com.yikecited.clipbrd;

import android.app.Activity;
import android.app.AlertDialog$Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface$OnClickListener;
import android.content.SharedPreferences$Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View$OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ChangePasswordActivity extends Activity
{
    protected void _showError(final String message) {
        new AlertDialog$Builder((Context)this).setTitle(2131361831).setMessage((CharSequence)message).setPositiveButton(2131361801, (DialogInterface$OnClickListener)new DialogInterface$OnClickListener() {
            public void onClick(final DialogInterface dialogInterface, final int n) {
                dialogInterface.dismiss();
            }
        }).create().show();
    }
    
    protected void changePassword() {
        final EditText editText = (EditText)this.findViewById(2131099694);
        final EditText editText2 = (EditText)this.findViewById(2131099687);
        final EditText editText3 = (EditText)this.findViewById(2131099688);
        final String trim = editText.getText().toString().trim();
        final String trim2 = editText2.getText().toString().trim();
        final String trim3 = editText3.getText().toString().trim();
        boolean b = true;
        if (trim.equals("")) {
            editText.setError((CharSequence)this.getString(2131361848));
            b = false;
        }
        if (!trim2.equals(trim3)) {
            editText3.setError((CharSequence)this.getString(2131361855));
            b = false;
        }
        if (trim2.equals("")) {
            editText2.setError((CharSequence)this.getString(2131361845));
            b = false;
        }
        if (trim3.equals("")) {
            editText3.setError((CharSequence)this.getString(2131361854));
            b = false;
        }
        if (!b) {
            return;
        }
        final String email = Utils.getCredentials((Context)this).getEmail();
        new GenerateCryptoKeyTask((Context)this, new AsyncTaskCompleteListener<String>() {
            final /* synthetic */ ProgressDialog val$progressDialog = ProgressDialog.show((Context)this, (CharSequence)"", (CharSequence)ChangePasswordActivity.this.getString(2131361858), true);
            
            @Override
            public void onTaskComplete(final String s) {
                ChangePasswordActivity.this.onGenerateOldCryptoKey(email, s, trim2, this.val$progressDialog);
            }
        }).execute(new Object[] { email, trim });
    }
    
    protected void onCreate(final Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(2131230720);
        ((Button)this.findViewById(2131099660)).setOnClickListener((View$OnClickListener)new View$OnClickListener() {
            public void onClick(final View view) {
                ChangePasswordActivity.this.changePassword();
            }
        });
        ((Button)this.findViewById(2131099659)).setOnClickListener((View$OnClickListener)new View$OnClickListener() {
            public void onClick(final View view) {
                ChangePasswordActivity.this.finish();
            }
        });
    }
    
    protected void onGenerateNewCryptoKey(final String s, final String s2, final Credentials credentials, final ProgressDialog progressDialog) {
        new GenerateHashTask((Context)this, new AsyncTaskCompleteListener<String>() {
            @Override
            public void onTaskComplete(final String s) {
                progressDialog.cancel();
                final Credentials credentials = new Credentials(s, s);
                credentials.setKey(s2);
                ChangePasswordActivity.this.onNewCredentials(credentials, credentials);
            }
        }).execute(new Object[] { s, s2 });
    }
    
    protected void onGenerateOldCryptoKey(final String s, final String s2, final String s3, final ProgressDialog progressDialog) {
        new GenerateHashTask((Context)this, new AsyncTaskCompleteListener<String>() {
            @Override
            public void onTaskComplete(final String s) {
                final Credentials credentials = new Credentials(s, s);
                credentials.setKey(s2);
                ChangePasswordActivity.this.onOldCredentials(credentials, s3, progressDialog);
            }
        }).execute(new Object[] { s, s2 });
    }
    
    protected void onNewCredentials(final Credentials credentials, final Credentials credentials2) {
        new ChangePasswordTask((Context)this, new AsyncTaskCompleteListener<String>() {
            final /* synthetic */ ProgressDialog val$progressDialog = ProgressDialog.show((Context)this, (CharSequence)"", (CharSequence)ChangePasswordActivity.this.getString(2131361797), true);
            
            @Override
            public void onTaskComplete(final String s) {
                this.val$progressDialog.cancel();
                if (s != null) {
                    ChangePasswordActivity.this._showError(s);
                    return;
                }
                ChangePasswordActivity.this.onPasswordChanged(credentials);
            }
        }).execute(new Object[] { credentials2, credentials });
    }
    
    protected void onOldCredentials(final Credentials credentials, final String s, final ProgressDialog progressDialog) {
        final String email = credentials.getEmail();
        new GenerateCryptoKeyTask((Context)this, new AsyncTaskCompleteListener<String>() {
            @Override
            public void onTaskComplete(final String s) {
                ChangePasswordActivity.this.onGenerateNewCryptoKey(email, s, credentials, progressDialog);
            }
        }).execute(new Object[] { email, s });
    }
    
    protected void onPasswordChanged(final Credentials credentials) {
        final SharedPreferences$Editor edit = Utils.getConfig((Context)this).edit();
        edit.putString("email", credentials.getEmail());
        edit.putString("crypto_key", credentials.getKey());
        edit.putString("hash", credentials.getHash());
        edit.commit();
        Toast.makeText((Context)this, 2131361853, 0).show();
        this.finish();
    }
}
