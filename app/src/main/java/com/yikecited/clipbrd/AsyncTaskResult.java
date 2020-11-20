// 
// Decompiled by Procyon v0.5.36
// 

package com.yikecited.clipbrd;

public class AsyncTaskResult<T>
{
    private Exception error;
    private T result;
    
    public AsyncTaskResult(final Exception error) {
        this.error = error;
    }
    
    public AsyncTaskResult(final T result) {
        this.result = result;
    }
    
    public Exception getError() {
        return this.error;
    }
    
    public T getResult() {
        return this.result;
    }
    
    public boolean hasError() {
        return this.error != null;
    }
}
