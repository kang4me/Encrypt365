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

import com.kang.entity.RsaEntity;

import java.security.PrivateKey;
import java.security.PublicKey;

public interface RsaEncrypt {
    public RsaEntity selecteEncodeMode(Object object);
    public RsaEntity selecteDecodeMode(Object object);
    public PrivateKey getPrivateKeyFromPEM(String pemPrivateKey) throws Exception;
    public PublicKey getPublicKeyFromPEM(String pemPublicKey) throws Exception;
    public byte[] encryptWithPublicKey(PublicKey publicKey, RsaEntity rsaEntity) throws Exception;
    public String decodeWithPublicKey(PublicKey publicKey,RsaEntity rsaEntity) throws Exception;
    public byte[] encryptWithPrivateKey(PrivateKey privateKey,RsaEntity rsaEntity) throws Exception;
    public String decodeWithPrivateKey(PrivateKey privateKey,RsaEntity rsaEntity) throws Exception;
}
