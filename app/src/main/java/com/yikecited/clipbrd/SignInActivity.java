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
import android.content.Intent;
import android.content.SharedPreferences$Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View$OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SignInActivity extends Activity
{
    protected void _showError(final String message) {
        new AlertDialog$Builder((Context)this).setTitle(2131361831).setMessage((CharSequence)message).setPositiveButton(2131361801, (DialogInterface$OnClickListener)new DialogInterface$OnClickListener() {
            public void onClick(final DialogInterface dialogInterface, final int n) {
                dialogInterface.dismiss();
            }
        }).create().show();
    }
    
    public void onCreate(final Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(2131230724);
        ((Button)this.findViewById(2131099663)).setOnClickListener((View$OnClickListener)new View$OnClickListener() {
            public void onClick(final View view) {
                SignInActivity.this.signin();
            }
        });
        ((Button)this.findViewById(2131099659)).setOnClickListener((View$OnClickListener)new View$OnClickListener() {
            public void onClick(final View view) {
                SignInActivity.this.setResult(0);
                SignInActivity.this.finish();
            }
        });
        ((TextView)this.findViewById(2131099673)).setOnClickListener((View$OnClickListener)new View$OnClickListener() {
            public void onClick(final View view) {
                SignInActivity.this.startActivity(new Intent((Context)SignInActivity.this, (Class)HashForgotActivity.class));
            }
        });
    }
    
    protected void onCredentials(final Credentials credentials) {
        new WaitForRegistrationIdTask((Context)this, new AsyncTaskCompleteListener<String>() {
            final /* synthetic */ ProgressDialog val$progressDialog = ProgressDialog.show((Context)this, (CharSequence)"", (CharSequence)SignInActivity.this.getString(2131361873), true);
            
            @Override
            public void onTaskComplete(final String s) {
                SignInActivity.this.onRegistrationId(credentials, s, this.val$progressDialog);
            }
        }).execute((Object[])new Void[0]);
    }
    
    protected void onGenerateCryptoKey(final String s, final String s2, final ProgressDialog progressDialog) {
        new GenerateHashTask((Context)this, new AsyncTaskCompleteListener<String>() {
            @Override
            public void onTaskComplete(final String s) {
                progressDialog.cancel();
                final Credentials credentials = new Credentials(s, s);
                credentials.setKey(s2);
                SignInActivity.this.onCredentials(credentials);
            }
        }).execute(new Object[] { s, s2 });
    }
    
    protected void onRegistrationId(final Credentials credentials, final String s, final ProgressDialog progressDialog) {
        if (s == null) {
            this._showError("Error signing in. Please try again later.");
            return;
        }
        new DeviceRegisterTask((Context)this, new AsyncTaskCompleteListener<String>() {
            @Override
            public void onTaskComplete(final String s) {
                progressDialog.cancel();
                if (s != null) {
                    SignInActivity.this._showError(s);
                    return;
                }
                SignInActivity.this.onSigninSuccess(credentials);
            }
        }).execute(new Object[] { credentials, s });
    }
    
    protected void onSigninSuccess(final Credentials credentials) {
        final SharedPreferences$Editor edit = Utils.getConfig((Context)this).edit();
        edit.putString("email", credentials.getEmail());
        edit.putString("crypto_key", credentials.getKey());
        edit.putString("hash", credentials.getHash());
        edit.putBoolean("enabled", true);
        edit.commit();
        this.setResult(-1);
        this.finish();
    }
    
    protected void signin() {
        final EditText editText = (EditText)this.findViewById(2131099699);
        final EditText editText2 = (EditText)this.findViewById(2131099700);
        final String trim = editText.getText().toString().trim();
        final String trim2 = editText2.getText().toString().trim();
        editText2.setText((CharSequence)"");
        boolean b = true;
        if (trim.length() < 1) {
            editText.setError((CharSequence)this.getString(2131361829));
            b = false;
        }
        if (trim2.length() < 1) {
            editText2.setError((CharSequence)this.getString(2131361852));
            b = false;
        }
        if (!b) {
            return;
        }
        new GenerateCryptoKeyTask((Context)this, new AsyncTaskCompleteListener<String>() {
            final /* synthetic */ ProgressDialog val$progressDialog = ProgressDialog.show((Context)this, (CharSequence)"", (CharSequence)SignInActivity.this.getString(2131361858), true);
            
            @Override
            public void onTaskComplete(final String s) {
                SignInActivity.this.onGenerateCryptoKey(trim, s, this.val$progressDialog);
            }
        }).execute(new Object[] { trim, trim2 });
    }
}
