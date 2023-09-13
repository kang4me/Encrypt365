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

import java.io.StringWriter;
import java.security.*;

public class RsaCreateImpl implements RsaCreate {
    public RsaEntity Create(RsaEntity rsaEntity) throws NoSuchAlgorithmException {
        KeyPair keyPair = generateRSAKeyPair(rsaEntity.getBit());
        rsaEntity.setPublicKeyApi(keyPair.getPublic());
        rsaEntity.setPrivateKeyApi(keyPair.getPrivate());
        return rsaEntity;
    }
    public KeyPair generateRSAKeyPair(int keySize) throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(keySize);
        return keyPairGenerator.generateKeyPair();
    }

    public String publicKeyToPEM(PublicKey publicKey) throws Exception {
        SubjectPublicKeyInfo publicKeyInfo = SubjectPublicKeyInfo.getInstance(publicKey.getEncoded());
        return convertToPEM(publicKeyInfo);
    }

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

}
