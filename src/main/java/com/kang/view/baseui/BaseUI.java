/*
 * @Author: Kang
 * @Version: 1.0
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.kang.view.baseui;

import burp.api.montoya.MontoyaApi;
import burp.api.montoya.logging.Logging;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.kang.entity.BaseEntity;
import com.kang.entity.HistoryEntity;
import com.kang.service.BaseEncrypt;
import com.kang.service.History;

import javax.swing.*;
import java.awt.*;
import java.nio.charset.StandardCharsets;

public class BaseUI {
    public JPanel UI;
    private JComboBox<String> base_ComboBox;
    private JButton encode_Button;
    public JTextArea base_encode;
    private JTextArea base_decode;
    private JButton decode_Button;
    private JScrollPane JSencode;
    private JScrollPane JSdecode;
    @Inject
    private final BaseEncrypt base_encrypt;
    private JPopupMenu pop;
    private final BaseEntity baseEntity = new BaseEntity();
    private final String[] str = {"Base64编码", "Base32编码", "Base16编码", "Base58编码", "Base91编码"};
    private final Logging log;
    @Inject
    private HistoryEntity historyEntity;
    @Inject
    private History history;

    public BaseUI(MontoyaApi api, Injector injector) {
        log = api.logging();
        base_encrypt = injector.getInstance(BaseEncrypt.class);
        history = injector.getInstance(History.class);
        historyEntity = injector.getInstance(HistoryEntity.class);
        
        //设置选项卡
        for (String value : str) {
            base_ComboBox.addItem(value);
        }

        this.encode_Button.addActionListener((e) -> {
            setEntity();
            encode_Button();
            historyEntity.setBaseEntity(baseEntity);
            history.baseHistory(injector);
        });
        this.decode_Button.addActionListener((e) -> {
            setEntity();
            decode_Button();
            historyEntity.setBaseEntity(baseEntity);
            history.baseHistory(injector);
        });
    }

    /**
     * encode_Button
     * @param 
     * @return void
     * @Author: Kang on 2023/10/10 14:44
     * 加密监听
     */
    private void encode_Button() {
        switch (base_ComboBox.getSelectedIndex()) {
            case 0 ->
                //Base64
                baseEntity.setOutput(base_encrypt.base64_Encode(baseEntity.getInput()));
            case 1 ->
                //Base32
                baseEntity.setOutput(base_encrypt.base32_Encode(baseEntity.getInput()));
            case 2 -> {
                //Base16
                baseEntity.setOutput(base_encrypt.base16_Encode(baseEntity.getInput().getBytes(StandardCharsets.UTF_8)));
            }
            case 3 ->
                //Base58
                baseEntity.setOutput(base_encrypt.base58_Encode(baseEntity.getInput().getBytes()));
            case 4 ->
                //Base91
                baseEntity.setOutput(base_encrypt.base91_Encode(baseEntity.getInput().getBytes()));
        }
        this.base_decode.setText(baseEntity.getOutput());
        this.base_decode.setLineWrap(true);
        this.base_decode.setWrapStyleWord(true);
    }

    /**
     * decode_Button
     * @param
     * @return void
     * @Author: Kang on 2023/10/10 14:45
     * 解密监听
     */
    private void decode_Button() {
        switch (base_ComboBox.getSelectedIndex()) {
            case 0 -> {
                //Base64
                baseEntity.setOutput(base_encrypt.base64_Decode(baseEntity.getInput()));
            }
            case 1 -> {
                //Base32
                baseEntity.setOutput(new String(base_encrypt.base32_Decode(baseEntity.getInput()), StandardCharsets.UTF_8));
            }
            case 2 -> {
                //Base16
                baseEntity.setOutput(new String(base_encrypt.base16_Decode(baseEntity.getInput()), StandardCharsets.UTF_8));
            }
            case 3 -> {
                //Base58
                baseEntity.setOutput(new String(base_encrypt.base58_Decode(baseEntity.getInput()), StandardCharsets.UTF_8));
            }
            case 4 -> {
                //Base91
                baseEntity.setOutput(new String(base_encrypt.base91_Decode(baseEntity.getInput()), StandardCharsets.UTF_8));
            }
        }
        this.base_decode.setText(baseEntity.getOutput());
        this.base_decode.setLineWrap(true);
        this.base_decode.setWrapStyleWord(true);
    }

    /**
     * setEntity
     * @param
     * @return void
     * @Author: Kang on 2023/10/10 14:45
     * 设置数据信息
     */
    private void setEntity() {
        baseEntity.setInput(this.base_encode.getText());
        baseEntity.setMode(str[base_ComboBox.getSelectedIndex()]);
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
        UI = new JPanel();
        UI.setLayout(new GridBagLayout());
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
        UI.add(base_ComboBox, gbc);
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
        UI.add(decode_Button, gbc);
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
        UI.add(encode_Button, gbc);
        JSencode = new JScrollPane();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 5, 5, 5);
        UI.add(JSencode, gbc);
        base_encode = new JTextArea();
        base_encode.setColumns(0);
        base_encode.setEditable(true);
        base_encode.setLineWrap(true);
        base_encode.setMargin(new Insets(5, 5, 5, 5));
        base_encode.setText("");
        base_encode.setWrapStyleWord(true);
        JSencode.setViewportView(base_encode);
        JSdecode = new JScrollPane();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 3;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 5, 5, 5);
        UI.add(JSdecode, gbc);
        base_decode = new JTextArea();
        base_decode.setLineWrap(true);
        base_decode.setText("");
        base_decode.setWrapStyleWord(true);
        JSdecode.setViewportView(base_decode);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return UI;
    }

}
