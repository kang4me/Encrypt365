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

public interface Md5Encrypt {
    public String md5Encode32(String inputString);
    public String md5Encode16(String inputString);
    public String sha1Encode(String inputString);
    public String sha224Encode(String inputString);
    public String sha256Encode(String inputString);
    public String sha384Encode(String inputString);
    public String sha512Encode(String inputString);

}
