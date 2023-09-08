/*
 * @Author: Kang
 * @Version: 1.0
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.kang.service.Impl;

import com.kang.entity.AesEntity;
import com.kang.service.AesEncrypt;
import cn.hutool.crypto.Mode;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.AES;

import java.nio.charset.Charset;

public class AesEncryptImpl implements AesEncrypt {
    private AesEntity aes_entity;
    private AES aes;

    public AesEncryptImpl(AesEntity aes_entity) {
        SecureUtil.disableBouncyCastle();
        this.aes_entity = aes_entity;
        if (aes_entity.getMode() == Mode.ECB) {
            aes = new AES(aes_entity.getMode(), aes_entity.getPadding(), padKeyTo128_192_256Bits(aes_entity.getKey(), aes_entity.getDataBlock()));
        }else {
            aes = new AES(aes_entity.getMode(), aes_entity.getPadding(), padKeyTo128_192_256Bits( aes_entity.getKey(), aes_entity.getDataBlock()),padKeyTo128_192_256Bits( aes_entity.getIv(), aes_entity.getDataBlock()));
        }
    }

    /**
     * 采用第三方库hutool
     * @return
     */
    public byte[] AES_Encrypt_all(){
        return aes.encrypt(aes_entity.getPlainText().getBytes(Charset.forName(aes_entity.getCharset())));
    }

    /**
     * 采用第三方库hutool
     * @return
     */
    public String AES_Decrypt_all(){
        return new String(aes.decrypt(aes_entity.getEncryptedBytes()), Charset.forName(aes_entity.getCharset()));
    }

    /**
     * 封印
     */
/*    //PKCS7Padding
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
    }*/
    /**
     * 封印；
     */

    /**
     * 自写填充方法
     * 不满足秘钥长度规则时采用0x00填补
     * @param originalKey
     * @param size
     * @return
     */
    public byte[] padKeyTo128_192_256Bits(String originalKey, int size) {
        byte[] keyBytes = originalKey.getBytes(Charset.forName(aes_entity.getCharset()));

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
