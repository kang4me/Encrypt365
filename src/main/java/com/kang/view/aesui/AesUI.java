/*
 * @Author: Kang
 * @Version: 1.0
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.kang.view.aesui;


import javax.crypto.BadPaddingException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import burp.api.montoya.MontoyaApi;
import burp.api.montoya.logging.Logging;
import cn.hutool.crypto.CryptoException;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.kang.burpApi.PayloadGeneratorProvider.AesPayloadProcessor;
import com.kang.entity.AesEntity;
import com.kang.entity.HistoryEntity;
import com.kang.service.AesEncrypt;
import com.kang.service.History;
import cn.hutool.crypto.Mode;
import cn.hutool.crypto.Padding;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.kang.service.Impl.AesEncryptImpl;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

public class AesUI {
    public JPanel UI;
    private JComboBox<String> Mode_Box;
    private JComboBox<String> Padding_Box;
    private JComboBox<String> DataBlock__Box;
    private JTextField Password;
    private JTextField iv;
    private JComboBox<String> Output__Box;
    private JComboBox<String> Charset__Box;
    public JTextArea Aes_encode;
    private JTextArea Aes_decode;
    private JButton AES_Encode_Button;
    private JButton AES_Decode_Button;
    private JLabel hint;
    private JPanel JPanel1;
    private JPanel JPanel2;
    private JPanel JPanel3;
    private JPanel JPanel4;
    private JButton addIntruderButton;
    private AesEncrypt aes_encrypt;
    private final AesEntity aes_entity = new AesEntity();
    private final Logging log;
    @Inject
    private HistoryEntity historyEntity;
    @Inject
    private History history;

    public AesUI(MontoyaApi api, Injector injector) {
        log = api.logging();
        history = injector.getInstance(History.class);
        historyEntity = injector.getInstance(HistoryEntity.class);

        init();//初始化数据

        this.AES_Encode_Button.addActionListener((e) -> {
            aesEntity();//设置输入数据
            aes_encrypt = new AesEncryptImpl(aes_entity);
            Encode_modeSelected();
            historyEntity.setAesEntity(aes_entity);//设置数据
            history.aesHistory(injector);//将传递注入
        });
        this.AES_Decode_Button.addActionListener((e) -> {
            aesEntity();//设置输入数据
            aes_encrypt = new AesEncryptImpl(aes_entity);
            Decode_modeSelected();
            historyEntity.setAesEntity(aes_entity);
            history.aesHistory(injector);
        });

        this.addIntruderButton.addActionListener((e) -> {
            aesEntity();

            if (aes_entity.getKey().equals("请输入密码!")) {
                JOptionPane.showMessageDialog(UI, "密码不能为空！");
                return;
            }

            String extName = JOptionPane.showInputDialog("请自定义Intruder名称:Encrypt365-*");
            if (extName != null) {
                if (extName.length() == 0) {
                    JOptionPane.showMessageDialog(UI, "名称不可为空!");
                    return;
                }
            } else return;

            historyEntity.setAesEntity(aes_entity);
            api.intruder().registerPayloadProcessor(new AesPayloadProcessor(api, injector, extName));
        });

    }

    /**
     * Encode_modeSelected
     *
     * @param
     * @return void
     * @Author: Kang on 2023/10/10 14:37
     * 加密监听方法体
     */
    private void Encode_modeSelected() {
        if (aes_entity.getKey().equals("请输入密码!") || aes_entity.getKey().equals("")) {
            JOptionPane.showMessageDialog(UI, "密码不能为空！");
            return;
        }

        if (aes_entity.getPadding() == Padding.NoPadding) {
            if ((aes_entity.getInputValue().length() % 16) != 0) {
                Aes_decode.setText("加密文本必须为16的倍数");
                return;
            }
        }

        if (aes_entity.getMode() != Mode.ECB) {
            if (aes_entity.getIv().length() < 16) {
                Aes_decode.setText("偏移量最少：16字节长度。");
                return;
            }
        }
        byte[] bt = null;
        try {
            bt = aes_encrypt.AES_Encrypt_all();
        } catch (Exception e) {
            Aes_decode.setText("异常信息");
        }

        if (bt == null) {
            Aes_decode.setText("参数异常，请检查参数");
            return;
        }

        //输出方式
        if (aes_entity.getOutputEncode().equals("0")) {
            aes_entity.setOutputValue(new String(Base64.encodeBase64(bt)));
            Aes_decode.setText(aes_entity.getOutputValue());
        } else {
            aes_entity.setOutputValue(new String(Hex.encodeHex(bt)));
            Aes_decode.setText(aes_entity.getOutputValue());
        }
    }

    /**
     * Decode_modeSelected
     *
     * @param
     * @return void
     * @Author: Kang on 2023/10/10 14:38
     * 解密监听方法体
     */
    private void Decode_modeSelected() {
        if (aes_entity.getKey().equals("请输入密码!") || aes_entity.getKey().equals("")) {
            JOptionPane.showMessageDialog(UI, "密码不能为空！");
            return;
        }

        //输入方式
        try {
/*            if (Output__Box.getSelectedIndex() == 0) {
                aes_entity.setInputValue(new String(Base64.decodeBase64(aes_entity.getInputValue())));
            } else if (Output__Box.getSelectedIndex() == 1) {
                aes_entity.setInputValue(new String(Hex.decodeHex(aes_entity.getInputValue())));
            }*/
            aes_entity.setOutputValue(aes_encrypt.AES_Decrypt_all());
            if (aes_entity.getOutputValue() == null || aes_entity.getOutputValue().isEmpty()) {
                Aes_decode.setText("参数异常，请检查参数");
            }
        } catch (CryptoException cryptoException) {
            JOptionPane.showMessageDialog(UI, "密码错误");
        } catch (Exception e) {
            log.logToError(e);
        }

        Aes_decode.setText(aes_entity.getOutputValue());
    }

    /**
     * aesEntity
     *
     * @param
     * @return void
     * @Author: Kang on 2023/10/10 14:37
     * 设置数据信息
     */
    private void aesEntity() {
        aes_entity.setInputValue(Aes_encode.getText());
        aes_entity.setKey(Password.getText());
        aes_entity.setIv(iv.getText());
        aes_entity.setOutputEncode(String.valueOf(Output__Box.getSelectedIndex()));

        if (Mode_Box.getSelectedIndex() == 0) {
            aes_entity.setMode(Mode.ECB);
        } else if (Mode_Box.getSelectedIndex() == 1) {
            aes_entity.setMode(Mode.CBC);
        } else if (Mode_Box.getSelectedIndex() == 2) {
            aes_entity.setMode(Mode.CTR);
        } else if (Mode_Box.getSelectedIndex() == 3) {
            aes_entity.setMode(Mode.OFB);
        } else if (Mode_Box.getSelectedIndex() == 4) {
            aes_entity.setMode(Mode.CFB);
        } else if (Mode_Box.getSelectedIndex() == 5) {
            aes_entity.setMode(Mode.CTS);
        } else if (Mode_Box.getSelectedIndex() == 6) {
            aes_entity.setMode(Mode.PCBC);
        }
        if (Padding_Box.getSelectedIndex() == 0) {
            aes_entity.setPadding(Padding.PKCS5Padding);
        } else if (Padding_Box.getSelectedIndex() == 1) {
            aes_entity.setPadding(Padding.PKCS1Padding);
        } else if (Padding_Box.getSelectedIndex() == 2) {
            aes_entity.setPadding(Padding.ZeroPadding);
        } else if (Padding_Box.getSelectedIndex() == 3) {
            aes_entity.setPadding(Padding.ISO10126Padding);
        } else if (Padding_Box.getSelectedIndex() == 4) {
            aes_entity.setPadding(Padding.OAEPPadding);
        } else if (Padding_Box.getSelectedIndex() == 5) {
            aes_entity.setPadding(Padding.SSL3Padding);
        } else if (Padding_Box.getSelectedIndex() == 6) {
            aes_entity.setPadding(Padding.NoPadding);
        }
        //默认为16字节
        if (DataBlock__Box.getSelectedIndex() == 0) {
            aes_entity.setDataBlock(16);
        } else if (DataBlock__Box.getSelectedIndex() == 1) {
            aes_entity.setDataBlock(24);
        } else if (DataBlock__Box.getSelectedIndex() == 2) {
            aes_entity.setDataBlock(32);
        }
        //判断字符集
        if (Charset__Box.getSelectedIndex() == 0) {
            aes_entity.setCharset("gb2312");
        } else if (Charset__Box.getSelectedIndex() == 1) {
            aes_entity.setCharset("gbk");
        } else if (Charset__Box.getSelectedIndex() == 2) {
            aes_entity.setCharset("gb18030");
        } else if (Charset__Box.getSelectedIndex() == 3) {
            aes_entity.setCharset("utf");
        } else if (Charset__Box.getSelectedIndex() == 4) {
            aes_entity.setCharset("iso-8859-1");
        }
    }

    /**
     * 初始化AES选项数据
     */
    private void init() {
        String[] mode_str = new String[]{"ECB", "CBC", "CTR", "OFB", "CFB", "CTS", "PCBC"};
        String[] padding_str = new String[]{"Pkcs5padding", "Pkcs1Padding", "ZeroPadding", "Iso10126Padding", "OAepPadding", "Ssl3Padding", "no padding"};
        String[] dataBlock_str = new String[]{"128位", "192位", "256位"};
        String[] output_str = new String[]{"base64", "hex"};
        String[] charset_str = new String[]{"gb2312编码(简体)", "gbk编码(简繁体)", "gb18030编码(中日韩)", "utf8编码(unicode编码)", "iso-8859-1(单字节)"};

        for (String value : mode_str) {
            Mode_Box.addItem(value);
        }
        for (String value : padding_str) {
            Padding_Box.addItem(value);
        }
        for (String value : dataBlock_str) {
            DataBlock__Box.addItem(value);
        }
        for (String value : output_str) {
            Output__Box.addItem(value);
        }
        for (String value : charset_str) {
            Charset__Box.addItem(value);
        }

        Password.setForeground(Color.gray); //将提示文字设置为灰色
        Password.setText("请输入密码!");     //显示提示文字
        Password.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
                //得到焦点时，当前文本框的提示文字和创建该对象时的提示文字一样，说明用户正要键入内容
                if (Password.getText().equals("请输入密码!")) {
                    Password.setText("");     //将提示文字清空
                    Password.setForeground(Color.black);  //设置用户输入的字体颜色为黑色
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                //失去焦点时，用户尚未在文本框内输入任何内容，所以依旧显示提示文字
                if (Password.getText().isEmpty()) {
                    Password.setForeground(Color.gray); //将提示文字设置为灰色
                    Password.setText("请输入密码!");     //显示提示文字
                }
            }
        });
        iv.setForeground(Color.gray); //将提示文字设置为灰色
        iv.setText("ECB模式无需填写!");     //显示提示文字
        iv.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
                //得到焦点时，当前文本框的提示文字和创建该对象时的提示文字一样，说明用户正要键入内容
                if (iv.getText().equals("ECB模式无需填写!")) {
                    iv.setText("");     //将提示文字清空
                    iv.setForeground(Color.black);  //设置用户输入的字体颜色为黑色
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                //失去焦点时，用户尚未在文本框内输入任何内容，所以依旧显示提示文字
                if (iv.getText().isEmpty()) {
                    iv.setForeground(Color.gray); //将提示文字设置为灰色
                    iv.setText("ECB模式无需填写!");     //显示提示文字
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
        Mode_Box = new JComboBox();
        JPanel1.add(Mode_Box, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("填充：");
        JPanel1.add(label2, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        Padding_Box = new JComboBox();
        JPanel1.add(Padding_Box, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("数据块：");
        JPanel1.add(label3, new GridConstraints(0, 4, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        DataBlock__Box = new JComboBox();
        JPanel1.add(DataBlock__Box, new GridConstraints(0, 5, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label4 = new JLabel();
        label4.setText("密码：");
        JPanel1.add(label4, new GridConstraints(0, 6, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label5 = new JLabel();
        label5.setText("偏移量：");
        JPanel1.add(label5, new GridConstraints(0, 8, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        Password = new JTextField();
        Password.setText("");
        Password.setToolTipText("请输入密码!");
        JPanel1.add(Password, new GridConstraints(0, 7, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(100, -1), null, 0, false));
        iv = new JTextField();
        iv.setToolTipText("iv偏移量，ecb模式不用填写！");
        JPanel1.add(iv, new GridConstraints(0, 9, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(100, -1), null, 0, false));
        final JLabel label6 = new JLabel();
        label6.setText("输出：");
        JPanel1.add(label6, new GridConstraints(0, 10, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        Output__Box = new JComboBox();
        JPanel1.add(Output__Box, new GridConstraints(0, 11, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label7 = new JLabel();
        label7.setText("字符集");
        JPanel1.add(label7, new GridConstraints(0, 12, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        Charset__Box = new JComboBox();
        JPanel1.add(Charset__Box, new GridConstraints(0, 13, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
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
        Aes_encode.setLineWrap(true);
        scrollPane1.setViewportView(Aes_encode);
        JPanel3 = new JPanel();
        JPanel3.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.BOTH;
        UI.add(JPanel3, gbc);
        AES_Encode_Button = new JButton();
        AES_Encode_Button.setPreferredSize(new Dimension(100, 35));
        AES_Encode_Button.setText("AES加密");
        JPanel3.add(AES_Encode_Button);
        AES_Decode_Button = new JButton();
        AES_Decode_Button.setPreferredSize(new Dimension(100, 35));
        AES_Decode_Button.setText("AES解密");
        JPanel3.add(AES_Decode_Button);
        addIntruderButton = new JButton();
        addIntruderButton.setPreferredSize(new Dimension(120, 35));
        addIntruderButton.setText("添加Intruder");
        JPanel3.add(addIntruderButton);
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
        Aes_decode.setLineWrap(true);
        Aes_decode.setWrapStyleWord(true);
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
