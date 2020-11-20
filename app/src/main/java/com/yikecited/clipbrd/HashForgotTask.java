// 
// Decompiled by Procyon v0.5.36
// 

package com.yikecited.clipbrd;

import android.content.Context;

import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;

public class HashForgotTask extends CallbackAsyncTask<Object, Void, AsyncTaskResult<Boolean>>
{
    public HashForgotTask(final Context context, final AsyncTaskCompleteListener<AsyncTaskResult<Boolean>> asyncTaskCompleteListener) {
        super(context, asyncTaskCompleteListener);
    }
    
    protected AsyncTaskResult<Boolean> doInBackground(final Object... array) {
        final String s = (String)array[0];
        final ClipbrdClient clipbrdClient = new ClipbrdClient("https://api.clipbrd.com");
        try {
            return new AsyncTaskResult<Boolean>(clipbrdClient.forgotHash(s));
        }
        catch (Exception ex) {
            ThrowableExtension.printStackTrace(ex);
            return new AsyncTaskResult<Boolean>(ex);
        }
    }
}
