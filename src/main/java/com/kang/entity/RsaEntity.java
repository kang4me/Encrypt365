/*
 * @Author: Kang
 * @Version: 1.0
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.kang.entity;

import java.security.PrivateKey;
import java.security.PublicKey;

public class RsaEntity {
    private String publicKey;
    private String privateKey;
    private PublicKey publicKeyApi;
    private PrivateKey privateKeyApi;
    private int Bit;
    private String outPut;
    private String key;
    private String padding;
    private String charset;
    private String textValue;
    private String keyFormat;
    private String outputValue;
    private boolean selectedRadio;

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public PublicKey getPublicKeyApi() {
        return publicKeyApi;
    }

    public void setPublicKeyApi(PublicKey publicKeyApi) {
        this.publicKeyApi = publicKeyApi;
    }

    public PrivateKey getPrivateKeyApi() {
        return privateKeyApi;
    }

    public void setPrivateKeyApi(PrivateKey privateKeyApi) {
        this.privateKeyApi = privateKeyApi;
    }

    public int getBit() {
        return Bit;
    }

    public void setBit(int bit) {
        Bit = bit;
    }

    public String getOutPut() {
        return outPut;
    }

    public void setOutPut(String outPut) {
        this.outPut = outPut;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getPadding() {
        return padding;
    }

    public void setPadding(String padding) {
        this.padding = padding;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public String getTextValue() {
        return textValue;
    }

    public void setTextValue(String textValue) {
        this.textValue = textValue;
    }

    public String getKeyFormat() {
        return keyFormat;
    }

    public void setKeyFormat(String keyFormat) {
        this.keyFormat = keyFormat;
    }

    public String getOutputValue() {
        return outputValue;
    }

    public void setOutputValue(String outputValue) {
        this.outputValue = outputValue;
    }

    public boolean isSelectedRadio() {
        return selectedRadio;
    }

    public void setSelectedRadio(boolean selectedRadio) {
        this.selectedRadio = selectedRadio;
    }
}
