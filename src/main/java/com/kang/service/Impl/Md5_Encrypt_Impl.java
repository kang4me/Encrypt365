/*
 * @Author: Kang
 * @Version: 1.0
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.kang.service.Impl;

import com.kang.service.Md5Encrypt;

import cn.hutool.crypto.digest.DigestUtil;
import org.bouncycastle.crypto.digests.SHA224Digest;
import org.bouncycastle.crypto.digests.SHA384Digest;
import org.bouncycastle.util.encoders.Hex;

public class Md5_Encrypt_Impl implements Md5Encrypt {


    public String md5Encode32(String inputString){
        return DigestUtil.md5Hex(inputString);
    }

    @Override
    public String md5Encode16(String inputString) {
        return DigestUtil.md5Hex16(inputString);
    }

    @Override
    public String sha1Encode(String inputString) {
        return DigestUtil.sha1Hex(inputString);
    }
    public String sha224Encode(String inputString){
        SHA224Digest sha224Digest = new SHA224Digest();
        sha224Digest.update(inputString.getBytes(),0,inputString.length());
        byte[] sha224Bytes = new byte[sha224Digest.getDigestSize()];
        sha224Digest.doFinal(sha224Bytes,0);
        return new String(Hex.encode(sha224Bytes));
    }
    @Override
    public String sha256Encode(String inputString) {
        return DigestUtil.sha256Hex(inputString);
    }
    @Override
    public String sha384Encode(String inputString){
        SHA384Digest sha384Digest = new SHA384Digest();
        sha384Digest.update(inputString.getBytes(),0, inputString.length());// 更新 Digest
        // 计算哈希值
        byte[] sha384Bytes = new byte[sha384Digest.getDigestSize()];
        sha384Digest.doFinal(sha384Bytes, 0);
        // 计算哈希值
        return new String(Hex.encode(sha384Bytes));
    }
    @Override
    public String sha512Encode(String inputString) {
        return DigestUtil.sha512Hex(inputString);
    }

    /**
     * 旧接口MD5加密接口
     * @param inputString
     * @return
     */
/*    public String MD5_Encode(String inputString) {
        String signature = "";
        try {
            //创建一个提供信息摘要算法的对象，初始化为md5算法对象
            MessageDigest md = MessageDigest.getInstance("MD5");
            //计算后获得字节数组
            byte[] bytes = md.digest(inputString.getBytes("utf-8"));

            StringBuffer md5str = new StringBuffer();
            //把数组每一字节换成16进制连成md5字符串
            int digital;
            for (int i = 0; i < bytes.length; i++) {
                digital = bytes[i];
                if (digital < 0) {
                    digital += 256;
                }
                if (digital < 16) {
                    md5str.append("0");
                }
                md5str.append(Integer.toHexString(digital));
                //把数组每一字节换成16进制连成md5字符串
                signature = md5str.toString();
            }
        } catch (Exception e) {

        }
        return signature;
    }*/

}
