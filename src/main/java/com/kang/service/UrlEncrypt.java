/*
 * @Author: Kang
 * @Version: 1.0
 * Copyright (c) 2024. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.kang.service;

import java.nio.charset.Charset;

public interface UrlEncrypt {
    public String urlEncode(String inputString, Charset charSet);
    public String urlEncodeAll(String inputString,Charset charSet);
    public String urlEncodeAlltoA_Z(String inputString,Charset charSet);
    public String urlDecode(String inputString,Charset charSet);
}
