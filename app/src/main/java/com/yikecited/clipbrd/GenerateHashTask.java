// 
// Decompiled by Procyon v0.5.36
// 

package com.yikecited.clipbrd;

import android.content.Context;

public class GenerateHashTask extends CallbackAsyncTask<Object, Void, String>
{
    public GenerateHashTask(final Context context, final AsyncTaskCompleteListener<String> asyncTaskCompleteListener) {
        super(context, asyncTaskCompleteListener);
    }
    
    protected String doInBackground(final Object... array) {
        return Crypto.createHash((String)array[0], (String)array[1]);
    }
}
