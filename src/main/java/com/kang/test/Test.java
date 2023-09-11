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


import org.bouncycastle.crypto.digests.*;
import org.bouncycastle.util.encoders.Hex;


public class Test {

    public static void main(String[] args) {
        String key = "123";
        SHA384Digest sha384Digest = new SHA384Digest();
        sha384Digest.update(key.getBytes(),0, key.length());
        byte[] sha384Bytes = new byte[sha384Digest.getDigestSize()];
        sha384Digest.doFinal(sha384Bytes, 0);
        System.out.printf(new String(Hex.encode(sha384Bytes)));
    }
}
