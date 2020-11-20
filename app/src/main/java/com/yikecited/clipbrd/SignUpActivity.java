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
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.View$OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class SignUpActivity extends Activity
{
    private static boolean isValidEmail(final String input) {
        return !TextUtils.isEmpty((CharSequence)input) && Patterns.EMAIL_ADDRESS.matcher(input).matches();
    }
    
    protected void _showError(final String message) {
        new AlertDialog$Builder((Context)this).setTitle(2131361831).setMessage((CharSequence)message).setPositiveButton(2131361801, (DialogInterface$OnClickListener)new DialogInterface$OnClickListener() {
            public void onClick(final DialogInterface dialogInterface, final int n) {
                dialogInterface.dismiss();
            }
        }).create().show();
    }
    
    public void onCreate(final Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(2131230725);
        ((Button)this.findViewById(2131099665)).setOnClickListener((View$OnClickListener)new View$OnClickListener() {
            public void onClick(final View view) {
                SignUpActivity.this.signup();
            }
        });
        ((Button)this.findViewById(2131099659)).setOnClickListener((View$OnClickListener)new View$OnClickListener() {
            public void onClick(final View view) {
                SignUpActivity.this.setResult(0);
                SignUpActivity.this.finish();
            }
        });
    }
    
    protected void onCredentials(final Credentials credentials) {
        new SignUpTask((Context)this, new AsyncTaskCompleteListener<String>() {
            final /* synthetic */ ProgressDialog val$progressDialog = ProgressDialog.show((Context)this, (CharSequence)"", (CharSequence)SignUpActivity.this.getString(2131361875), true);
            
            @Override
            public void onTaskComplete(final String s) {
                this.val$progressDialog.cancel();
                if (s != null) {
                    SignUpActivity.this._showError(s);
                    return;
                }
                SignUpActivity.this.onSignup(credentials);
            }
        }).execute(new Object[] { credentials });
    }
    
    protected void onGenerateCryptoKey(final String s, final String s2, final ProgressDialog progressDialog) {
        new GenerateHashTask((Context)this, new AsyncTaskCompleteListener<String>() {
            @Override
            public void onTaskComplete(final String s) {
                progressDialog.cancel();
                final Credentials credentials = new Credentials(s, s);
                credentials.setKey(s2);
                SignUpActivity.this.onCredentials(credentials);
            }
        }).execute(new Object[] { s, s2 });
    }
    
    protected void onRegistrationId(final Credentials credentials, final String s) {
        if (s == null) {
            this._showError("Error signing in. Please try again later.");
            return;
        }
        new DeviceRegisterTask((Context)this, new AsyncTaskCompleteListener<String>() {
            final /* synthetic */ ProgressDialog val$progressDialog = ProgressDialog.show((Context)this, (CharSequence)"", (CharSequence)SignUpActivity.this.getString(2131361873), true);
            
            @Override
            public void onTaskComplete(final String s) {
                this.val$progressDialog.cancel();
                if (s != null) {
                    SignUpActivity.this._showError(s);
                    return;
                }
                SignUpActivity.this.onSigninSuccess(credentials);
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
    
    protected void onSignup(final Credentials credentials) {
        new WaitForRegistrationIdTask((Context)this, new AsyncTaskCompleteListener<String>() {
            final /* synthetic */ ProgressDialog val$progressDialog = ProgressDialog.show((Context)this, (CharSequence)"", (CharSequence)SignUpActivity.this.getString(2131361820), true);
            
            @Override
            public void onTaskComplete(final String s) {
                this.val$progressDialog.cancel();
                SignUpActivity.this.onRegistrationId(credentials, s);
            }
        }).execute((Object[])new Void[0]);
    }
    
    protected void signup() {
        final EditText editText = (EditText)this.findViewById(2131099701);
        final EditText editText2 = (EditText)this.findViewById(2131099702);
        final String trim = editText.getText().toString().trim();
        final String trim2 = editText2.getText().toString().trim();
        editText2.setText((CharSequence)"");
        boolean b = true;
        if (trim.length() < 1) {
            editText.setError((CharSequence)"Email not specified");
            b = false;
        }
        if (!isValidEmail(trim)) {
            editText.setError((CharSequence)"Invalid email");
            b = false;
        }
        if (trim2.length() < 1) {
            editText2.setError((CharSequence)"Password not specified");
            b = false;
        }
        if (!b) {
            return;
        }
        new GenerateCryptoKeyTask((Context)this, new AsyncTaskCompleteListener<String>() {
            final /* synthetic */ ProgressDialog val$progressDialog = ProgressDialog.show((Context)this, (CharSequence)"", (CharSequence)SignUpActivity.this.getString(2131361858), true);
            
            @Override
            public void onTaskComplete(final String s) {
                SignUpActivity.this.onGenerateCryptoKey(trim, s, this.val$progressDialog);
            }
        }).execute(new Object[] { trim, trim2 });
    }
}
