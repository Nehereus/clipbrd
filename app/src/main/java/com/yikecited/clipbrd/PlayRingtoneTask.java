// 
// Decompiled by Procyon v0.5.36
// 

package com.yikecited.clipbrd;

import android.media.Ringtone;
import android.os.AsyncTask;
import android.util.Log;

public class PlayRingtoneTask extends AsyncTask<Ringtone, Void, Void>
{
    private static final String TAG;
    
    static {
        TAG = PlayRingtoneTask.class.getName();
    }
    
    protected Void doInBackground(final Ringtone... array) {
        final Ringtone ringtone = array[0];
        Label_0056: {
            try {
                ringtone.play();
                while (ringtone.isPlaying()) {
                    Thread.sleep(100L);
                }
                break Label_0056;
            }
            catch (Exception ex) {
                Log.w(PlayRingtoneTask.TAG, "Failed to play ringtone: " + ex.toString());
            }
            return null;
        }
        ringtone.stop();
        return null;
    }
}
