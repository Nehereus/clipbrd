// 
// Decompiled by Procyon v0.5.36
// 

package com.yikecited.clipbrd;

import android.content.Context;

import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;

public class ChangePasswordTask extends CallbackAsyncTask<Object, Void, String>
{
    public ChangePasswordTask(final Context context, final AsyncTaskCompleteListener<String> asyncTaskCompleteListener) {
        super(context, asyncTaskCompleteListener);
    }
    
    protected String doInBackground(final Object... array) {
        final Credentials credentials = (Credentials)array[0];
        final Credentials credentials2 = (Credentials)array[1];
        final ClipbrdClient clipbrdClient = new ClipbrdClient("https://api.clipbrd.com");
        clipbrdClient.setCredentials(credentials);
        try {
            clipbrdClient.changeHash(credentials2.getHash());
            return null;
        }
        catch (Exception ex) {
            ThrowableExtension.printStackTrace(ex);
            return ex.getMessage();
        }
    }
}
