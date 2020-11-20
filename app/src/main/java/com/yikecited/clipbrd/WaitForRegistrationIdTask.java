// 
// Decompiled by Procyon v0.5.36
// 

package com.yikecited.clipbrd;

import android.content.Context;

import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.google.firebase.iid.FirebaseInstanceId;

public class WaitForRegistrationIdTask extends CallbackAsyncTask<Void, Void, String>
{
    public WaitForRegistrationIdTask(final Context context, final AsyncTaskCompleteListener<String> asyncTaskCompleteListener) {
        super(context, asyncTaskCompleteListener);
    }
    
    protected String doInBackground(final Void... array) {
        String s = "";
        int n = 5;
        while (true) {
            final int n2 = n - 1;
            if (n2 <= 0) {
                break;
            }
            String s2 = s;
            try {
                final String token = FirebaseInstanceId.getInstance().getToken();
                if (token != null) {
                    n = n2;
                    s = token;
                    s2 = token;
                    if (!token.equals("")) {
                        continue;
                    }
                }
                s2 = token;
                Thread.sleep(1000L);
                n = n2;
                s = token;
            }
            catch (InterruptedException ex) {
                ThrowableExtension.printStackTrace(ex);
                return s2;
            }
        }
        return s;
    }
}
