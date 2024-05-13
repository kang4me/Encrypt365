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

import burp.api.montoya.MontoyaApi;
import cn.hutool.core.util.URLUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class cshi {
    public static void main(String[] args) {

        String originalString = "a' union select 1,''+(SELECT @@VERSION)+";
        String encodedString = encodeString(originalString);

        System.out.println("原始字符串: " + originalString);
        System.out.println("自定义 URI 编码后的字符串: " + encodedString);
    }

    public static String encodeString(String input) {
        StringBuilder encoded = new StringBuilder();
        for (char c : input.toCharArray()) {
            // 对每个字符进行 URI 编码
            encoded.append(encodeChar(c));
        }
        return encoded.toString();
    }

    public static String encodeChar(char c) {
        try {
            return java.net.URLEncoder.encode(String.valueOf(c), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            // 如果编码不支持，则直接返回原始字符
            return String.valueOf(c);
        }
    }
}
