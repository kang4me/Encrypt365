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

import com.kang.entity.RsaEntity;
import com.kang.service.RsaPrivateKeyEncrypt;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.nio.charset.Charset;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;

public class RsaPrivateKeyEncryptImpl implements RsaPrivateKeyEncrypt {

    public PrivateKey getPrivateKeyFromPEM(String pemPrivateKey) throws Exception {
        // 去除PEM格式的头尾和换行符
        byte[] privateKeyBytes = Base64.decodeBase64(pemPrivateKey
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replaceAll("\\s+", ""));
        // 使用PKCS8EncodedKeySpec构建RSA私钥
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(keySpec);
    }
    // 提供的PEM格式私钥（未受密码保护）
    public byte[] encryptWithPrivateKey(PrivateKey privateKey,RsaEntity rsaEntity) throws Exception {
        // 初始化RSA加密器
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);

        return cipher.doFinal(rsaEntity.getText().getBytes(Charset.forName(rsaEntity.getCharset())));
    }
    public String decodeWithPrivateKey(PrivateKey privateKey,RsaEntity rsaEntity) throws Exception{
        // 初始化RSA解密器
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);

        // 要解密的密文（Base64编码）
        byte[] encryptedTextBytes = Base64.decodeBase64(rsaEntity.getText());

        // 执行解密操作
        byte[] decryptedBytes = cipher.doFinal(encryptedTextBytes);

        return new String(decryptedBytes, Charset.forName(rsaEntity.getCharset()));
    }

}
