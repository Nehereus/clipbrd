// 
// Decompiled by Procyon v0.5.36
// 

package com.yikecited.clipbrd;

import android.content.Context;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class MyInstanceIdService extends FirebaseInstanceIdService
{
    @Override
    public void onTokenRefresh() {
        final String token = FirebaseInstanceId.getInstance().getToken();
        Log.d("MyFIIS", "Refreshed token: " + token);
        final Credentials credentials = Utils.getCredentials((Context)this);
        if (credentials == null) {
            Log.v("MyFIIS", "Not logged in - skipping token refresh.");
            return;
        }
        final ClipbrdClient clipbrdClient = new ClipbrdClient("https://api.clipbrd.com");
        clipbrdClient.setCredentials(credentials);
        try {
            clipbrdClient.registerDevice(token);
        }
        catch (ClipbrdClient.ClipbrdClientException ex) {
            Log.e("MyFIIS", "Failed to register device", (Throwable)ex);
        }
    }
}
