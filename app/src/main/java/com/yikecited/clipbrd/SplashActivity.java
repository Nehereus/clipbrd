// 
// Decompiled by Procyon v0.5.36
// 

package com.yikecited.clipbrd;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View$OnClickListener;
import android.widget.Button;

public class SplashActivity extends Activity
{
    private View$OnClickListener mSignInListener;
    private View$OnClickListener mSignUpListener;
    
    public SplashActivity() {
        this.mSignUpListener = (View$OnClickListener)new View$OnClickListener() {
            public void onClick(final View view) {
                SplashActivity.this.startActivityForResult(new Intent((Context)SplashActivity.this, (Class)SignUpActivity.class), 1);
            }
        };
        this.mSignInListener = (View$OnClickListener)new View$OnClickListener() {
            public void onClick(final View view) {
                SplashActivity.this.startActivityForResult(new Intent((Context)SplashActivity.this, (Class)SignInActivity.class), 2);
            }
        };
    }
    
    protected void onActivityResult(final int n, final int n2, final Intent intent) {
        if (n2 == 0) {
            return;
        }
        this.setResult(n2, intent);
        this.finish();
    }
    
    public void onCreate(final Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(2131230726);
        ((Button)this.findViewById(2131099665)).setOnClickListener(this.mSignUpListener);
        ((Button)this.findViewById(2131099663)).setOnClickListener(this.mSignInListener);
    }
}
