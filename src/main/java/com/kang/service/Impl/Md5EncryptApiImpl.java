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

import com.kang.entity.Md5Entity;
import com.kang.service.Md5EncryptApi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Md5EncryptApiImpl implements Md5EncryptApi {
    public Md5Entity CMD5_url_api(Md5Entity md5_entity) {
        md5_entity.setUrl_api(md5_entity.getUrl_api() + "api.ashx?email=" + md5_entity.getEmail_text() + "&key=" + md5_entity.getKey_text() + "&hash=" + md5_entity.getInputString());
        md5_entity.setOutputString("查询失败，查看日志");
        String log = "";
        try {
            URL url = new URL(md5_entity.getUrl_api());
            HttpURLConnection connectionGet = (HttpURLConnection) url.openConnection();

            connectionGet.setRequestMethod("GET");
            int responseCode = connectionGet.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader inGet = new BufferedReader(new InputStreamReader(connectionGet.getInputStream()));
                String inputLine;
                StringBuffer responseGet = new StringBuffer();
                while ((inputLine = inGet.readLine()) != null) {
                    responseGet.append(inputLine);
                }
                inGet.close();
                switch (responseGet.toString().trim()) {
                    case "CMD5-ERROR:0" -> {
                        System.out.println("解密失败");
                        log = log + "解密失败\n\r";
                    }
                    case "CMD5-ERROR:-1" -> {
                        System.out.println("无效的用户名密码");
                        log = log + "无效的用户名密码\n\r";
                    }
                    case "CMD5-ERROR:-2" -> {
                        System.out.println("余额不足");
                        log = log + "余额不足\n\r";
                    }
                    case "CMD5-ERROR:-3" -> {
                        System.out.println("解密服务器故障");
                        log = log + "解密服务器故障\n\r";
                    }
                    case "CMD5-ERROR:-4" -> {
                        System.out.println("不识别的密文");
                        log = log + "不识别的密文\n\r";
                    }
                    case "CMD5-ERROR:-7" -> {
                        System.out.println("不支持的类型");
                        log = log + "不支持的类型\n\r";
                    }
                    case "CMD5-ERROR:-8" -> {
                        System.out.println("api权限被禁止");
                        log = log + "api权限被禁止\n\r";
                    }
                    case "CMD5-ERROR:-9" -> {
                        System.out.println("条数超过200条");
                        log = log + "条数超过200条\n\r";
                    }
                    case "CMD5-ERROR:-9999" -> {
                        System.out.println("其它错误");
                        log = log + "其它错误\n\r";
                    }
                    default -> {
                        log = log + "查询成功\n\r";
                        md5_entity.setOutputString(responseGet.toString().trim());
                    }
                }
            } else {
                log = log + "服务器未响应\n\r";
                System.out.println("服务器未响应");
            }
        } catch (Exception e) {
            log = log + "服务器异常\n\r";
            throw new RuntimeException(e);
        }
        md5_entity.setLog_Text(log);
        return md5_entity;
    }

    public Md5Entity CMD5_url(Md5Entity md5_entity) {
        String log = "";
        md5_entity.setOutputString("查询失败，查看日志");
        try {
            // 设置请求的URL
            URL url = new URL(md5_entity.getUrl_api());
            HttpURLConnection connectionPost = (HttpURLConnection) url.openConnection();
            connectionPost.setRequestMethod("POST");
            HttpConnection(connectionPost);
            connectionPost.setDoOutput(true);
            // 构建请求参数

            // 将参数写入请求体
            OutputStream os = connectionPost.getOutputStream();
            os.write(requestGet(md5_entity.getInputString(), md5_entity.getUrl_api()).getBytes());
            os.flush();
            os.close();
            // 发送请求并获取响应
            int responseCode = connectionPost.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // 读取响应内容
                BufferedReader inPost = new BufferedReader(new InputStreamReader(connectionPost.getInputStream()));
                String inputLine;
                StringBuffer responsePost = new StringBuffer();
                while ((inputLine = inPost.readLine()) != null) {
                    responsePost.append(inputLine);
                }
                inPost.close();
                String regex = "<span\\s*id=\"LabelAnswer\"[^>]*>(.*?)</span>";
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(responsePost.toString());
                // 提取匹配的字符串
                if (matcher.find()) {
                    String str = new String(matcher.group(1).getBytes(), StandardCharsets.UTF_8);
                    if (str.contains("未查到") || str.contains("验证码错误") || str.contains("登录")) {
                        log = log + "cmd5平台：" + new String(matcher.group(1).getBytes(), StandardCharsets.UTF_8) + "\n\r";
                    } else {
                        log = log + "查询成功\n\r";
                        md5_entity.setOutputString(str);
                    }
                } else {
                    log = log + "未知错误\n\r";
                }
            } else {
                log = log + "服务器未响应\n\r";
            }
        } catch (Exception e) {
            log = log + "服务器错误\n\r";
        }
        md5_entity.setLog_Text(log);
        return md5_entity;
    }

    public String requestGet(String inputString, String link_url) throws IOException {
        URL url = new URL(link_url);
        // 创建连接并设置请求属性
        HttpURLConnection connectionGet = (HttpURLConnection) url.openConnection();

        connectionGet.setRequestMethod("GET");
        HttpConnection(connectionGet);

        int responseCode = connectionGet.getResponseCode();

        // 读取响应内容
        BufferedReader inGet = new BufferedReader(new InputStreamReader(connectionGet.getInputStream()));
        String inputLine;
        StringBuffer responseGet = new StringBuffer();
        while ((inputLine = inGet.readLine()) != null) {
            responseGet.append(inputLine);
        }
        inGet.close();

        String __EVENTTARGET = extractValue(responseGet.toString(), "__EVENTTARGET");
        String __EVENTARGUMENT = extractValue(responseGet.toString(), "__EVENTARGUMENT");
        String __VIEWSTATE = extractValue(responseGet.toString(), "__VIEWSTATE");
        String __VIEWSTATEGENERATOR = extractValue(responseGet.toString(), "__VIEWSTATEGENERATOR");
        String $HiddenField1 = extractValue(responseGet.toString(), "ctl00\\$ContentPlaceHolder1\\$HiddenField1");
        String $HiddenField2 = extractValue(responseGet.toString(), "ctl00\\$ContentPlaceHolder1\\$HiddenField2");
        __VIEWSTATE = URLEncoder.encode(__VIEWSTATE, "UTF-8");
        $HiddenField2 = URLEncoder.encode($HiddenField2, "UTF-8");

        return "__EVENTTARGET=" + __EVENTTARGET + "&__EVENTARGUMENT=" + __EVENTARGUMENT + "&__VIEWSTATE=" + __VIEWSTATE + "&__VIEWSTATEGENERATOR=" + __VIEWSTATEGENERATOR + "&ctl00%24ContentPlaceHolder1%24TextBoxInput=" + inputString + "&ctl00%24ContentPlaceHolder1%24InputHashType=md5&ctl00%24ContentPlaceHolder1%24Button1=%E6%9F%A5%E8%AF%A2&ctl00%24ContentPlaceHolder1%24HiddenField1=" + $HiddenField1 + "&ctl00%24ContentPlaceHolder1%24HiddenField2=" + $HiddenField2;
    }

    public void HttpConnection(HttpURLConnection connection) {
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        connection.setRequestProperty("Referer", "https://www.cmd5.com/");
        connection.setRequestProperty("Origin", "https://www.cmd5.com");
        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:109.0) Gecko/20100101 Firefox/112.0");
        connection.setRequestProperty("Sec-Fetch-Dest", "document");
        connection.setRequestProperty("Sec-Fetch-Mode", "navigate");
        connection.setRequestProperty("Sec-Fetch-Site", "same-origin");
        connection.setRequestProperty("Sec-Fetch-User", "?1");
    }

    public String extractValue(String html, String name) {
        String regex = "<input type=\"hidden\" name=\"" + name + "\" id=\"[^\"]*\" value=\"([^\"]*)\"\\s*/?>";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(html);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return "";
    }
}
