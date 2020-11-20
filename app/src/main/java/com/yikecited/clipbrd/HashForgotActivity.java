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
import android.os.Bundle;
import android.view.View;
import android.view.View$OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class HashForgotActivity extends Activity
{
    protected void forgotHash() {
        final EditText editText = (EditText)this.findViewById(2131099670);
        final String trim = editText.getText().toString().trim();
        boolean b = true;
        if (trim.equals("")) {
            editText.setError((CharSequence)this.getString(2131361829));
            b = false;
        }
        if (!b) {
            return;
        }
        new HashForgotTask((Context)this, new AsyncTaskCompleteListener<AsyncTaskResult<Boolean>>() {
            final /* synthetic */ ProgressDialog val$progressDialog = ProgressDialog.show((Context)this, (CharSequence)"", (CharSequence)HashForgotActivity.this.getString(2131361861), true);
            
            @Override
            public void onTaskComplete(final AsyncTaskResult<Boolean> asyncTaskResult) {
                this.val$progressDialog.cancel();
                if (asyncTaskResult.hasError()) {
                    HashForgotActivity.this.showAlert(asyncTaskResult.getError().getMessage());
                    return;
                }
                if (asyncTaskResult.getResult()) {
                    HashForgotActivity.this.showAlert(2131361856);
                    return;
                }
                ((EditText)HashForgotActivity.this.findViewById(2131099670)).setError((CharSequence)HashForgotActivity.this.getString(2131361846));
            }
        }).execute(new Object[] { trim });
    }
    
    protected void onCreate(final Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(2131230721);
        ((Button)this.findViewById(2131099666)).setOnClickListener((View$OnClickListener)new View$OnClickListener() {
            public void onClick(final View view) {
                HashForgotActivity.this.forgotHash();
            }
        });
        ((Button)this.findViewById(2131099659)).setOnClickListener((View$OnClickListener)new View$OnClickListener() {
            public void onClick(final View view) {
                HashForgotActivity.this.finish();
            }
        });
    }
    
    protected void showAlert(final int n) {
        this.showAlert(this.getString(n));
    }
    
    protected void showAlert(final String message) {
        new AlertDialog$Builder((Context)this).setMessage((CharSequence)message).setCancelable(false).setPositiveButton(2131361801, (DialogInterface$OnClickListener)new DialogInterface$OnClickListener() {
            public void onClick(final DialogInterface dialogInterface, final int n) {
                HashForgotActivity.this.finish();
            }
        }).create().show();
    }
}
