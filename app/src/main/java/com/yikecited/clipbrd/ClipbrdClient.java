// 
// Decompiled by Procyon v0.5.36
// 

package com.yikecited.clipbrd;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class ClipbrdClient
{
    private Credentials credentials;
    private String endpoint;
    
    public ClipbrdClient(final String endpoint) {
        this.endpoint = endpoint;
    }
    
    public void changeHash(final String s) throws ClipbrdClientException {
        Log.d("ClipbrdClient", "changeHash()");
        if (this.credentials == null) {
            throw new ClipbrdClientException("No credentials");
        }
        final ArrayList<BasicNameValuePair> list = new ArrayList<BasicNameValuePair>(1);
        list.add(new BasicNameValuePair("hash", s));
        int i = 0;
        Label_0198: {
            DefaultHttpClient defaultHttpClient;
            HttpPost httpPost;
            try {
                final UrlEncodedFormEntity entity = new UrlEncodedFormEntity((List)list);
                defaultHttpClient = new DefaultHttpClient();
                httpPost = new HttpPost(this.endpoint + "/change-hash");
                httpPost.addHeader("Authorization", this.credentials.getBasicAuthorization());
                httpPost.setEntity((HttpEntity)entity);
                final DefaultHttpClient defaultHttpClient2 = defaultHttpClient;
                final Object o = httpPost;
                final HttpResponse execute = defaultHttpClient2.execute((HttpUriRequest)o);
                final HttpResponse execute2 = execute;
                final StatusLine statusLine = execute2.getStatusLine();
                i = statusLine.getStatusCode();
                final String s2 = "ClipbrdClient";
                final StringBuilder sb = new StringBuilder();
                final String s3 = "HTTP ";
                final StringBuilder sb2 = sb.append(s3);
                final int n = i;
                final StringBuilder sb3 = sb2.append(n);
                final String s4 = sb3.toString();
                Log.i(s2, s4);
                final int n2 = i;
                final int n3 = 200;
                if (n2 == n3) {
                    return;
                }
                break Label_0198;
            }
            catch (UnsupportedEncodingException ex) {
                throw new ClipbrdClientException("Client error", ex);
            }
            try {
                final DefaultHttpClient defaultHttpClient2 = defaultHttpClient;
                final Object o = httpPost;
                final HttpResponse execute2;
                final HttpResponse execute = execute2 = defaultHttpClient2.execute((HttpUriRequest)o);
                final StatusLine statusLine = execute2.getStatusLine();
                i = statusLine.getStatusCode();
                final String s2 = "ClipbrdClient";
                final StringBuilder sb = new StringBuilder();
                final String s3 = "HTTP ";
                final StringBuilder sb2 = sb.append(s3);
                final int n = i;
                final StringBuilder sb3 = sb2.append(n);
                final String s4 = sb3.toString();
                Log.i(s2, s4);
                final int n2 = i;
                final int n3 = 200;
                if (n2 == n3) {
                    return;
                }
            }
            catch (Exception ex2) {
                throw new ClipbrdClientException("Request error", ex2);
            }
        }
        if (i == 401) {
            throw new ClipbrdClientException("Wrong old password");
        }
        throw new ClipbrdClientException("Unknown error: " + i);
    }
    
    public boolean forgotHash(final String s) throws ClipbrdClientException {
        Log.d("ClipbrdClient", "forgotHash()");
        final ArrayList<BasicNameValuePair> list = new ArrayList<BasicNameValuePair>(1);
        list.add(new BasicNameValuePair("email", s));
        int i = 0;
        Label_0167: {
            DefaultHttpClient defaultHttpClient;
            HttpPost httpPost;
            try {
                final UrlEncodedFormEntity entity = new UrlEncodedFormEntity((List)list);
                defaultHttpClient = new DefaultHttpClient();
                httpPost = new HttpPost(this.endpoint + "/forgot-hash");
                httpPost.setEntity((HttpEntity)entity);
                final DefaultHttpClient defaultHttpClient2 = defaultHttpClient;
                final Object o = httpPost;
                final HttpResponse execute = defaultHttpClient2.execute((HttpUriRequest)o);
                final HttpResponse execute2 = execute;
                final StatusLine statusLine = execute2.getStatusLine();
                i = statusLine.getStatusCode();
                final String s2 = "ClipbrdClient";
                final StringBuilder sb = new StringBuilder();
                final String s3 = "HTTP ";
                final StringBuilder sb2 = sb.append(s3);
                final int n = i;
                final StringBuilder sb3 = sb2.append(n);
                final String s4 = sb3.toString();
                Log.i(s2, s4);
                final int n2 = i;
                final int n3 = 200;
                if (n2 == n3) {
                    return true;
                }
                break Label_0167;
            }
            catch (UnsupportedEncodingException ex) {
                throw new ClipbrdClientException("Client error", ex);
            }
            try {
                final DefaultHttpClient defaultHttpClient2 = defaultHttpClient;
                final Object o = httpPost;
                final HttpResponse execute2;
                final HttpResponse execute = execute2 = defaultHttpClient2.execute((HttpUriRequest)o);
                final StatusLine statusLine = execute2.getStatusLine();
                i = statusLine.getStatusCode();
                final String s2 = "ClipbrdClient";
                final StringBuilder sb = new StringBuilder();
                final String s3 = "HTTP ";
                final StringBuilder sb2 = sb.append(s3);
                final int n = i;
                final StringBuilder sb3 = sb2.append(n);
                final String s4 = sb3.toString();
                Log.i(s2, s4);
                final int n2 = i;
                final int n3 = 200;
                if (n2 == n3) {
                    return true;
                }
            }
            catch (Exception ex2) {
                throw new ClipbrdClientException("Request error", ex2);
            }
        }
        if (i == 404) {
            return false;
        }
        throw new ClipbrdClientException("Unknown error: " + i);
    }
    
    public void pushClipboard(final String s, final CtWithIv ctWithIv) throws ClipbrdClientException {
        Log.d("ClipbrdClient", "pushClipboard()");
        if (this.credentials == null) {
            throw new ClipbrdClientException("No credentials");
        }
        final ArrayList<BasicNameValuePair> list = new ArrayList<BasicNameValuePair>(4);
        list.add(new BasicNameValuePair("device_id", s));
        list.add(new BasicNameValuePair("iv", ctWithIv.getIv()));
        list.add(new BasicNameValuePair("ct", ctWithIv.getCt()));
        int i = 0;
        Label_0245: {
            DefaultHttpClient defaultHttpClient;
            HttpPost httpPost;
            try {
                final UrlEncodedFormEntity entity = new UrlEncodedFormEntity((List)list, "UTF-8");
                defaultHttpClient = new DefaultHttpClient();
                httpPost = new HttpPost(this.endpoint + "/sync");
                httpPost.addHeader("Authorization", this.credentials.getBasicAuthorization());
                httpPost.setEntity((HttpEntity)entity);
                final DefaultHttpClient defaultHttpClient2 = defaultHttpClient;
                final Object o = httpPost;
                final HttpResponse execute = defaultHttpClient2.execute((HttpUriRequest)o);
                final HttpResponse execute2 = execute;
                final StatusLine statusLine = execute2.getStatusLine();
                i = statusLine.getStatusCode();
                final String s2 = "ClipbrdClient";
                final StringBuilder sb = new StringBuilder();
                final String s3 = "HTTP ";
                final StringBuilder sb2 = sb.append(s3);
                final int n = i;
                final StringBuilder sb3 = sb2.append(n);
                final String s4 = sb3.toString();
                Log.i(s2, s4);
                final int n2 = i;
                final int n3 = 200;
                if (n2 == n3) {
                    return;
                }
                break Label_0245;
            }
            catch (UnsupportedEncodingException ex) {
                throw new ClipbrdClientException("Client error", ex);
            }
            try {
                final DefaultHttpClient defaultHttpClient2 = defaultHttpClient;
                final Object o = httpPost;
                final HttpResponse execute2;
                final HttpResponse execute = execute2 = defaultHttpClient2.execute((HttpUriRequest)o);
                final StatusLine statusLine = execute2.getStatusLine();
                i = statusLine.getStatusCode();
                final String s2 = "ClipbrdClient";
                final StringBuilder sb = new StringBuilder();
                final String s3 = "HTTP ";
                final StringBuilder sb2 = sb.append(s3);
                final int n = i;
                final StringBuilder sb3 = sb2.append(n);
                final String s4 = sb3.toString();
                Log.i(s2, s4);
                final int n2 = i;
                final int n3 = 200;
                if (n2 == n3) {
                    return;
                }
            }
            catch (Exception ex2) {
                throw new ClipbrdClientException("Request error", ex2);
            }
        }
        if (i == 401) {
            throw new UnauthorizedException();
        }
        throw new ClipbrdClientException("Unknown error: " + i);
    }
    
    public void registerDevice(final String s) throws ClipbrdClientException {
        Log.d("ClipbrdClient", "registerDevice()");
        if (this.credentials == null) {
            throw new ClipbrdClientException("No credentials");
        }
        final ArrayList<BasicNameValuePair> list = new ArrayList<BasicNameValuePair>(1);
        list.add(new BasicNameValuePair("registration_id", s));
        int i = 0;
        Label_0198: {
            DefaultHttpClient defaultHttpClient;
            HttpPost httpPost;
            try {
                final UrlEncodedFormEntity entity = new UrlEncodedFormEntity((List)list);
                defaultHttpClient = new DefaultHttpClient();
                httpPost = new HttpPost(this.endpoint + "/devices/register");
                httpPost.addHeader("Authorization", this.credentials.getBasicAuthorization());
                httpPost.setEntity((HttpEntity)entity);
                final DefaultHttpClient defaultHttpClient2 = defaultHttpClient;
                final Object o = httpPost;
                final HttpResponse execute = defaultHttpClient2.execute((HttpUriRequest)o);
                final HttpResponse execute2 = execute;
                final StatusLine statusLine = execute2.getStatusLine();
                i = statusLine.getStatusCode();
                final String s2 = "ClipbrdClient";
                final StringBuilder sb = new StringBuilder();
                final String s3 = "HTTP ";
                final StringBuilder sb2 = sb.append(s3);
                final int n = i;
                final StringBuilder sb3 = sb2.append(n);
                final String s4 = sb3.toString();
                Log.i(s2, s4);
                final int n2 = i;
                final int n3 = 200;
                if (n2 == n3) {
                    return;
                }
                break Label_0198;
            }
            catch (UnsupportedEncodingException ex) {
                throw new ClipbrdClientException("Client error", ex);
            }
            try {
                final DefaultHttpClient defaultHttpClient2 = defaultHttpClient;
                final Object o = httpPost;
                final HttpResponse execute2;
                final HttpResponse execute = execute2 = defaultHttpClient2.execute((HttpUriRequest)o);
                final StatusLine statusLine = execute2.getStatusLine();
                i = statusLine.getStatusCode();
                final String s2 = "ClipbrdClient";
                final StringBuilder sb = new StringBuilder();
                final String s3 = "HTTP ";
                final StringBuilder sb2 = sb.append(s3);
                final int n = i;
                final StringBuilder sb3 = sb2.append(n);
                final String s4 = sb3.toString();
                Log.i(s2, s4);
                final int n2 = i;
                final int n3 = 200;
                if (n2 == n3) {
                    return;
                }
            }
            catch (Exception ex2) {
                throw new ClipbrdClientException("Request error", ex2);
            }
        }
        if (i == 401) {
            throw new ClipbrdClientException("Wrong email or password");
        }
        throw new ClipbrdClientException("Unknown error: " + i);
    }
    
    public void resetHash(final Credentials credentials, final String s) throws ClipbrdClientException {
        Log.d("ClipbrdClient", "resetHash()");
        if (credentials == null) {
            throw new ClipbrdClientException("No credentials");
        }
        final ArrayList<BasicNameValuePair> list = new ArrayList<BasicNameValuePair>(3);
        list.add(new BasicNameValuePair("email", credentials.getEmail()));
        list.add(new BasicNameValuePair("token", s));
        list.add(new BasicNameValuePair("hash", credentials.getHash()));
        int i = 0;
        Label_0226: {
            DefaultHttpClient defaultHttpClient;
            HttpPost httpPost;
            try {
                final UrlEncodedFormEntity entity = new UrlEncodedFormEntity((List)list);
                defaultHttpClient = new DefaultHttpClient();
                httpPost = new HttpPost(this.endpoint + "/reset-hash");
                httpPost.setEntity((HttpEntity)entity);
                final DefaultHttpClient defaultHttpClient2 = defaultHttpClient;
                final Object o = httpPost;
                final HttpResponse execute = defaultHttpClient2.execute((HttpUriRequest)o);
                final HttpResponse execute2 = execute;
                final StatusLine statusLine = execute2.getStatusLine();
                i = statusLine.getStatusCode();
                final String s2 = "ClipbrdClient";
                final StringBuilder sb = new StringBuilder();
                final String s3 = "HTTP ";
                final StringBuilder sb2 = sb.append(s3);
                final int n = i;
                final StringBuilder sb3 = sb2.append(n);
                final String s4 = sb3.toString();
                Log.i(s2, s4);
                final int n2 = i;
                final int n3 = 200;
                if (n2 == n3) {
                    return;
                }
                break Label_0226;
            }
            catch (UnsupportedEncodingException ex) {
                throw new ClipbrdClientException("Client error", ex);
            }
            try {
                final DefaultHttpClient defaultHttpClient2 = defaultHttpClient;
                final Object o = httpPost;
                final HttpResponse execute2;
                final HttpResponse execute = execute2 = defaultHttpClient2.execute((HttpUriRequest)o);
                final StatusLine statusLine = execute2.getStatusLine();
                i = statusLine.getStatusCode();
                final String s2 = "ClipbrdClient";
                final StringBuilder sb = new StringBuilder();
                final String s3 = "HTTP ";
                final StringBuilder sb2 = sb.append(s3);
                final int n = i;
                final StringBuilder sb3 = sb2.append(n);
                final String s4 = sb3.toString();
                Log.i(s2, s4);
                final int n2 = i;
                final int n3 = 200;
                if (n2 == n3) {
                    return;
                }
            }
            catch (Exception ex2) {
                throw new ClipbrdClientException("Request error", ex2);
            }
        }
        if (i == 401) {
            throw new ClipbrdClientException("Wrong token");
        }
        throw new ClipbrdClientException("Unknown error: " + i);
    }
    
    public void setCredentials(final Credentials credentials) {
        this.credentials = credentials;
    }
    
    public void signUp(final Credentials credentials) throws ClipbrdClientException {
        Log.d("ClipbrdClient", "signUp()");
        if (credentials == null) {
            throw new ClipbrdClientException("No credentials");
        }
        final ArrayList<BasicNameValuePair> list = new ArrayList<BasicNameValuePair>(2);
        list.add(new BasicNameValuePair("email", credentials.getEmail()));
        list.add(new BasicNameValuePair("hash", credentials.getHash()));
        int i = 0;
        Label_0204: {
            DefaultHttpClient defaultHttpClient;
            HttpPost httpPost;
            try {
                final UrlEncodedFormEntity entity = new UrlEncodedFormEntity((List)list);
                defaultHttpClient = new DefaultHttpClient();
                httpPost = new HttpPost(this.endpoint + "/signup");
                httpPost.setEntity((HttpEntity)entity);
                final DefaultHttpClient defaultHttpClient2 = defaultHttpClient;
                final Object o = httpPost;
                final HttpResponse execute = defaultHttpClient2.execute((HttpUriRequest)o);
                final HttpResponse execute2 = execute;
                final StatusLine statusLine = execute2.getStatusLine();
                i = statusLine.getStatusCode();
                final String s = "ClipbrdClient";
                final StringBuilder sb = new StringBuilder();
                final String s2 = "HTTP ";
                final StringBuilder sb2 = sb.append(s2);
                final int n = i;
                final StringBuilder sb3 = sb2.append(n);
                final String s3 = sb3.toString();
                Log.i(s, s3);
                final int n2 = i;
                final int n3 = 200;
                if (n2 == n3) {
                    return;
                }
                break Label_0204;
            }
            catch (UnsupportedEncodingException ex) {
                throw new ClipbrdClientException("Client error", ex);
            }
            try {
                final DefaultHttpClient defaultHttpClient2 = defaultHttpClient;
                final Object o = httpPost;
                final HttpResponse execute2;
                final HttpResponse execute = execute2 = defaultHttpClient2.execute((HttpUriRequest)o);
                final StatusLine statusLine = execute2.getStatusLine();
                i = statusLine.getStatusCode();
                final String s = "ClipbrdClient";
                final StringBuilder sb = new StringBuilder();
                final String s2 = "HTTP ";
                final StringBuilder sb2 = sb.append(s2);
                final int n = i;
                final StringBuilder sb3 = sb2.append(n);
                final String s3 = sb3.toString();
                Log.i(s, s3);
                final int n2 = i;
                final int n3 = 200;
                if (n2 == n3) {
                    return;
                }
            }
            catch (Exception ex2) {
                throw new ClipbrdClientException("Request error", ex2);
            }
        }
        if (i == 409) {
            throw new ClipbrdClientException("This email is already in use");
        }
        throw new ClipbrdClientException("Unknown error: " + i);
    }
    
    public void unregisterDevice(final String s) throws ClipbrdClientException {
        Log.d("ClipbrdClient", "unregisterDevice()");
        if (this.credentials == null) {
            throw new ClipbrdClientException("No credentials");
        }
        final ArrayList<BasicNameValuePair> list = new ArrayList<BasicNameValuePair>(1);
        list.add(new BasicNameValuePair("registration_id", s));
        int i = 0;
        Label_0198: {
            DefaultHttpClient defaultHttpClient;
            HttpPost httpPost;
            try {
                final UrlEncodedFormEntity entity = new UrlEncodedFormEntity((List)list);
                defaultHttpClient = new DefaultHttpClient();
                httpPost = new HttpPost(this.endpoint + "/devices/unregister");
                httpPost.addHeader("Authorization", this.credentials.getBasicAuthorization());
                httpPost.setEntity((HttpEntity)entity);
                final DefaultHttpClient defaultHttpClient2 = defaultHttpClient;
                final Object o = httpPost;
                final HttpResponse execute = defaultHttpClient2.execute((HttpUriRequest)o);
                final HttpResponse execute2 = execute;
                final StatusLine statusLine = execute2.getStatusLine();
                i = statusLine.getStatusCode();
                final String s2 = "ClipbrdClient";
                final StringBuilder sb = new StringBuilder();
                final String s3 = "HTTP ";
                final StringBuilder sb2 = sb.append(s3);
                final int n = i;
                final StringBuilder sb3 = sb2.append(n);
                final String s4 = sb3.toString();
                Log.i(s2, s4);
                final int n2 = i;
                final int n3 = 200;
                if (n2 == n3) {
                    return;
                }
                break Label_0198;
            }
            catch (UnsupportedEncodingException ex) {
                throw new ClipbrdClientException("Client error", ex);
            }
            try {
                final DefaultHttpClient defaultHttpClient2 = defaultHttpClient;
                final Object o = httpPost;
                final HttpResponse execute2;
                final HttpResponse execute = execute2 = defaultHttpClient2.execute((HttpUriRequest)o);
                final StatusLine statusLine = execute2.getStatusLine();
                i = statusLine.getStatusCode();
                final String s2 = "ClipbrdClient";
                final StringBuilder sb = new StringBuilder();
                final String s3 = "HTTP ";
                final StringBuilder sb2 = sb.append(s3);
                final int n = i;
                final StringBuilder sb3 = sb2.append(n);
                final String s4 = sb3.toString();
                Log.i(s2, s4);
                final int n2 = i;
                final int n3 = 200;
                if (n2 == n3) {
                    return;
                }
            }
            catch (Exception ex2) {
                throw new ClipbrdClientException("Request error", ex2);
            }
        }
        if (i == 401) {
            throw new ClipbrdClientException("Wrong email or password");
        }
        throw new ClipbrdClientException("Unknown error: " + i);
    }
    
    public class ClipbrdClientException extends Exception
    {
        public ClipbrdClientException(final String message) {
            super(message);
        }
        
        public ClipbrdClientException(final String message, final Throwable cause) {
            super(message, cause);
        }
    }
}
