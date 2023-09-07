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

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimplePBEConfig;
public class AESECBZeroPaddingExample {
    public static void main(String[] args) {
        try {
            // 设置秘钥
            String key = "1234567890123456";

            // 设置字符集为gb2312
            System.setProperty("jasypt.encryptor.password.charset", "gb2312");

            // 初始化Jasypt加密器
            StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
            SimplePBEConfig config = new SimplePBEConfig();

            config.setPassword(key);
            encryptor.setConfig(config);

            // 加密
            String plainText = "Hello";
            String encryptedText = encryptor.encrypt(plainText);
            System.out.println("Encrypted text: " + encryptedText);

            // 解密
            String decryptedText = encryptor.decrypt(encryptedText);
            System.out.println("Decrypted text: " + decryptedText);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
