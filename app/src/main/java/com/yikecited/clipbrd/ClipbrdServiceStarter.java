// 
// Decompiled by Procyon v0.5.36
// 

package com.yikecited.clipbrd;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build$VERSION;
import android.util.Log;

public class ClipbrdServiceStarter extends BroadcastReceiver
{
    public void onReceive(final Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            Log.i("ClipbrdServiceStarter", "Received BOOT_COMPLETED.");
            intent = new Intent(context, (Class)ClipbrdService.class);
            ComponentName componentName;
            if (Build$VERSION.SDK_INT >= 26) {
                Log.d("ClipbrdServiceStarter", "startForegroundService()");
                componentName = context.startForegroundService(intent);
            }
            else {
                Log.d("ClipbrdServiceStarter", "startService()");
                componentName = context.startService(intent);
            }
            if (componentName == null) {
                Log.e("ClipbrdServiceStarter", "Can't start service " + ClipbrdService.class.getName());
            }
            Log.i("ClipbrdServiceStarter", "Successfully started " + ClipbrdService.class.getName());
            return;
        }
        Log.e("ClipbrdServiceStarter", "Recieved unexpected intent: " + intent.toString());
    }
}
