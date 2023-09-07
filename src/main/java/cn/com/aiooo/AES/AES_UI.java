/*
 * @Author: Kang
 * @Version: 1.0
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package cn.com.aiooo.AES;


import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import burp.api.montoya.MontoyaApi;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

public class AES_UI {
    public JPanel UI;
    private JComboBox<String> Mode;
    private JComboBox<String> Padding;
    private JComboBox<String> DataBlock;
    private JTextField Password;
    private JTextField Offset;
    private JComboBox<String> Output;
    private JComboBox<String> Charset;
    private JTextArea Aes_encode;
    private JTextArea Aes_decode;
    private JButton AES_Encode_Button;
    private JButton AES_Decode_Button;
    private JLabel hint;
    private JPanel JPanel1;
    private JPanel JPanel2;
    private JPanel JPanel3;
    private JPanel JPanel4;
    private MontoyaApi api;
    private int size = 0;
    private AES_ECB aes_ecb = null;

    public AES_UI(MontoyaApi api) {
        this.api = api;

        init();//初始化

        //默认为16字节
        if (DataBlock.getSelectedIndex() == 0) {
            size = 16;
        } else if (DataBlock.getSelectedIndex() == 1) {
            size = 24;
        } else if (DataBlock.getSelectedIndex() == 2) {
            size = 32;
        }
        this.AES_Encode_Button.addActionListener((e) -> {
            Encode_modeSelected();
        });
        this.AES_Decode_Button.addActionListener((e) -> {
            Decode_modeSelected();
        });
    }

    private void Encode_modeSelected() {
        byte[] bt = null;

        //判断字符集
        if (Charset.getSelectedIndex() == 0) {
            aes_ecb = new AES_ECB("gb2312", api);
        } else if (Charset.getSelectedIndex() == 1) {
            aes_ecb = new AES_ECB("gbk", api);
        } else if (Charset.getSelectedIndex() == 2) {
            aes_ecb = new AES_ECB("gb18030", api);
        } else if (Charset.getSelectedIndex() == 3) {
            aes_ecb = new AES_ECB("utf-8", api);
        } else if (Charset.getSelectedIndex() == 4) {
            aes_ecb = new AES_ECB("iso-8859-1", api);
        }

        //选择加密模式
        switch (Mode.getSelectedIndex()) {
            case 0://ECB
                //选择填充方式
                switch (Padding.getSelectedIndex()) {
                    case 0://pkcs5padding
                        bt = aes_ecb.AES_ECB_PKCS5_encrypt(Aes_encode.getText(), Password.getText(), size);
                        break;
                    case 1://PKCS7Padding
                        bt = aes_ecb.AES_ECB_PKCS7_encrypt(Aes_encode.getText(), Password.getText(), size);
                        break;
                }
                break;
            case 1://CBC
                break;
            case 2://CTR
                break;
            case 3://OFB
                break;
            case 4://CFB
                break;
        }
        if (bt == null) {
            Aes_decode.setText("参数异常，请检查参数");
            return;
        }

        //输出方式
        if (Output.getSelectedIndex() == 0) {
            api.logging().logToOutput("2");
            Aes_decode.setText(new String(Base64.encodeBase64(bt)));
        } else if (Output.getSelectedIndex() == 1) {
            Aes_decode.setText(new String(Hex.encodeHex(bt)));
        }
    }

    private void Decode_modeSelected() {
        String bt = null;

        //解密模式
        switch (Mode.getSelectedIndex()) {
            case 0://ECB

                //选择填充方式
                switch (Padding.getSelectedIndex()) {
                    case 0://pkcs5padding
                        bt = aes_ecb.AES_ECB_PKCS5_decrypt(Base64.decodeBase64(Aes_encode.getText()), Password.getText(), size);
                        break;
                    case 1://PKCS7Padding
                        bt = aes_ecb.AES_ECB_PKCS7_decrypt(Base64.decodeBase64(Aes_encode.getText()), Password.getText(), size);
                        break;
                }
                break;
            case 1://CBC
                break;
            case 2://CTR
                break;
            case 3://OFB
                break;
            case 4://CFB
                break;
        }
        if (bt == null || bt.equals("")) {
            bt = "参数异常，请检查参数";
        }

        Aes_decode.setText(bt);
    }

    //初始化数据
    private void init() {
        String[] mode_str = new String[]{"ECB", "CBC", "CTR", "OFB", "CFB"};
        String[] padding_str = new String[]{"pkcs5padding", "pkcs7padding", "ZeroPadding", "iso10126", "Ansix923", "no padding"};
        String[] dataBlock_str = new String[]{"128位", "192位", "256位"};
        String[] output_str = new String[]{"base64", "hex"};
        String[] charset_str = new String[]{"gb2312编码(简体)", "gbk编码(简繁体)", "gb18030编码(中日韩)", "utf8编码(unicode编码)", "iso-8859-1(单字节)"};

        for (String value : mode_str) {
            Mode.addItem(value);
        }
        for (String value : padding_str) {
            Padding.addItem(value);
        }
        for (String value : dataBlock_str) {
            DataBlock.addItem(value);
        }
        for (String value : output_str) {
            Output.addItem(value);
        }
        for (String value : charset_str) {
            Charset.addItem(value);
        }
        Password.setForeground(Color.gray); //将提示文字设置为灰色
        Password.setText("请输入密码");     //显示提示文字
        Password.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
                //得到焦点时，当前文本框的提示文字和创建该对象时的提示文字一样，说明用户正要键入内容
                if (Password.getText().equals("请输入密码")) {
                    Password.setText("");     //将提示文字清空
                    Password.setForeground(Color.black);  //设置用户输入的字体颜色为黑色
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                //失去焦点时，用户尚未在文本框内输入任何内容，所以依旧显示提示文字
                if (Password.getText().equals("")) {
                    Password.setForeground(Color.gray); //将提示文字设置为灰色
                    Password.setText("请输入密码");     //显示提示文字
                }
            }
        });
        Offset.setForeground(Color.gray); //将提示文字设置为灰色
        Offset.setText("ECB模式无需填写!");     //显示提示文字
        Offset.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
                //得到焦点时，当前文本框的提示文字和创建该对象时的提示文字一样，说明用户正要键入内容
                if (Offset.getText().equals("ECB模式无需填写!")) {
                    Offset.setText("");     //将提示文字清空
                    Offset.setForeground(Color.black);  //设置用户输入的字体颜色为黑色
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                //失去焦点时，用户尚未在文本框内输入任何内容，所以依旧显示提示文字
                if (Offset.getText().equals("")) {
                    Offset.setForeground(Color.gray); //将提示文字设置为灰色
                    Offset.setText("ECB模式无需填写!");     //显示提示文字
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
        JPanel1 = new JPanel();
        JPanel1.setLayout(new GridLayoutManager(1, 14, new Insets(0, 0, 0, 0), -1, -1));
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.ipady = 10;
        gbc.insets = new Insets(5, 5, 5, 5);
        UI.add(JPanel1, gbc);
        final JLabel label1 = new JLabel();
        label1.setText("AES加密模式：");
        JPanel1.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        Mode = new JComboBox();
        JPanel1.add(Mode, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("填充：");
        JPanel1.add(label2, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        Padding = new JComboBox();
        JPanel1.add(Padding, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("数据块：");
        JPanel1.add(label3, new GridConstraints(0, 4, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        DataBlock = new JComboBox();
        JPanel1.add(DataBlock, new GridConstraints(0, 5, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label4 = new JLabel();
        label4.setText("密码：");
        JPanel1.add(label4, new GridConstraints(0, 6, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label5 = new JLabel();
        label5.setText("偏移量：");
        JPanel1.add(label5, new GridConstraints(0, 8, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        Password = new JTextField();
        Password.setText("");
        Password.setToolTipText("请输入密码");
        JPanel1.add(Password, new GridConstraints(0, 7, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(100, -1), null, 0, false));
        Offset = new JTextField();
        Offset.setToolTipText("iv偏移量，ecb模式不用填写！");
        JPanel1.add(Offset, new GridConstraints(0, 9, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(100, -1), null, 0, false));
        final JLabel label6 = new JLabel();
        label6.setText("输出：");
        JPanel1.add(label6, new GridConstraints(0, 10, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        Output = new JComboBox();
        JPanel1.add(Output, new GridConstraints(0, 11, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label7 = new JLabel();
        label7.setText("字符集");
        JPanel1.add(label7, new GridConstraints(0, 12, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        Charset = new JComboBox();
        JPanel1.add(Charset, new GridConstraints(0, 13, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        JPanel2 = new JPanel();
        JPanel2.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 5, 5, 5);
        UI.add(JPanel2, gbc);
        final JScrollPane scrollPane1 = new JScrollPane();
        JPanel2.add(scrollPane1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        Aes_encode = new JTextArea();
        scrollPane1.setViewportView(Aes_encode);
        JPanel3 = new JPanel();
        JPanel3.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.BOTH;
        UI.add(JPanel3, gbc);
        AES_Encode_Button = new JButton();
        AES_Encode_Button.setText("AES加密");
        JPanel3.add(AES_Encode_Button, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, new Dimension(100, 35), null, null, 0, false));
        AES_Decode_Button = new JButton();
        AES_Decode_Button.setText("AES解密");
        JPanel3.add(AES_Decode_Button, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, new Dimension(100, 35), null, null, 0, false));
        JPanel4 = new JPanel();
        JPanel4.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 5, 5, 5);
        UI.add(JPanel4, gbc);
        final JScrollPane scrollPane2 = new JScrollPane();
        JPanel4.add(scrollPane2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        Aes_decode = new JTextArea();
        scrollPane2.setViewportView(Aes_decode);
        hint = new JLabel();
        hint.setForeground(new Color(-4511199));
        hint.setText("提示：秘钥不满足128/192/256bit时，采用0x00补充");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);
        UI.add(hint, gbc);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return UI;
    }

}
