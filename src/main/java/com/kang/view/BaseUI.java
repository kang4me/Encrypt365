/*
 * @Author: Kang
 * @Version: 1.0
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.kang.view;

import burp.api.montoya.MontoyaApi;
import com.kang.service.BaseEncrypt;
import com.kang.service.Impl.BaseEncryptImpl;

import javax.swing.*;
import java.awt.*;
import java.nio.charset.StandardCharsets;

public class BaseUI {
    public JPanel Base;
    private JComboBox<String> base_ComboBox;
    private JButton encode_Button;
    private JTextArea base_encode;
    private JTextArea base_decode;
    private JButton decode_Button;
    private JScrollPane JS;
    private final BaseEncrypt base_encrypt = new BaseEncryptImpl();

    public BaseUI(MontoyaApi api) {
        String[] str = {"Base64编码", "Base32编码", "Base16编码", "Base58编码", "Base91编码"};
        for (String value : str) {
            base_ComboBox.addItem(value);
        }

        this.encode_Button.addActionListener((e) -> {
            encode_Button();
        });
        this.decode_Button.addActionListener((e) -> {
            decode_Button();
        });
    }

    private void encode_Button() {
        String result = "";
        String inputString = this.base_encode.getText();
        switch (base_ComboBox.getSelectedIndex()) {
            case 0 ->
                //Base64
                    result = base_encrypt.base64_Encode(inputString);
            case 1 ->
                //Base32
                    result = base_encrypt.base32_Encode(inputString);
            case 2 -> {
                //Base16
                byte[] inputBytes = inputString.getBytes(StandardCharsets.UTF_8);
                result = base_encrypt.base16_Encode(inputBytes);
            }
            case 3 ->
                //Base58
                    result = base_encrypt.base58_Encode(inputString.getBytes());
            case 4 ->
                //Base91
                    result = base_encrypt.base91_Encode(inputString.getBytes());
        }
        ;
        this.base_decode.setText(result);
        this.base_decode.setLineWrap(true);
        this.base_decode.setWrapStyleWord(true);
    }

    private void decode_Button() {
        String result = "";
        byte[] decodedBytes = null;
        String inputString = this.base_encode.getText();
        switch (base_ComboBox.getSelectedIndex()) {
            case 0 ->
                //Base64
                    result = base_encrypt.base64_Decode(inputString);
            case 1 -> {
                //Base32
                decodedBytes = base_encrypt.base32_Decode(inputString);
                result = new String(decodedBytes, StandardCharsets.UTF_8);
            }
            case 2 -> {
                //Base16
                decodedBytes = base_encrypt.base16_Decode(inputString);
                result = new String(decodedBytes, StandardCharsets.UTF_8);
            }
            case 3 -> {
                //Base58
                decodedBytes = base_encrypt.base58_Decode(inputString);
                result = new String(decodedBytes, StandardCharsets.UTF_8);
            }
            case 4 -> {
                //Base91
                decodedBytes = base_encrypt.base91_Decode(result);
                result = new String(decodedBytes, StandardCharsets.UTF_8);
            }
        }
        ;
        this.base_decode.setText(result);
        this.base_decode.setLineWrap(true);
        this.base_decode.setWrapStyleWord(true);
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        Base = new JPanel();
        Base.setLayout(new GridBagLayout());
        base_ComboBox = new JComboBox();
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipadx = 150;
        gbc.ipady = 10;
        gbc.insets = new Insets(3, 5, 3, 5);
        Base.add(base_ComboBox, gbc);
        decode_Button = new JButton();
        decode_Button.setHideActionText(false);
        decode_Button.setHorizontalAlignment(0);
        decode_Button.setHorizontalTextPosition(11);
        decode_Button.setText("解密");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.weightx = 55.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.ipadx = 50;
        gbc.ipady = 10;
        Base.add(decode_Button, gbc);
        encode_Button = new JButton();
        encode_Button.setEnabled(true);
        encode_Button.setHideActionText(false);
        encode_Button.setHorizontalAlignment(0);
        encode_Button.setHorizontalTextPosition(11);
        encode_Button.setText("加密");
        encode_Button.setVerticalAlignment(0);
        encode_Button.setVerticalTextPosition(0);
        encode_Button.putClientProperty("hideActionText", Boolean.FALSE);
        encode_Button.putClientProperty("html.disable", Boolean.FALSE);
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.ipadx = 50;
        gbc.ipady = 10;
        gbc.insets = new Insets(0, 0, 0, 10);
        Base.add(encode_Button, gbc);
        JS = new JScrollPane();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 5, 5, 5);
        Base.add(JS, gbc);
        base_encode = new JTextArea();
        base_encode.setColumns(0);
        base_encode.setEditable(true);
        base_encode.setLineWrap(false);
        base_encode.setMargin(new Insets(5, 5, 5, 5));
        base_encode.setText("");
        JS.setViewportView(base_encode);
        final JScrollPane scrollPane1 = new JScrollPane();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 3;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 5, 5, 5);
        Base.add(scrollPane1, gbc);
        base_decode = new JTextArea();
        base_decode.setText("");
        scrollPane1.setViewportView(base_decode);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return Base;
    }

}
