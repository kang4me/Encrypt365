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

import com.kang.entity.BaseEntity;
import com.kang.entity.RsaEntity;
import com.kang.service.RsaEncrypt;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

import javax.crypto.Cipher;
import java.nio.charset.Charset;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Objects;

public class RsaEncryptImpl implements RsaEncrypt {

    public RsaEntity selecteEncodeMode(Object object){
        RsaEntity rsaEntity = (RsaEntity) object;
        try {
            byte[] encryptedData;
            if (rsaEntity.isSelectedRadio()) {
                encryptedData = encryptWithPublicKey(getPublicKeyFromPEM(rsaEntity.getPublicKey()), rsaEntity);
            }else {
                encryptedData = encryptWithPrivateKey(getPrivateKeyFromPEM(rsaEntity.getPublicKey()), rsaEntity);
            }

            if (rsaEntity.getOutPut().equals("0")) {
                rsaEntity.setOutputValue(new String(Objects.requireNonNull(Base64.encodeBase64(encryptedData))));
            } else if(rsaEntity.getOutPut().equals("1")){
                rsaEntity.setOutputValue(new String(Hex.encodeHex(encryptedData)));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return rsaEntity;
    }
    public RsaEntity selecteDecodeMode(Object object){
        RsaEntity rsaEntity = (RsaEntity) object;
        try {
            if (rsaEntity.isSelectedRadio()) {
                rsaEntity.setOutputValue(decodeWithPublicKey(getPublicKeyFromPEM(rsaEntity.getPublicKey()), rsaEntity));
            } else {
                rsaEntity.setOutputValue(decodeWithPrivateKey(getPrivateKeyFromPEM(rsaEntity.getPublicKey()), rsaEntity));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return rsaEntity;
    }

    /**
     * 过滤秘钥头尾部
     * @param pemPrivateKey
     * @return
     * @throws Exception
     */
    public PrivateKey getPrivateKeyFromPEM(String pemPrivateKey) throws Exception {
        // 去除PEM格式的头尾和换行符
        byte[] privateKeyBytes = Base64.decodeBase64(pemPrivateKey
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replace("-----BEGIN RSA PRIVATE KEY-----", "")
                .replace("-----END RSA PRIVATE KEY-----", "")
                .replaceAll("\\s+", ""));
        // 使用PKCS8EncodedKeySpec构建RSA私钥
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(keySpec);
    }

    /**
     * 过滤公钥头尾部
     * @param pemPublicKey
     * @return
     * @throws Exception
     */
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

    /**
     * 公钥加密数据
     * @param publicKey
     * @param rsaEntity
     * @return
     * @throws Exception
     */
    public byte[] encryptWithPublicKey(PublicKey publicKey,RsaEntity rsaEntity) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(rsaEntity.getTextValue().getBytes(Charset.forName(rsaEntity.getCharset())));
    }

    /**
     * 公钥解密数据
     * @param publicKey
     * @param rsaEntity
     * @return
     * @throws Exception
     */
    public String decodeWithPublicKey(PublicKey publicKey,RsaEntity rsaEntity) throws Exception {
        // 初始化RSA解密器
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, publicKey);

        // 要解密的密文（Base64编码）
        byte[] encryptedTextBytes = Base64.decodeBase64(rsaEntity.getTextValue());

        // 执行解密操作
        byte[] decryptedBytes = cipher.doFinal(encryptedTextBytes);

        // 将解密后的字节数组转换为字符串
        return new String(decryptedBytes, Charset.forName(rsaEntity.getCharset()));
    }

    /**
     * 私钥加密数据
     * @param privateKey
     * @param rsaEntity
     * @return
     * @throws Exception
     */
    public byte[] encryptWithPrivateKey(PrivateKey privateKey,RsaEntity rsaEntity) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        return cipher.doFinal(rsaEntity.getTextValue().getBytes(Charset.forName(rsaEntity.getCharset())));
    }

    /**
     * 私钥解密数据
     * @param privateKey
     * @param rsaEntity
     * @return
     * @throws Exception
     */
    public String decodeWithPrivateKey(PrivateKey privateKey,RsaEntity rsaEntity) throws Exception{
        // 初始化RSA解密器
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);

        // 要解密的密文（Base64编码）
        byte[] encryptedTextBytes = Base64.decodeBase64(rsaEntity.getTextValue());

        // 执行解密操作
        byte[] decryptedBytes = cipher.doFinal(encryptedTextBytes);

        return new String(decryptedBytes, Charset.forName(rsaEntity.getCharset()));
    }
}
