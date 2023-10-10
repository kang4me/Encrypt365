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
import com.kang.service.RsaCreate;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.openssl.jcajce.JcaPEMWriter;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemWriter;

import java.io.IOException;
import java.io.StringWriter;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;

public class RsaCreateImpl implements RsaCreate {
    
    /**
     * Create
     * @param rsaEntity
     * @return com.kang.entity.RsaEntity
     * @Author: Kang on 2023/10/10 14:27
     * RSA生成
     */
    public RsaEntity Create(RsaEntity rsaEntity) throws NoSuchAlgorithmException {
        KeyPair keyPair = generateRSAKeyPair(rsaEntity.getBit());
        rsaEntity.setPublicKeyApi(keyPair.getPublic());
        rsaEntity.setPrivateKeyApi(keyPair.getPrivate());
        return rsaEntity;
    }
    
    /**
     * generateRSAKeyPair
     * @param keySize
     * @return java.security.KeyPair
     * @Author: Kang on 2023/10/10 14:27
     * 设置生成RSA密钥对的大小
     */
    public KeyPair generateRSAKeyPair(int keySize) throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(keySize);
        return keyPairGenerator.generateKeyPair();
    }

    /**
     * publicKeyToPEM
     * @param publicKey
     * @return java.lang.String
     * @Author: Kang on 2023/10/10 14:28
     * PKCS#1 初始化公钥
     */
    public String publicKeyToPEM(PublicKey publicKey) throws Exception {
        SubjectPublicKeyInfo publicKeyInfo = SubjectPublicKeyInfo.getInstance(publicKey.getEncoded());
        return convertToPEM(publicKeyInfo);
    }

    /**
     * privateKeyToPEM
     * @param privateKey
     * @return java.lang.String
     * @Author: Kang on 2023/10/10 14:33
     * PKCS#1 初始化秘钥
     */
    public String privateKeyToPEM(PrivateKey privateKey) throws Exception {
        PrivateKeyInfo privateKeyInfo = PrivateKeyInfo.getInstance(privateKey.getEncoded());
        return convertToPEM(privateKeyInfo);
    }

    private String convertToPEM(Object obj) throws Exception {
        StringWriter sw = new StringWriter();
        try (JcaPEMWriter pemWriter = new JcaPEMWriter(sw)) {
            pemWriter.writeObject(obj);
        }
        return sw.toString();
    }

    /**
     * getPrivateKeyAsPem
     * @param privateKey
     * @return java.lang.String
     * @Author: Kang on 2023/10/10 14:33
     * PKCS#8 初始化秘钥
     */
    public String getPrivateKeyAsPem(PrivateKey privateKey) throws IOException {
        StringWriter writer = new StringWriter();
        PemWriter pemWriter = new PemWriter(writer);
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(privateKey.getEncoded());
        pemWriter.writeObject(new PemObject("PRIVATE KEY", pkcs8EncodedKeySpec.getEncoded()));
        pemWriter.flush();
        pemWriter.close();
        return writer.toString();
    }

    /**
     * getPublicKeyAsPem
     * @param publicKey
     * @return java.lang.String
     * @Author: Kang on 2023/10/10 14:33
     * PKCS#8 初始化公钥
     */
    public String getPublicKeyAsPem(PublicKey publicKey) throws IOException {
        StringWriter writer = new StringWriter();
        PemWriter pemWriter = new PemWriter(writer);
        pemWriter.writeObject(new PemObject("PUBLIC KEY", publicKey.getEncoded()));
        pemWriter.flush();
        pemWriter.close();
        return writer.toString();
    }

}
