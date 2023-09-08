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

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
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
            InputStream in = new BufferedInputStream (new FileInputStream("Encrypt365.properties"));
            prop.load(in);     ///加载属性列表
            Iterator<String> it=prop.stringPropertyNames().iterator();
            Map<String,String> map = new HashMap<>();
            for (String s : prop.stringPropertyNames()) {
                map.put(s,prop.getProperty(s));
            }
            System.out.println(map.get("key"));
            in.close();
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
}
