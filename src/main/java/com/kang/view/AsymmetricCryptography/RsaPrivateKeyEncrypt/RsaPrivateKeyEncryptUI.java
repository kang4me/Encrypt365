/*
 * @Author: Kang
 * @Version: 1.0
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.kang.view.AsymmetricCryptography.RsaPrivateKeyEncrypt;

import burp.api.montoya.MontoyaApi;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.kang.entity.RsaEntity;
import com.kang.service.Impl.RsaPrivateKeyEncryptImpl;
import com.kang.service.RsaPrivateKeyEncrypt;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class RsaPrivateKeyEncryptUI {
    public JPanel UI;
    private JComboBox<String> padding;
    private JComboBox<String> outPut;
    private JButton encodeButton;
    private JButton decodeButton;
    private JTextArea privateKey;
    private JTextArea textArea;
    private JTextArea encodeText;
    private JTextField key;
    private JComboBox<String> charset_Box;
    private RsaEntity rsaEntity;
    private final MontoyaApi api;
    private final RsaPrivateKeyEncrypt rsaPrivateKeyEncrypt = new RsaPrivateKeyEncryptImpl();

    public RsaPrivateKeyEncryptUI(MontoyaApi api) {
        this.api = api;
        ini();

        this.encodeButton.addActionListener(e -> {
            setEntity();
            encodeButton();
        });
        this.decodeButton.addActionListener(e -> {
            setEntity();
            decodeButton();
        });

    }

    private void encodeButton() {
        try {
            byte[] bytes = rsaPrivateKeyEncrypt.encryptWithPrivateKey(rsaPrivateKeyEncrypt.getPrivateKeyFromPEM(rsaEntity.getPrivateKey()), rsaEntity);
            if (outPut.getSelectedIndex() == 0) {
                this.encodeText.setText(new String(Base64.encodeBase64(bytes)));
            } else if (outPut.getSelectedIndex() == 0) {
                this.encodeText.setText(new String(Hex.encodeHex(bytes)));
            }
        } catch (Exception e) {
            api.logging().logToError(e);
        }
    }

    private void decodeButton() {
        try {
            this.encodeText.setText(rsaPrivateKeyEncrypt.decodeWithPrivateKey(rsaPrivateKeyEncrypt.getPrivateKeyFromPEM(rsaEntity.getPrivateKey()), rsaEntity));
        } catch (Exception e) {
            api.logging().logToError(e);
        }
    }

    public void setEntity() {
        rsaEntity = new RsaEntity();

        rsaEntity.setPrivateKey(this.privateKey.getText());
        rsaEntity.setText(this.textArea.getText());

        if (this.padding.getSelectedIndex() == 0) {//PKCS1_PADDING

        } else if (this.padding.getSelectedIndex() == 1) {//PKCS1_OAEP_PADDING

        } else if (this.padding.getSelectedIndex() == 2) {//SSLV23_PADDING

        }

        if (charset_Box.getSelectedIndex() == 0) {
            rsaEntity.setCharset("gb2312");
        } else if (charset_Box.getSelectedIndex() == 1) {
            rsaEntity.setCharset("gbk");
        } else if (charset_Box.getSelectedIndex() == 2) {
            rsaEntity.setCharset("gb18030");
        } else if (charset_Box.getSelectedIndex() == 3) {
            rsaEntity.setCharset("utf8");
        } else if (charset_Box.getSelectedIndex() == 4) {
            rsaEntity.setCharset("iso-8859-1");
        }
    }

    private void ini() {
        String[] padding = new String[]{"PKCS1_PADDING", "PKCS1_OAEP_PADDING", "SSLV23_PADDING"};
        String[] outPutStr = new String[]{"Base64", "Hex"};
        String[] charset_str = new String[]{"gb2312编码(简体)", "gbk编码(简繁体)", "gb18030编码(中日韩)", "utf8编码(unicode编码)", "iso-8859-1(单字节)"};


        for (String value : padding) {
            this.padding.addItem(value);
        }
        for (String value : outPutStr) {
            outPut.addItem(value);
        }
        for (String value : charset_str) {
            charset_Box.addItem(value);
        }


        privateKey.setForeground(Color.gray); //将提示文字设置为灰色
        privateKey.setText("（以“-----BEGIN PRIVATE KEY-----”开头 “-----END PRIVATE KEY-----” 结尾）");     //显示提示文字
        privateKey.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
                //得到焦点时，当前文本框的提示文字和创建该对象时的提示文字一样，说明用户正要键入内容
                if (privateKey.getText().equals("（以“-----BEGIN PRIVATE KEY-----”开头 “-----END PRIVATE KEY-----” 结尾）")) {
                    privateKey.setText("");     //将提示文字清空
                    privateKey.setForeground(Color.black);  //设置用户输入的字体颜色为黑色
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                //失去焦点时，用户尚未在文本框内输入任何内容，所以依旧显示提示文字
                if (privateKey.getText().isEmpty()) {
                    privateKey.setForeground(Color.gray); //将提示文字设置为灰色
                    privateKey.setText("（以“-----BEGIN PRIVATE KEY-----”开头 “-----END PRIVATE KEY-----” 结尾）");     //显示提示文字
                }
            }
        });
        key.setForeground(Color.gray); //将提示文字设置为灰色
        key.setText("有密码保护时输入");     //显示提示文字
        key.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
                //得到焦点时，当前文本框的提示文字和创建该对象时的提示文字一样，说明用户正要键入内容
                if (key.getText().equals("有密码保护时输入")) {
                    key.setText("");     //将提示文字清空
                    key.setForeground(Color.black);  //设置用户输入的字体颜色为黑色
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                //失去焦点时，用户尚未在文本框内输入任何内容，所以依旧显示提示文字
                if (key.getText().isEmpty()) {
                    key.setForeground(Color.gray); //将提示文字设置为灰色
                    key.setText("有密码保护时输入");     //显示提示文字
                }
            }
        });
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
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 10, new Insets(0, 0, 0, 0), -1, -1));
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        UI.add(panel1, gbc);
        final JLabel label1 = new JLabel();
        label1.setText("填充方式：");
        panel1.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        padding = new JComboBox();
        panel1.add(padding, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(125, 30), null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("私钥密码：");
        panel1.add(label2, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        key = new JTextField();
        panel1.add(key, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("输出格式：");
        panel1.add(label3, new GridConstraints(0, 6, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        outPut = new JComboBox();
        panel1.add(outPut, new GridConstraints(0, 7, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(100, -1), null, 0, false));
        encodeButton = new JButton();
        encodeButton.setText("加密");
        panel1.add(encodeButton, new GridConstraints(0, 8, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, new Dimension(100, 35), null, null, 0, false));
        decodeButton = new JButton();
        decodeButton.setText("解密[解密对应公钥的加密]");
        panel1.add(decodeButton, new GridConstraints(0, 9, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, new Dimension(100, 35), null, null, 0, false));
        final JLabel label4 = new JLabel();
        label4.setText("字符集：");
        panel1.add(label4, new GridConstraints(0, 4, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        charset_Box = new JComboBox();
        panel1.add(charset_Box, new GridConstraints(0, 5, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(100, -1), null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 5, 5, 5);
        UI.add(panel2, gbc);
        final JLabel label5 = new JLabel();
        label5.setForeground(new Color(-15932142));
        label5.setText("非对称加密秘钥：");
        label5.setVerticalAlignment(0);
        label5.setVerticalTextPosition(0);
        panel2.add(label5, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JScrollPane scrollPane1 = new JScrollPane();
        panel2.add(scrollPane1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        privateKey = new JTextArea();
        privateKey.setLineWrap(true);
        privateKey.setRows(0);
        scrollPane1.setViewportView(privateKey);
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 5, 5, 5);
        UI.add(panel3, gbc);
        final JLabel label6 = new JLabel();
        label6.setForeground(new Color(-1763052));
        label6.setText("需要加密/解密的文本：");
        panel3.add(label6, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JScrollPane scrollPane2 = new JScrollPane();
        panel3.add(scrollPane2, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        textArea = new JTextArea();
        scrollPane2.setViewportView(textArea);
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 5, 5, 5);
        UI.add(panel4, gbc);
        final JLabel label7 = new JLabel();
        label7.setForeground(new Color(-12959003));
        label7.setText("RSA秘钥加密/解密转换结果");
        panel4.add(label7, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JScrollPane scrollPane3 = new JScrollPane();
        panel4.add(scrollPane3, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        encodeText = new JTextArea();
        scrollPane3.setViewportView(encodeText);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return UI;
    }

}