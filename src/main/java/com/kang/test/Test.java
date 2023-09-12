/*
 * @Author: Kang
 * @Version: 1.0
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.kang.test;


import java.io.StringReader;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher;

import com.kang.entity.RsaEntity;
import com.kang.service.Impl.RsaPrivateKeyEncryptImpl;
import com.kang.service.RsaPrivateKeyEncrypt;
import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMDecryptorProvider;
import org.bouncycastle.openssl.PEMEncryptedKeyPair;
import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.bouncycastle.openssl.jcajce.JcePEMDecryptorProviderBuilder;

public class Test {
    public static void main(String[] args) throws Exception {
        RsaPrivateKeyEncrypt rsaPrivateKeyEncrypt = new RsaPrivateKeyEncryptImpl();
        RsaEntity rsaEntity = new RsaEntity();
        // 加载私钥（PEM格式或DER格式），并将其转换为PrivateKey对象
        String privateKeyPEM = "-----BEGIN PRIVATE KEY-----\n" +
                "MIIBVAIBADANBgkqhkiG9w0BAQEFAASCAT4wggE6AgEAAkEAzAqYDCuGLcNNf2c+\n" +
                "JpTInkoPwReSGdMAUab4Ii/s8NzYvV1BA57rNV8xukYVPDHo01co/SvrdFJY6l6J\n" +
                "2QTIiwIDAQABAkBRYwRlDlNWG6nk4KyUvRIMuWPxVFKNhHGDIEOnd1BefjIRf4zm\n" +
                "0NQyalIPMRKZUT3ic63Gr/GSOCu1qsx4jY4BAiEA9d3VkpGLuetLwpG9+86bKoy6\n" +
                "t1r7oCA50KNVip99FwECIQDUc3UrLRDmnXBlGwuaNm0Z7BGikNkQqCHqLh3dVlxL\n" +
                "iwIgSk22Y8s0rQVdKfodrmHsJtnM++i1LtlOX61dBr3YcgECIB+r3qnDHCPlEJ5h\n" +
                "+8bPmlAk+zQK9/Edv4CTw2v9teClAiEA05fLm3XMfIGqXBHdK7SF7T/Ln9bJrna4\n" +
                "qMe2lX4pII4=\n" +
                "-----END PRIVATE KEY-----";
        rsaEntity.setPrivateKey(privateKeyPEM);
        rsaEntity.setCharset("utf-8");
        rsaEntity.setText("SqU01PZaypvWMsCce8Tb58OFrd0115Q1nXRemVpD+lLP8V5usgcy2YmI1yV7F8Q0Sf5946rQPbeAHlf/2WVFpw==");


        System.out.println("解密后的文本: " + rsaPrivateKeyEncrypt.decodeWithPrivateKey(rsaPrivateKeyEncrypt.getPrivateKeyFromPEM(rsaEntity.getPrivateKey()),rsaEntity));
    }
}
