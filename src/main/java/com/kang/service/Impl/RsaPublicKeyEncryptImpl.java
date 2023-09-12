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
import com.kang.service.RsaPublicKeyEncrypt;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

public class RsaPublicKeyEncryptImpl implements RsaPublicKeyEncrypt {

    // 从PEM格式的公钥字符串中获取PublicKey对象
    public PublicKey getPublicKeyFromPEM(String pemPublicKey) throws Exception {
        byte[] publicKeyBytes = Base64.decodeBase64(pemPublicKey
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replaceAll("\\s+", "")
        );

        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(keySpec);
    }
    // 使用公钥加密数据
    public byte[] encryptWithPublicKey(PublicKey publicKey,RsaEntity rsaEntity) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(rsaEntity.getText().getBytes(Charset.forName(rsaEntity.getCharset())));
    }
    public String decodeWithPublicKey(PublicKey publicKey,RsaEntity rsaEntity) throws Exception {
        // 初始化RSA解密器
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, publicKey);

        // 要解密的密文（Base64编码）
        byte[] encryptedTextBytes = Base64.decodeBase64(rsaEntity.getText());

        // 执行解密操作
        byte[] decryptedBytes = cipher.doFinal(encryptedTextBytes);

        // 将解密后的字节数组转换为字符串
        return new String(decryptedBytes, Charset.forName(rsaEntity.getCharset()));
    }
}
