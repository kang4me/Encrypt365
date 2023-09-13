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

import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

public interface RsaCreate {
    public RsaEntity Create(RsaEntity rsaEntity) throws NoSuchAlgorithmException;
    public String publicKeyToPEM(PublicKey publicKey) throws Exception;
    public String privateKeyToPEM(PrivateKey privateKey) throws Exception;
}
