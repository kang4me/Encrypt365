/*
 * @Author: Kang
 * @Version: 1.0
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.kang.view.asymmetricCryptography.RsaEncrypt;

import burp.api.montoya.MontoyaApi;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.kang.burpApi.PayloadGeneratorProvider.AesPayloadProcessor;
import com.kang.burpApi.PayloadGeneratorProvider.RsaPayloadProcessor;
import com.kang.entity.HistoryEntity;
import com.kang.entity.RsaEntity;
import com.kang.service.History;
import com.kang.service.RsaEncrypt;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Objects;

public class RsaEncryptUI {
    public JPanel UI;
    private JComboBox<String> padding;
    private JComboBox<String> outPut;
    private JButton encodeButton;
    private JButton decodeButton;
    private JTextArea publicOrPrivate;
    public JTextArea textValue;
    private JTextArea outputValue;
    private JComboBox<String> charset_Box;
    private JRadioButton publicKeyRadio;
    private JRadioButton privateKeyRadio;
    private JButton addIntruderButton;
    @Inject
    private final RsaEncrypt rsaPublicKeyEncrypt;
    private RsaEntity rsaEntity = new RsaEntity();
    private final MontoyaApi api;
    @Inject
    private HistoryEntity historyEntity;
    @Inject
    private History history;

    public RsaEncryptUI(MontoyaApi api, Injector injector) {
        this.api = api;
        rsaPublicKeyEncrypt = injector.getInstance(RsaEncrypt.class);
        history = injector.getInstance(History.class);
        historyEntity = injector.getInstance(HistoryEntity.class);
        ini();

        this.encodeButton.addActionListener(e -> {
            setEntity();
            this.outputValue.setText(rsaPublicKeyEncrypt.selecteEncodeMode(rsaEntity).getOutputValue());
            historyEntity.setRsaEntity(rsaEntity);
            history.rsaEncryptHistory(injector);
        });
        this.decodeButton.addActionListener(e -> {
            setEntity();
            this.outputValue.setText(rsaPublicKeyEncrypt.selecteDecodeMode(rsaEntity).getOutputValue());
            historyEntity.setRsaEntity(rsaEntity);
            history.rsaEncryptHistory(injector);
        });

        this.addIntruderButton.addActionListener((e) -> {
            if (this.publicOrPrivate.getText().equals("公钥：（以“-----BEGIN PUBLIC KEY-----”开头 “-----END PUBLIC KEY-----” 结尾）\n"
                    + "私钥：（以”-----BEGIN PRIVATE KEY-----”开头 “-----END PRIVATE KEY-----“ 结尾）"
                    + " or （以”-----BEGIN RSA PRIVATE KEY-----”开头 “-----END RSA PRIVATE KEY-----“ 结尾）")) {
                JOptionPane.showMessageDialog(UI, "先存放公私钥，并确保模式正确！");
                return;
            }
            String extName = JOptionPane.showInputDialog("请自定义Intruder名称:Encrypt365-*");
            if (extName != null) {
                if (extName.length() == 0) {
                    JOptionPane.showMessageDialog(UI, "名称不可为空!");
                    return;
                }
            } else return;

            setEntity();
            historyEntity.setRsaEntity(rsaEntity);
            api.intruder().registerPayloadProcessor(new RsaPayloadProcessor(api, injector, extName));
        });

    }

    /**
     * setEntity
     *
     * @param
     * @return void
     * @Author: Kang on 2023/10/10 14:43
     * 设置数据信息
     */
    public void setEntity() {

        rsaEntity.setPublicKey(this.publicOrPrivate.getText());
        rsaEntity.setTextValue(this.textValue.getText());

        if (publicKeyRadio.isSelected()) {
            rsaEntity.setSelectedRadio(true);//公钥加解密
        }
        if (privateKeyRadio.isSelected()) {
            rsaEntity.setSelectedRadio(false);//私钥加解密
        }

        if (this.padding.getSelectedIndex() == 0) {//PKCS1_PADDING
            rsaEntity.setPadding("PKCS1_PADDING");
        } else if (this.padding.getSelectedIndex() == 1) {//PKCS1_OAEP_PADDING
            rsaEntity.setPadding("PKCS1_OAEP_PADDING");
        } else if (this.padding.getSelectedIndex() == 2) {//SSLV23_PADDING
            rsaEntity.setPadding("SSLV23_PADDING");
        }

        if (outPut.getSelectedIndex() == 0) {
            rsaEntity.setOutPut("0"); //base64
        } else if (outPut.getSelectedIndex() == 1) {
            rsaEntity.setOutPut("1"); //hex
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

    /**
     * ini
     *
     * @param
     * @return void
     * @Author: Kang on 2023/10/10 14:43
     * 初始化
     */
    private void ini() {
        ButtonGroup bg = new ButtonGroup();
        bg.add(publicKeyRadio);
        bg.add(privateKeyRadio);
        publicKeyRadio.setSelected(true);

        String[] padding = new String[]{"PKCS1_PADDING"};
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

        publicOrPrivate.setForeground(Color.gray); //将提示文字设置为灰色
        publicOrPrivate.setText("公钥：（以“-----BEGIN PUBLIC KEY-----”开头 “-----END PUBLIC KEY-----” 结尾）\n"
                + "私钥：（以”-----BEGIN PRIVATE KEY-----”开头 “-----END PRIVATE KEY-----“ 结尾）"
                + " or （以”-----BEGIN RSA PRIVATE KEY-----”开头 “-----END RSA PRIVATE KEY-----“ 结尾）");     //显示提示文字
        publicOrPrivate.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
                //得到焦点时，当前文本框的提示文字和创建该对象时的提示文字一样，说明用户正要键入内容
                if (publicOrPrivate.getText().equals("公钥：（以“-----BEGIN PUBLIC KEY-----”开头 “-----END PUBLIC KEY-----” 结尾）\n私钥：（以”-----BEGIN PRIVATE KEY-----”开头 “-----END PRIVATE KEY-----“ 结尾） or （以”-----BEGIN RSA PRIVATE KEY-----”开头 “-----END RSA PRIVATE KEY-----“ 结尾）")) {
                    publicOrPrivate.setText("");     //将提示文字清空
                    publicOrPrivate.setForeground(Color.black);  //设置用户输入的字体颜色为黑色
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                //失去焦点时，用户尚未在文本框内输入任何内容，所以依旧显示提示文字
                if (publicOrPrivate.getText().isEmpty()) {
                    publicOrPrivate.setForeground(Color.gray); //将提示文字设置为灰色
                    publicOrPrivate.setText("公钥：（以“-----BEGIN PUBLIC KEY-----”开头 “-----END PUBLIC KEY-----” 结尾）\n"
                            + "私钥：（以”-----BEGIN PRIVATE KEY-----”开头 “-----END PRIVATE KEY-----“ 结尾）"
                            + " or （以”-----BEGIN RSA PRIVATE KEY-----”开头 “-----END RSA PRIVATE KEY-----“ 结尾）");     //显示提示文字
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
        panel1.add(label1, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        padding = new JComboBox();
        panel1.add(padding, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(100, -1), null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("输出格式：");
        panel1.add(label2, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        outPut = new JComboBox();
        panel1.add(outPut, new GridConstraints(0, 4, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(100, -1), null, 0, false));
        encodeButton = new JButton();
        encodeButton.setText("加密");
        panel1.add(encodeButton, new GridConstraints(0, 7, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, new Dimension(100, 35), null, null, 0, false));
        decodeButton = new JButton();
        decodeButton.setText("解密[解密对应私钥的加密]");
        panel1.add(decodeButton, new GridConstraints(0, 8, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, new Dimension(100, 35), null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("字符集：");
        panel1.add(label3, new GridConstraints(0, 5, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        charset_Box = new JComboBox();
        panel1.add(charset_Box, new GridConstraints(0, 6, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(panel2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel2.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(-4519936)), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        final JLabel label4 = new JLabel();
        label4.setText("模式：");
        panel2.add(label4, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        publicKeyRadio = new JRadioButton();
        publicKeyRadio.setText("公钥");
        panel2.add(publicKeyRadio, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        privateKeyRadio = new JRadioButton();
        privateKeyRadio.setText("私钥");
        panel2.add(privateKeyRadio, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        addIntruderButton = new JButton();
        addIntruderButton.setText("添加Intruder");
        panel1.add(addIntruderButton, new GridConstraints(0, 9, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, new Dimension(100, 35), null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 5, 5, 5);
        UI.add(panel3, gbc);
        final JLabel label5 = new JLabel();
        label5.setForeground(new Color(-1764602));
        label5.setText("非对称加密公/私钥：");
        panel3.add(label5, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JScrollPane scrollPane1 = new JScrollPane();
        panel3.add(scrollPane1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(-1, 150), null, 0, false));
        publicOrPrivate = new JTextArea();
        publicOrPrivate.setLineWrap(true);
        publicOrPrivate.setRows(0);
        publicOrPrivate.setWrapStyleWord(true);
        scrollPane1.setViewportView(publicOrPrivate);
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 5, 5, 5);
        UI.add(panel4, gbc);
        final JLabel label6 = new JLabel();
        label6.setForeground(new Color(-15014638));
        label6.setText("需要加密/解密的文本：");
        panel4.add(label6, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JScrollPane scrollPane2 = new JScrollPane();
        panel4.add(scrollPane2, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(-1, 150), null, 0, false));
        textValue = new JTextArea();
        textValue.setLineWrap(true);
        textValue.setWrapStyleWord(true);
        scrollPane2.setViewportView(textValue);
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 5, 5, 5);
        UI.add(panel5, gbc);
        final JLabel label7 = new JLabel();
        label7.setForeground(new Color(-12959003));
        label7.setText("公/私钥加解密转换结果");
        panel5.add(label7, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JScrollPane scrollPane3 = new JScrollPane();
        panel5.add(scrollPane3, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(-1, 150), null, 0, false));
        outputValue = new JTextArea();
        outputValue.setLineWrap(true);
        outputValue.setWrapStyleWord(true);
        scrollPane3.setViewportView(outputValue);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return UI;
    }

}