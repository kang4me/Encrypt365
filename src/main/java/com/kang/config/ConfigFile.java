/*
 * @Author: Kang
 * @Version: 1.0
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.kang.config;

import com.kang.entity.Md5Entity;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

public class ConfigFile {
    /**
     * getConfig
     * @param md5Entity
     * @return com.kang.entity.Md5Entity
     * @Author: Kang on 2023/10/10 14:05
     * 创建文件
     */
    public Md5Entity getConfig(Md5Entity md5Entity){
        String log = null;
        Properties prop = new Properties();
        InputStream in = null;
        try{
            //读取属性文件
            in = new BufferedInputStream(new FileInputStream("Encrypt365.properties"));
            prop.load(in);     ///加载属性列表
            Map<String,String> map = new HashMap<>();
            for (String s : prop.stringPropertyNames()) {
                map.put(s,prop.getProperty(s));
            }
            md5Entity.setEmail_text(map.get("email"));
            md5Entity.setKey_text(map.get("key"));
        } catch (Exception e){
            log = log + "创建配置文件异常！！";
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        md5Entity.setLog_Text(log);
        return md5Entity;
    }
    /**
     * save
     * @param md5Entity
     * @return java.lang.String
     * @Author: Kang on 2023/10/10 14:14
     * 保存文件
     */
    public String save(Md5Entity md5Entity){
        Properties prop = new Properties();
        ///保存属性到b.properties文件
        FileOutputStream oFile = null;//true表示追加打开
        String log;
        try {
            oFile = new FileOutputStream("Encrypt365.properties", false);
            prop.setProperty("email", md5Entity.getEmail_text());
            prop.setProperty("key", md5Entity.getKey_text());
            prop.store(oFile, "The New Encrypt365.properties file");
            oFile.close();
            log = "保存成功";
        } catch (IOException e) {
            log = "保存异常\n\r" + e;
        }
        return log;
    }
    /**
     * iniFile
     * @param
     * @return void
     * @Author: Kang on 2023/10/10 14:15
     * 初始化文件
     */
    public void iniFile(){
        Properties prop = new Properties();
        File file = new File("");
        String os = System.getProperty("os.name");
        if (os.toLowerCase().startsWith("win")) {
            file = new File("Encrypt365.properties");
        } else {
            String jarPath = file.getAbsolutePath();
            file = new File(jarPath.substring(0, jarPath.lastIndexOf("/")) + "/Encrypt365.properties");
        }
        try {
            if(file.createNewFile()) {
                FileOutputStream oFile = new FileOutputStream("Encrypt365.properties", false);//true表示追加打开
                prop.setProperty("email", "xxxxxxx@xxx.com");
                prop.setProperty("key", "xxxxxxxx");
                prop.store(oFile, "The Encrypt365.properties file");
                oFile.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
