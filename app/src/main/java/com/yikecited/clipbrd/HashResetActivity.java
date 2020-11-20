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
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View$OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class HashResetActivity extends Activity
{
    private String email;
    private String token;
    
    private void resetHash() {
        final EditText editText = (EditText)this.findViewById(2131099695);
        final EditText editText2 = (EditText)this.findViewById(2131099696);
        final String trim = editText.getText().toString().trim();
        final String trim2 = editText2.getText().toString().trim();
        boolean b = true;
        if (!trim.equals(trim2)) {
            editText2.setError((CharSequence)this.getString(2131361855));
            b = false;
        }
        if (trim.equals("")) {
            editText.setError((CharSequence)this.getString(2131361845));
            b = false;
        }
        if (trim2.equals("")) {
            editText2.setError((CharSequence)this.getString(2131361854));
            b = false;
        }
        if (!b) {
            return;
        }
        new GenerateCryptoKeyTask((Context)this, new AsyncTaskCompleteListener<String>() {
            final /* synthetic */ ProgressDialog val$progressDialog = ProgressDialog.show((Context)this, (CharSequence)"", (CharSequence)HashResetActivity.this.getString(2131361858), true);
            
            @Override
            public void onTaskComplete(final String s) {
                HashResetActivity.this.onGenerateCryptoKey(s, this.val$progressDialog);
            }
        }).execute(new Object[] { this.email, trim });
    }
    
    protected void onCreate(final Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(2131230722);
        ((Button)this.findViewById(2131099662)).setOnClickListener((View$OnClickListener)new View$OnClickListener() {
            public void onClick(final View view) {
                HashResetActivity.this.resetHash();
            }
        });
        ((Button)this.findViewById(2131099659)).setOnClickListener((View$OnClickListener)new View$OnClickListener() {
            public void onClick(final View view) {
                HashResetActivity.this.finish();
            }
        });
    }
    
    protected void onCredentials(final Credentials credentials) {
        new HashResetTask((Context)this, new AsyncTaskCompleteListener<String>() {
            final /* synthetic */ ProgressDialog val$progressDialog = ProgressDialog.show((Context)this, (CharSequence)"", (CharSequence)HashResetActivity.this.getString(2131361865), true);
            
            @Override
            public void onTaskComplete(final String s) {
                this.val$progressDialog.cancel();
                if (s != null) {
                    HashResetActivity.this.showError(s);
                    return;
                }
                HashResetActivity.this.onSuccess(credentials);
            }
        }).execute(new Object[] { credentials, this.token });
    }
    
    protected void onGenerateCryptoKey(final String s, final ProgressDialog progressDialog) {
        new GenerateHashTask((Context)this, new AsyncTaskCompleteListener<String>() {
            @Override
            public void onTaskComplete(final String s) {
                progressDialog.cancel();
                final Credentials credentials = new Credentials(HashResetActivity.this.email, s);
                credentials.setKey(s);
                HashResetActivity.this.onCredentials(credentials);
            }
        }).execute(new Object[] { this.email, s });
    }
    
    protected void onResume() {
        super.onResume();
        final String fragment = this.getIntent().getData().getFragment();
        if (fragment == null || fragment.equals("")) {
            this.showError(2131361877);
            return;
        }
        final Uri parse = Uri.parse("http://example.com/foo?" + fragment);
        final String queryParameter = parse.getQueryParameter("email");
        final String queryParameter2 = parse.getQueryParameter("token");
        if (queryParameter == null || queryParameter.equals("") || queryParameter2 == null || queryParameter2.equals("")) {
            this.showError(2131361877);
            return;
        }
        this.email = queryParameter;
        this.token = queryParameter2;
    }
    
    protected void onSuccess(final Credentials credentials) {
        final SharedPreferences$Editor edit = Utils.getConfig((Context)this).edit();
        edit.putString("email", credentials.getEmail());
        edit.putString("crypto_key", credentials.getKey());
        edit.putString("hash", credentials.getHash());
        edit.putBoolean("enabled", true);
        edit.commit();
        this.startActivity(new Intent((Context)this, (Class)MainActivity.class));
        this.finish();
    }
    
    protected void showError(final int n) {
        this.showError(this.getString(n));
    }
    
    protected void showError(final String message) {
        new AlertDialog$Builder((Context)this).setMessage((CharSequence)message).setCancelable(false).setPositiveButton(2131361801, (DialogInterface$OnClickListener)new DialogInterface$OnClickListener() {
            public void onClick(final DialogInterface dialogInterface, final int n) {
                HashResetActivity.this.finish();
            }
        }).create().show();
    }
}
