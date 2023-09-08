/*
 * @Author: Kang
 * @Version: 1.0
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package cn.com.aiooo.service;

import cn.com.aiooo.entity.Md5Entity;

import java.io.IOException;
import java.net.HttpURLConnection;

public interface Md5EncryptApi {
    public Md5Entity CMD5_url_api(Md5Entity md5_entity);
    public Md5Entity CMD5_url(Md5Entity md5_entity);
    public String requestGet(String inputString, String link_url) throws IOException;
    public void HttpConnection(HttpURLConnection connection);
    public String extractValue(String html, String name);
}
