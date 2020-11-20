// 
// Decompiled by Procyon v0.5.36
// 

package com.yikecited.clipbrd;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences$Editor;

public class Utils
{
    public static String byteArrayToHexString(final byte[] array) {
        final StringBuilder sb = new StringBuilder();
        for (int length = array.length, i = 0; i < length; ++i) {
            sb.append(String.format("%02x", array[i]));
        }
        return sb.toString();
    }
    
    public static void forgetCredentials(final Context context) {
        final SharedPreferences$Editor edit = getConfig(context).edit();
        edit.remove("email");
        edit.remove("crypto_key");
        edit.remove("hash");
        edit.putBoolean("enabled", false);
        edit.commit();
    }
    
    public static SharedPreferences getConfig(final Context context) {
        return context.getSharedPreferences("config", 0);
    }
    
    public static Credentials getCredentials(final Context context) {
        final SharedPreferences config = getConfig(context);
        final String string = config.getString("email", (String)null);
        final String string2 = config.getString("hash", (String)null);
        if (string == null || string2 == null) {
            return null;
        }
        return new Credentials(string, string2);
    }
    
    public static byte[] hexStringToByteArray(final String s) {
        final int length = s.length();
        final byte[] array = new byte[length / 2];
        for (int i = 0; i < length; i += 2) {
            array[i / 2] = (byte)((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i + 1), 16));
        }
        return array;
    }
}
