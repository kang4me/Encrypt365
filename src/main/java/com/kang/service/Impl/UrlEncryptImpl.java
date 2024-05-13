/*
 * @Author: Kang
 * @Version: 1.0
 * Copyright (c) 2024. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.kang.service.Impl;

import cn.hutool.core.util.URLUtil;
import com.kang.entity.UrlEntity;
import com.kang.service.UrlEncrypt;

import java.nio.charset.Charset;


public class UrlEncryptImpl implements UrlEncrypt {

    public String urlEncode(String inputString, Charset charSet){
        return URLUtil.encode(inputString,charSet);
    }
    public String urlEncodeAll(String inputString,Charset charSet){
        return URLUtil.encodeAll(inputString,charSet);
    }
    public String urlEncodeAlltoA_Z(String inputString,Charset charSet){
        StringBuilder encodedString = new StringBuilder();

        for (char c : inputString.toCharArray()) {
            encodedString.append('%');
            encodedString.append(String.format("%02X", (int) c));
        }
        return encodedString.toString();
    }
    public String urlDecode(String inputString,Charset charSet){
        return URLUtil.decode(inputString,charSet);
    }
}
