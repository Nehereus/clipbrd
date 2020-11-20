// 
// Decompiled by Procyon v0.5.36
// 

package com.yikecited.clipbrd;

import android.content.Context;
import android.os.AsyncTask;

public abstract class CallbackAsyncTask<Params, Progress, Result> extends AsyncTask<Params, Progress, Result>
{
    protected AsyncTaskCompleteListener<Result> callback;
    protected Context context;
    
    public CallbackAsyncTask(final Context context, final AsyncTaskCompleteListener<Result> callback) {
        this.context = context;
        this.callback = callback;
    }
    
    protected void onPostExecute(final Result result) {
        this.callback.onTaskComplete(result);
    }
}
