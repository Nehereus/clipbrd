// 
// Decompiled by Procyon v0.5.36
// 

package com.yikecited.clipbrd;

import android.content.Context;
import android.util.Log;

import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.google.firebase.iid.FirebaseInstanceId;

import java.net.UnknownHostException;

public class PushClipboardTask extends CallbackAsyncTask<Object, Void, Integer>
{
    public PushClipboardTask(final Context context, final AsyncTaskCompleteListener<Integer> asyncTaskCompleteListener) {
        super(context, asyncTaskCompleteListener);
    }
    
    protected Integer doInBackground(final Object... array) {
        final CtWithIv ctWithIv = (CtWithIv)array[0];
        final Credentials credentials = Utils.getCredentials(this.context);
        if (credentials == null) {
            Log.e("PushClipboardTask", "Not signed in.");
            return -2;
        }
        final String token = FirebaseInstanceId.getInstance().getToken();
        if (token == null || token.equals("")) {
            Log.e("PushClipboardTask", "No registrationId");
            return -1;
        }
        final ClipbrdClient clipbrdClient = new ClipbrdClient("https://api.clipbrd.com");
        clipbrdClient.setCredentials(credentials);
        try {
            clipbrdClient.pushClipboard(token, ctWithIv);
            return 0;
        }
        catch (UnauthorizedException ex3) {
            return -3;
        }
        catch (ClipbrdClient.ClipbrdClientException ex) {
            final Throwable cause = ex.getCause();
            if (cause != null && cause instanceof UnknownHostException) {
                return -5;
            }
            ThrowableExtension.printStackTrace(ex);
            return -42;
        }
        catch (Exception ex2) {
            ThrowableExtension.printStackTrace(ex2);
            return -42;
        }
    }
}
