// 
// Decompiled by Procyon v0.5.36
// 

package com.yikecited.clipbrd;

import android.content.Intent;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService
{
    @Override
    public void onMessageReceived(final RemoteMessage remoteMessage) {
        Log.d("MyFMS", "onMessageReceived()");
        final Map<String, String> data = remoteMessage.getData();
        final String s = data.get("clipboard");
        final String s2 = data.get("iv");
        final String s3 = data.get("ct");
        final String s4 = data.get("deviceId");
        final Intent intent = new Intent();
        intent.setAction("com.clipbrd.action.INCOMING_CLIPBOARD");
        intent.putExtra("clipboard", s);
        intent.putExtra("iv", s2);
        intent.putExtra("ct", s3);
        intent.putExtra("deviceId", s4);
        this.sendBroadcast(intent);
    }
}
