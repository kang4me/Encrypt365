/*
 * @Author: Kang
 * @Version: 1.0
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package cn.com.aiooo.AES;

import burp.api.montoya.MontoyaApi;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.security.Security;

public class AES_ECB {

    static {
        Security.addProvider(new BouncyCastleProvider());
    }
    private Charset CHARSET = null;
    private MontoyaApi api;
    public AES_ECB(String charset,MontoyaApi api) {
        this.api = api;
        this.CHARSET = Charset.forName(charset);
    }

    //PKCS5Padding
    public byte[] AES_ECB_PKCS5_encrypt(String plainText, String key, int size){

        SecretKeySpec secretKey = new SecretKeySpec(padKeyTo128_192_256Bits( key, size), "AES");
        Cipher cipher = null;
        byte[] bt = null;
        try {
            cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            bt = cipher.doFinal(plainText.getBytes(CHARSET));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bt;
    }
    //PKCS5Padding
    public String AES_ECB_PKCS5_decrypt(byte[] encryptedBytes, String key,int size){
        SecretKeySpec secretKey = new SecretKeySpec(padKeyTo128_192_256Bits( key, size), "AES");

        Cipher cipher = null;
        byte[] decryptedBytes = null;
        try {
            cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);

            decryptedBytes = cipher.doFinal(encryptedBytes);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new String(decryptedBytes, CHARSET);
    }

    //PKCS7Padding
    public byte[] AES_ECB_PKCS7_encrypt(String input, String key, int size){
        byte[] crypted = null;
        try {
            SecretKeySpec skey = new SecretKeySpec(padKeyTo128_192_256Bits( key, size), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS7Padding");
            cipher.init(Cipher.ENCRYPT_MODE, skey);
            crypted = cipher.doFinal(input.getBytes(CHARSET));
        } catch (Exception e) {
            //api.logging().logToError(e+"\n该问题暂时无法处理");
            e.printStackTrace();
        }
        return crypted;
    }

    //PKCS7Padding
    public String AES_ECB_PKCS7_decrypt(byte[] input, String key, int size) {
        byte[] output = null;
        try {
            SecretKeySpec skey = new SecretKeySpec(padKeyTo128_192_256Bits( key, size), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS7Padding");
            cipher.init(Cipher.DECRYPT_MODE, skey);
            output = cipher.doFinal(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new String(output);
    }

    //ZeroPadding
    public byte[] AES_ECB_ZeroP_encrypt(String input, String key, int size){
        byte[] encryptedBytes = null;
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(padKeyTo128_192_256Bits( key, size), "AES");
            Cipher encryptCipher = Cipher.getInstance("AES/ECB/ZeroPadding");
            encryptCipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            encryptedBytes = encryptCipher.doFinal(input.getBytes(CHARSET));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encryptedBytes;
    }



    //16进制转byte
    public byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toLowerCase();
        final byte[] byteArray = new byte[hexString.length() >> 1];
        int index = 0;
        for (int i = 0; i < hexString.length(); i++) {
            if (index  > hexString.length() - 1) {
                return byteArray;
            }
            byte highDit = (byte) (Character.digit(hexString.charAt(index), 16) & 0xFF);
            byte lowDit = (byte) (Character.digit(hexString.charAt(index + 1), 16) & 0xFF);
            byteArray[i] = (byte) (highDit << 4 | lowDit);
            index += 2;
        }
        return byteArray;
    }
    //byte转16进制
    public String byteArrayToHexStr(byte[] byteArray) {
        if (byteArray == null) {
            return null;
        }
        char[] hexArray = "0123456789ABCDEF".toCharArray();
        char[] hexChars = new char[byteArray.length * 2];
        for (int j = 0; j < byteArray.length; j++) {
            int v = byteArray[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    public byte[] padKeyTo128_192_256Bits(String originalKey, int size) {
        byte[] keyBytes = originalKey.getBytes(CHARSET);

        // 创建一个16字节的数组，用于存储填充后的秘钥
        byte[] paddedKey = new byte[size];

        // 将原始秘钥的字节循环复制到填充后的秘钥数组，同时处理超出16字节长度的情况
        for (int i = 0; i < size; i++) {
            if (i < keyBytes.length) {
                paddedKey[i] = keyBytes[i];
            } else {
                paddedKey[i] = (byte) 0x00; // 使用0x00填充
            }
        }
        return paddedKey;
    }
}
