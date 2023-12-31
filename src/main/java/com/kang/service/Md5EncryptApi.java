/*
 * @Author: Kang
 * @Version: 1.0
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.kang.service;

import com.kang.entity.HashEntity;

import java.io.IOException;
import java.net.HttpURLConnection;

public interface Md5EncryptApi {
    public HashEntity selecteDecodeMode(Object object);
    public HashEntity CMD5_url_api(HashEntity md5_entity);
    public HashEntity CMD5_url(HashEntity md5_entity);
    public String requestGet(String inputString, String link_url) throws IOException;
    public void HttpConnection(HttpURLConnection connection);
    public String extractValue(String html, String name);
}
