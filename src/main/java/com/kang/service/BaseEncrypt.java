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

import com.kang.entity.BaseEntity;

public interface BaseEncrypt {
    public BaseEntity selecteEncodeMode(Object object);
    public BaseEntity selecteDecodeMode(Object object);
    public String base64_Encode(String inputString);
    public String base64_Decode(String encodedString);

    public String base32_Encode(String inputString);
    public byte[] base32_Decode(String inputString);

    public String base16_Encode(byte[] inputBytes);
    public byte[] base16_Decode(String inputString);

    public String base58_Encode(byte[] inputString);
    public byte[] base58_Decode(String inputString);

    public String base91_Encode(byte[] inputString);
    public byte[] base91_Decode(String inputString);
}
