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

public class Md5Entity {
    private String inputString;
    private String outputString;
    private String Email_text;
    private String Key_text;
    private String url_api;
    private String Log_Text;
    private int Hash_ComboBox;

    public String getInputString() {
        return inputString;
    }

    public void setInputString(String inputString) {
        this.inputString = inputString;
    }

    public String getOutputString() {
        return outputString;
    }

    public void setOutputString(String outputString) {
        this.outputString = outputString;
    }

    public String getEmail_text() {
        return Email_text;
    }

    public void setEmail_text(String email_text) {
        Email_text = email_text;
    }

    public String getKey_text() {
        return Key_text;
    }

    public void setKey_text(String key_text) {
        Key_text = key_text;
    }

    public String getUrl_api() {
        return url_api;
    }

    public void setUrl_api(String url_api) {
        this.url_api = url_api;
    }

    public String getLog_Text() {
        return Log_Text;
    }

    public void setLog_Text(String log_Text) {
        Log_Text = log_Text;
    }

    public int getHash_ComboBox() {
        return Hash_ComboBox;
    }

    public void setHash_ComboBox(int hash_ComboBox) {
        Hash_ComboBox = hash_ComboBox;
    }
}
