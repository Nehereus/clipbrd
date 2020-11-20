// 
// Decompiled by Procyon v0.5.36
// 

package com.yikecited.clipbrd;

import android.util.Base64;

public class Credentials
{
    private String email;
    private String hash;
    private String key;
    
    public Credentials(final String email, final String hash) {
        this.email = email;
        this.hash = hash;
    }
    
    public String getBasicAuthorization() {
        return "Basic " + Base64.encodeToString((this.email + ":" + this.hash).getBytes(), 2);
    }
    
    public String getEmail() {
        return this.email;
    }
    
    public String getHash() {
        return this.hash;
    }
    
    public String getKey() {
        return this.key;
    }
    
    public void setKey(final String key) {
        this.key = key;
    }
}
