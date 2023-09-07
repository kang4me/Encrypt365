/*
 * @Author: Kang
 * @Version: 1.0
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package cn.com.aiooo;

import cn.com.aiooo.AES.AES_ECB;
import cn.hutool.crypto.Mode;
import cn.hutool.crypto.Padding;
import cn.hutool.crypto.symmetric.AES;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;


import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.security.Security;


public class Test {
//    public static void main(String[] args) throws Exception {
//        AES_ECB aes_ecb = new AES_ECB("gb2312",null);
//        String text = "Hello, World!";
//        String key = "123";
//
//        System.out.println(new String(Base64.encodeBase64(aes_ecb.AES_ECB_ZeroP_encrypt(text, key, 16))));
//
//    }
public static void main(String[] args) {
    try {
        // 设置秘钥
        String key = "1234567890123456";

        // 初始化AES对象，指定模式为ECB和填充方式为PKCS5Padding
        AES aes = new AES(Mode.ECB, Padding.PKCS5Padding, key.getBytes(Charset.forName("gb2312")));

        // 加密
        String plainText = "你好，AES/ECB/PKCS5Padding!";
        byte[] encryptedBytes = aes.encrypt(plainText.getBytes(Charset.forName("gb2312")));

        // 将加密结果转换为Base64编码
        String encryptedText = aes.encryptBase64(plainText);

        System.out.println("Encrypted text: " + encryptedText);

        // 解密
        byte[] decryptedBytes = aes.decrypt(encryptedBytes);
        String decryptedText = new String(decryptedBytes);

        System.out.println("Decrypted text: " + decryptedText);
    } catch (Exception e) {
        e.printStackTrace();
    }
}
}
