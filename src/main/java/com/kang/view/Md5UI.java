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
import burp.api.montoya.logging.Logging;
import com.kang.config.ConfigFile;
import com.kang.entity.Md5Entity;
import com.kang.service.Impl.Md5_Encrypt_Impl;
import com.kang.service.Impl.Md5EncryptApiImpl;
import com.kang.service.Md5Encrypt;
import com.kang.service.Md5EncryptApi;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import javax.swing.*;
import java.awt.*;

public class Md5UI {
    public JPanel UI;
    private JComboBox<String> Link_URL;
    private JTextField Email_text;
    private JTextField Key_text;
    private JButton Save_Button;
    private JButton Reset_Button;
    private JTextArea inputString_Text;
    private JTextArea outputString_Text;
    private JTextArea Log_Text;
    private JButton Decode_butten;
    private JButton Encode_butten;
    private JComboBox<String> Hash_ComboBox;
    private JScrollPane JS1;
    private JScrollPane JS2;
    private JRadioButton No_vip;
    private JRadioButton Yes_vip;
    private final String[] str = {"MD5-32","MD5-16", "SHA-1", "SHA-224", "SHA-256", "SHA-384", "SHA-512"};
    private final String[] link = {"CMD5", "MD5免费在线"};
    private final String[] url = {"https://www.cmd5.com/", "https://www.somd5.com/"};
    private Md5Entity md5_entity = new Md5Entity();
    private ConfigFile configFile;
    private final Logging log;

    public Md5UI(MontoyaApi api) {
        log = api.logging();
        init();

        iniFile();

        this.Encode_butten.addActionListener((e) -> {
            setMd5_entity();
            encodeButton();
        });
        this.Decode_butten.addActionListener((e) -> {
            setMd5_entity();
            decodeButton();
        });
        this.Save_Button.addActionListener((e) -> {
            configFile = new ConfigFile();
            setMd5_entity();
            md5_entity.setLog_Text(configFile.save(md5_entity));

            this.Log_Text.setText(md5_entity.getLog_Text());
            this.Log_Text.setLineWrap(true);
            this.Log_Text.setWrapStyleWord(true);
        });
    }

    private void encodeButton() {
        Md5Encrypt md5_encrypt = new Md5_Encrypt_Impl();
        switch (md5_entity.getHash_ComboBox()) {
            case 0 ->
                //MD5-32
                md5_entity.setOutputString(md5_encrypt.md5Encode32(md5_entity.getInputString()));
            case 1 ->
                //MD5-16
                md5_entity.setOutputString(md5_encrypt.md5Encode16(md5_entity.getInputString()));
            case 2 ->
                //SHA-1
                md5_entity.setOutputString(md5_encrypt.sha1Encode(md5_entity.getInputString()));
            case 3 ->
                //SHA-224
                md5_entity.setOutputString(md5_encrypt.sha224Encode(md5_entity.getInputString()));
            case 4 ->
                //SHA-256
                md5_entity.setOutputString(md5_encrypt.sha256Encode(md5_entity.getInputString()));
            case 5 ->
                //SHA-384
                md5_entity.setOutputString(md5_encrypt.sha384Encode(md5_entity.getInputString()));
            case 6 ->
                //SHA-512
                md5_entity.setOutputString(md5_encrypt.sha512Encode(md5_entity.getInputString()));
        }
        this.outputString_Text.setText(md5_entity.getOutputString());
        this.outputString_Text.setLineWrap(true);
        this.outputString_Text.setWrapStyleWord(true);
    }

    private void decodeButton() {
        Md5EncryptApi md5_encrypt_api = new Md5EncryptApiImpl();

        switch (Link_URL.getSelectedIndex()) {
            case 0 -> {
                if (this.No_vip.isSelected()) {
                    md5_entity = md5_encrypt_api.CMD5_url(md5_entity);
                } else if (this.Yes_vip.isSelected()) {
                    md5_entity = md5_encrypt_api.CMD5_url_api(md5_entity);
                }
            }
            case 1 -> {
            }
        }
        this.Log_Text.setText(md5_entity.getLog_Text());
        this.Log_Text.setLineWrap(true);
        this.Log_Text.setWrapStyleWord(true);

        this.outputString_Text.setText(md5_entity.getOutputString());
        this.outputString_Text.setLineWrap(true);
        this.outputString_Text.setWrapStyleWord(true);
    }

    public void iniFile() {
        configFile = new ConfigFile();
        md5_entity = configFile.getConfig(md5_entity);
        this.Email_text.setText(md5_entity.getEmail_text());
        this.Key_text.setText(md5_entity.getKey_text());
    }

    private void setMd5_entity() {
        md5_entity.setInputString(this.inputString_Text.getText());

        md5_entity.setHash_ComboBox(Hash_ComboBox.getSelectedIndex());
        md5_entity.setUrl_api(url[this.Link_URL.getSelectedIndex()]);

        md5_entity.setEmail_text(this.Email_text.getText());
        md5_entity.setKey_text((this.Key_text.getText()));

    }

    private void init() {
        ButtonGroup bg = new ButtonGroup();
        bg.add(No_vip);
        bg.add(Yes_vip);
        No_vip.setSelected(true);

        for (String s : str) {
            Hash_ComboBox.addItem(s);
        }

        for (int i = 0; i < link.length; i++) {
            Link_URL.addItem(link[i] + " (" + url[i] + ")");
        }
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
        UI.setAlignmentX(0.0f);
        UI.setAlignmentY(0.0f);
        UI.setDoubleBuffered(true);
        UI.setFocusable(true);
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(3, 3, new Insets(0, 0, 0, 0), -1, -1));
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 5, 5, 5);
        UI.add(panel1, gbc);
        JS1 = new JScrollPane();
        panel1.add(JS1, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 3, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        inputString_Text = new JTextArea();
        JS1.setViewportView(inputString_Text);
        JS2 = new JScrollPane();
        panel1.add(JS2, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 3, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        outputString_Text = new JTextArea();
        JS2.setViewportView(outputString_Text);
        Hash_ComboBox = new JComboBox();
        panel1.add(Hash_ComboBox, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        Encode_butten = new JButton();
        Encode_butten.setText("加密");
        panel1.add(Encode_butten, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(9, 3, new Insets(0, 0, 0, 0), -1, -1));
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 5, 5, 5);
        UI.add(panel2, gbc);
        Link_URL = new JComboBox();
        panel2.add(Link_URL, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        Email_text = new JTextField();
        panel2.add(Email_text, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        Key_text = new JTextField();
        panel2.add(Key_text, new com.intellij.uiDesigner.core.GridConstraints(2, 1, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        Save_Button = new JButton();
        Save_Button.setText("保存");
        panel2.add(Save_Button, new com.intellij.uiDesigner.core.GridConstraints(4, 0, 1, 3, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        Reset_Button = new JButton();
        Reset_Button.setText("重置");
        panel2.add(Reset_Button, new com.intellij.uiDesigner.core.GridConstraints(5, 0, 1, 3, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JScrollPane scrollPane1 = new JScrollPane();
        panel2.add(scrollPane1, new com.intellij.uiDesigner.core.GridConstraints(8, 0, 1, 3, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        Log_Text = new JTextArea();
        scrollPane1.setViewportView(Log_Text);
        final JLabel label1 = new JLabel();
        label1.setText("email：");
        panel2.add(label1, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("key：");
        panel2.add(label2, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("日志：");
        panel2.add(label3, new com.intellij.uiDesigner.core.GridConstraints(7, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label4 = new JLabel();
        label4.setText("接口：");
        panel2.add(label4, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        Decode_butten = new JButton();
        Decode_butten.setText("解密");
        panel2.add(Decode_butten, new com.intellij.uiDesigner.core.GridConstraints(6, 0, 1, 3, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        No_vip = new JRadioButton();
        No_vip.setText("免费接口(有限制)");
        panel2.add(No_vip, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        Yes_vip = new JRadioButton();
        Yes_vip.setText("付费接口");
        panel2.add(Yes_vip, new com.intellij.uiDesigner.core.GridConstraints(3, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label5 = new JLabel();
        label5.setText("说明：");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);
        UI.add(label5, gbc);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return UI;
    }

}
