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

import com.kang.entity.AesEntity;
import com.kang.service.Impl.AesEncryptImpl;
import cn.hutool.crypto.Mode;
import cn.hutool.crypto.Padding;
import org.apache.commons.codec.binary.Base64;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Properties;


public class Test {
//    public static void main(String[] args){
//        AesEntity aes_entity = new AesEntity();
//        aes_entity.setMode(Mode.CBC);
//        aes_entity.setPadding(Padding.NoPadding);
//        aes_entity.setPlainText("1231231231231234");
//        aes_entity.setKey("123");
//        aes_entity.setCharset("gb2312");
//        aes_entity.setDataBlock(16);
//        aes_entity.setIv("1231231231231234");
//
//        AesEncryptImpl aes_encrypt = new AesEncryptImpl(aes_entity);
//        System.out.println(new String(Base64.encodeBase64(aes_encrypt.AES_Encrypt_all())));
//    }
public static void main(String[] args) {
    Properties prop = new Properties();
    try{
        //读取属性文件a.properties
        InputStream in = new BufferedInputStream(new FileInputStream("config.properties"));
        prop.load(in);     ///加载属性列表
        Iterator<String> it=prop.stringPropertyNames().iterator();
        while(it.hasNext()){
            String key=it.next();
            System.out.println(key+":"+prop.getProperty(key));
        }
        in.close();

        ///保存属性到b.properties文件
        FileOutputStream oFile = new FileOutputStream("config.properties", true);//true表示追加打开
        prop.setProperty("phone", "10086");
        prop.store(oFile, "The New properties file");
        oFile.close();
    }
    catch(Exception e){
        System.out.println(e);
    }
}
}
