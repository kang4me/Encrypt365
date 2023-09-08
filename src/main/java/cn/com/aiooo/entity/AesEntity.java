/*
 * @Author: Kang
 * @Version: 1.0
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package cn.com.aiooo.entity;

import cn.hutool.crypto.Mode;
import cn.hutool.crypto.Padding;

public class AesEntity {
    private Mode mode;
    private Padding padding;
    private int DataBlock;//size
    private String key;
    private String iv;
    private String Output;
    private String charset;
    private String plainText;
    private byte[] encryptedBytes;

    public Mode getMode() {
        return mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public Padding getPadding() {
        return padding;
    }

    public void setPadding(Padding padding) {
        this.padding = padding;
    }

    public int getDataBlock() {
        return DataBlock;
    }

    public void setDataBlock(int dataBlock) {
        DataBlock = dataBlock;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getIv() {
        return iv;
    }

    public void setIv(String iv) {
        this.iv = iv;
    }

    public String getOutput() {
        return Output;
    }

    public void setOutput(String output) {
        Output = output;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public String getPlainText() {
        return plainText;
    }

    public void setPlainText(String plainText) {
        this.plainText = plainText;
    }

    public byte[] getEncryptedBytes() {
        return encryptedBytes;
    }

    public void setEncryptedBytes(byte[] encryptedBytes) {
        this.encryptedBytes = encryptedBytes;
    }

}
