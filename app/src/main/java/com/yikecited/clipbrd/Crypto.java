// 
// Decompiled by Procyon v0.5.36
// 

package com.yikecited.clipbrd;

import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class Crypto
{
    public static String createHash(String byteArrayToHexString, final String s) {
        final PBEKeySpec keySpec = new PBEKeySpec(s.toCharArray(), ("secret:" + byteArrayToHexString + ":secret").getBytes(), 1000, 256);
        try {
            byteArrayToHexString = Utils.byteArrayToHexString(SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1").generateSecret(keySpec).getEncoded());
            return byteArrayToHexString;
        }
        catch (NoSuchAlgorithmException ex) {
            ThrowableExtension.printStackTrace(ex);
        }
        catch (InvalidKeySpecException ex2) {
            ThrowableExtension.printStackTrace(ex2);
            goto Label_0072;
        }
    }
    
    public static String createKey(String byteArrayToHexString, final String s) {
        final PBEKeySpec keySpec = new PBEKeySpec(s.toCharArray(), ("secret:" + byteArrayToHexString + ":secret").getBytes(), 1000, 256);
        try {
            byteArrayToHexString = Utils.byteArrayToHexString(SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1").generateSecret(keySpec).getEncoded());
            return byteArrayToHexString;
        }
        catch (NoSuchAlgorithmException ex) {
            ThrowableExtension.printStackTrace(ex);
        }
        catch (InvalidKeySpecException ex2) {
            ThrowableExtension.printStackTrace(ex2);
            goto Label_0072;
        }
    }
    
    public static String decrypt(final String p0, final String p1, final String p2) throws BadPaddingException {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: invokestatic    com/clipbrd/Utils.hexStringToByteArray:(Ljava/lang/String;)[B
        //     4: astore_3       
        //     5: aload_1        
        //     6: iconst_2       
        //     7: invokestatic    android/util/Base64.decode:(Ljava/lang/String;I)[B
        //    10: astore_1       
        //    11: aload_2        
        //    12: iconst_2       
        //    13: invokestatic    android/util/Base64.decode:(Ljava/lang/String;I)[B
        //    16: astore_0       
        //    17: new             Ljavax/crypto/spec/SecretKeySpec;
        //    20: dup            
        //    21: aload_3        
        //    22: ldc             "AES/CCM/NoPadding"
        //    24: invokespecial   javax/crypto/spec/SecretKeySpec.<init>:([BLjava/lang/String;)V
        //    27: astore_2       
        //    28: new             Ljavax/crypto/spec/IvParameterSpec;
        //    31: dup            
        //    32: aload_1        
        //    33: invokespecial   javax/crypto/spec/IvParameterSpec.<init>:([B)V
        //    36: astore_1       
        //    37: ldc             "AES/CCM/NoPadding"
        //    39: invokestatic    javax/crypto/Cipher.getInstance:(Ljava/lang/String;)Ljavax/crypto/Cipher;
        //    42: astore_3       
        //    43: aload_3        
        //    44: iconst_2       
        //    45: aload_2        
        //    46: aload_1        
        //    47: invokevirtual   javax/crypto/Cipher.init:(ILjava/security/Key;Ljava/security/spec/AlgorithmParameterSpec;)V
        //    50: aload_3        
        //    51: aload_0        
        //    52: invokevirtual   javax/crypto/Cipher.doFinal:([B)[B
        //    55: astore_0       
        //    56: new             Ljava/lang/String;
        //    59: dup            
        //    60: aload_0        
        //    61: ldc             "UTF-8"
        //    63: invokespecial   java/lang/String.<init>:([BLjava/lang/String;)V
        //    66: astore_0       
        //    67: aload_0        
        //    68: areturn        
        //    69: astore_0       
        //    70: aload_0        
        //    71: invokestatic    com/google/devtools/build/android/desugar/runtime/ThrowableExtension.printStackTrace:(Ljava/lang/Throwable;)V
        //    74: aconst_null    
        //    75: areturn        
        //    76: astore_0       
        //    77: aload_0        
        //    78: invokestatic    com/google/devtools/build/android/desugar/runtime/ThrowableExtension.printStackTrace:(Ljava/lang/Throwable;)V
        //    81: aconst_null    
        //    82: areturn        
        //    83: astore_0       
        //    84: aload_0        
        //    85: invokestatic    com/google/devtools/build/android/desugar/runtime/ThrowableExtension.printStackTrace:(Ljava/lang/Throwable;)V
        //    88: aconst_null    
        //    89: areturn        
        //    90: astore_0       
        //    91: aload_0        
        //    92: invokestatic    com/google/devtools/build/android/desugar/runtime/ThrowableExtension.printStackTrace:(Ljava/lang/Throwable;)V
        //    95: aconst_null    
        //    96: areturn        
        //    97: astore_0       
        //    98: aload_0        
        //    99: invokestatic    com/google/devtools/build/android/desugar/runtime/ThrowableExtension.printStackTrace:(Ljava/lang/Throwable;)V
        //   102: aconst_null    
        //   103: areturn        
        //   104: astore_0       
        //   105: aload_0        
        //   106: invokestatic    com/google/devtools/build/android/desugar/runtime/ThrowableExtension.printStackTrace:(Ljava/lang/Throwable;)V
        //   109: aconst_null    
        //   110: areturn        
        //    Exceptions:
        //  throws javax.crypto.BadPaddingException
        //    Exceptions:
        //  Try           Handler
        //  Start  End    Start  End    Type                                              
        //  -----  -----  -----  -----  --------------------------------------------------
        //  37     43     69     76     Ljava/security/NoSuchAlgorithmException;
        //  37     43     76     83     Ljavax/crypto/NoSuchPaddingException;
        //  43     50     83     90     Ljava/security/InvalidKeyException;
        //  43     50     90     97     Ljava/security/InvalidAlgorithmParameterException;
        //  50     56     97     104    Ljavax/crypto/IllegalBlockSizeException;
        //  56     67     104    111    Ljava/io/UnsupportedEncodingException;
        // 
        // The error that occurred was:
        // 
        // java.lang.IndexOutOfBoundsException: Index 72 out of bounds for length 72
        //     at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:64)
        //     at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:70)
        //     at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:248)
        //     at java.base/java.util.Objects.checkIndex(Objects.java:372)
        //     at java.base/java.util.ArrayList.get(ArrayList.java:458)
        //     at com.strobel.decompiler.ast.AstBuilder.convertToAst(AstBuilder.java:3321)
        //     at com.strobel.decompiler.ast.AstBuilder.build(AstBuilder.java:113)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:211)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:125)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at com.strobel.decompiler.DecompilerDriver.decompileType(DecompilerDriver.java:330)
        //     at com.strobel.decompiler.DecompilerDriver.decompileJar(DecompilerDriver.java:251)
        //     at com.strobel.decompiler.DecompilerDriver.main(DecompilerDriver.java:126)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public static CtWithIv encrypt(final String s, final String s2) {
        final byte[] bytes = new byte[12];
        new SecureRandom().nextBytes(bytes);
        return encrypt(s, bytes, s2);
    }
    
    public static CtWithIv encrypt(final String p0, final byte[] p1, final String p2) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: dup            
        //     4: aload_0        
        //     5: invokestatic    com/clipbrd/Utils.hexStringToByteArray:(Ljava/lang/String;)[B
        //     8: ldc             "AES/CCM/NoPadding"
        //    10: invokespecial   javax/crypto/spec/SecretKeySpec.<init>:([BLjava/lang/String;)V
        //    13: astore_3       
        //    14: ldc             "AES/CCM/NoPadding"
        //    16: invokestatic    javax/crypto/Cipher.getInstance:(Ljava/lang/String;)Ljavax/crypto/Cipher;
        //    19: astore_0       
        //    20: new             Ljavax/crypto/spec/IvParameterSpec;
        //    23: dup            
        //    24: aload_1        
        //    25: invokespecial   javax/crypto/spec/IvParameterSpec.<init>:([B)V
        //    28: astore          4
        //    30: aload_0        
        //    31: iconst_1       
        //    32: aload_3        
        //    33: aload           4
        //    35: invokevirtual   javax/crypto/Cipher.init:(ILjava/security/Key;Ljava/security/spec/AlgorithmParameterSpec;)V
        //    38: aload_2        
        //    39: ldc             "UTF-8"
        //    41: invokevirtual   java/lang/String.getBytes:(Ljava/lang/String;)[B
        //    44: astore_2       
        //    45: aload_0        
        //    46: aload_2        
        //    47: invokevirtual   javax/crypto/Cipher.doFinal:([B)[B
        //    50: astore_0       
        //    51: new             Lcom/clipbrd/CtWithIv;
        //    54: dup            
        //    55: aload_0        
        //    56: iconst_2       
        //    57: invokestatic    android/util/Base64.encodeToString:([BI)Ljava/lang/String;
        //    60: aload_1        
        //    61: iconst_2       
        //    62: invokestatic    android/util/Base64.encodeToString:([BI)Ljava/lang/String;
        //    65: invokespecial   com/clipbrd/CtWithIv.<init>:(Ljava/lang/String;Ljava/lang/String;)V
        //    68: areturn        
        //    69: astore_0       
        //    70: aload_0        
        //    71: invokestatic    com/google/devtools/build/android/desugar/runtime/ThrowableExtension.printStackTrace:(Ljava/lang/Throwable;)V
        //    74: aconst_null    
        //    75: areturn        
        //    76: astore_0       
        //    77: aload_0        
        //    78: invokestatic    com/google/devtools/build/android/desugar/runtime/ThrowableExtension.printStackTrace:(Ljava/lang/Throwable;)V
        //    81: aconst_null    
        //    82: areturn        
        //    83: astore_0       
        //    84: aload_0        
        //    85: invokestatic    com/google/devtools/build/android/desugar/runtime/ThrowableExtension.printStackTrace:(Ljava/lang/Throwable;)V
        //    88: aconst_null    
        //    89: areturn        
        //    90: astore_0       
        //    91: aload_0        
        //    92: invokestatic    com/google/devtools/build/android/desugar/runtime/ThrowableExtension.printStackTrace:(Ljava/lang/Throwable;)V
        //    95: aconst_null    
        //    96: areturn        
        //    97: astore_0       
        //    98: aload_0        
        //    99: invokestatic    com/google/devtools/build/android/desugar/runtime/ThrowableExtension.printStackTrace:(Ljava/lang/Throwable;)V
        //   102: aconst_null    
        //   103: areturn        
        //   104: astore_0       
        //   105: aload_0        
        //   106: invokestatic    com/google/devtools/build/android/desugar/runtime/ThrowableExtension.printStackTrace:(Ljava/lang/Throwable;)V
        //   109: aconst_null    
        //   110: areturn        
        //   111: astore_0       
        //   112: aload_0        
        //   113: invokestatic    com/google/devtools/build/android/desugar/runtime/ThrowableExtension.printStackTrace:(Ljava/lang/Throwable;)V
        //   116: aconst_null    
        //   117: areturn        
        //    Exceptions:
        //  Try           Handler
        //  Start  End    Start  End    Type                                              
        //  -----  -----  -----  -----  --------------------------------------------------
        //  14     20     69     76     Ljava/security/NoSuchAlgorithmException;
        //  14     20     76     83     Ljavax/crypto/NoSuchPaddingException;
        //  30     38     83     90     Ljava/security/InvalidKeyException;
        //  30     38     90     97     Ljava/security/InvalidAlgorithmParameterException;
        //  38     45     97     104    Ljava/io/UnsupportedEncodingException;
        //  45     51     104    111    Ljavax/crypto/IllegalBlockSizeException;
        //  45     51     111    118    Ljavax/crypto/BadPaddingException;
        // 
        // The error that occurred was:
        // 
        // java.lang.IndexOutOfBoundsException: Index 73 out of bounds for length 73
        //     at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:64)
        //     at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:70)
        //     at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:248)
        //     at java.base/java.util.Objects.checkIndex(Objects.java:372)
        //     at java.base/java.util.ArrayList.get(ArrayList.java:458)
        //     at com.strobel.decompiler.ast.AstBuilder.convertToAst(AstBuilder.java:3321)
        //     at com.strobel.decompiler.ast.AstBuilder.build(AstBuilder.java:113)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:211)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:125)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at com.strobel.decompiler.DecompilerDriver.decompileType(DecompilerDriver.java:330)
        //     at com.strobel.decompiler.DecompilerDriver.decompileJar(DecompilerDriver.java:251)
        //     at com.strobel.decompiler.DecompilerDriver.main(DecompilerDriver.java:126)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
}
