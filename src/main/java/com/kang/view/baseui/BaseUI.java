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
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.kang.burpApi.PayloadGeneratorProvider.BasePayloadProcessor;
import com.kang.entity.BaseEntity;
import com.kang.entity.EnumEntity;
import com.kang.entity.HistoryEntity;
import com.kang.service.BaseEncrypt;
import com.kang.service.History;

import javax.swing.*;
import java.awt.*;

public class BaseUI {
    public JPanel UI;
    private JComboBox<String> base_ComboBox;
    private JButton encode_Button;
    public JTextArea base_encode;
    private JTextArea base_decode;
    private JButton decode_Button;
    private JScrollPane JSencode;
    private JScrollPane JSdecode;
    private JButton addIntruder;
    @Inject
    private final BaseEncrypt base_encrypt;
    private JPopupMenu pop;
    private BaseEntity baseEntity = new BaseEntity();
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
            baseEntity = base_encrypt.selecteEncodeMode(baseEntity);
            this.base_decode.setText(baseEntity.getOutput());
            historyEntity.setBaseEntity(baseEntity);
            history.baseHistory(injector);
        });
        this.decode_Button.addActionListener((e) -> {
            setEntity();
            baseEntity = base_encrypt.selecteDecodeMode(baseEntity);
            this.base_decode.setText(baseEntity.getOutput());
            historyEntity.setBaseEntity(baseEntity);
            history.baseHistory(injector);
        });
        this.addIntruder.addActionListener((e) -> {
            String extName = JOptionPane.showInputDialog("请自定义Intruder名称:Encrypt365-*");
            if (extName != null) {
                if (extName.length() == 0) {
                    JOptionPane.showMessageDialog(UI, "名称不可为空!");
                    return;
                }
            } else return;

            setEntity();
            historyEntity.setBaseEntity(baseEntity);
            api.intruder().registerPayloadProcessor(new BasePayloadProcessor(api, injector, extName));
        });
    }

    /**
     * setEntity
     *
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
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 1, new Insets(5, 5, 5, 5), -1, -1));
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        UI.add(panel1, gbc);
        JSencode = new JScrollPane();
        panel1.add(JSencode, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(-1, 150), null, 0, false));
        base_encode = new JTextArea();
        base_encode.setColumns(0);
        base_encode.setEditable(true);
        base_encode.setLineWrap(true);
        base_encode.setMargin(new Insets(5, 5, 5, 5));
        base_encode.setText("");
        base_encode.setWrapStyleWord(true);
        JSencode.setViewportView(base_encode);
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 5, 0, 5);
        UI.add(panel2, gbc);
        base_ComboBox = new JComboBox();
        base_ComboBox.setAlignmentY(0.5f);
        base_ComboBox.setMinimumSize(new Dimension(100, 150));
        base_ComboBox.setPreferredSize(new Dimension(250, 30));
        panel2.add(base_ComboBox);
        encode_Button = new JButton();
        encode_Button.setEnabled(true);
        encode_Button.setHideActionText(false);
        encode_Button.setHorizontalAlignment(0);
        encode_Button.setHorizontalTextPosition(11);
        encode_Button.setMinimumSize(new Dimension(150, 35));
        encode_Button.setPreferredSize(new Dimension(100, 35));
        encode_Button.setText("加密");
        encode_Button.setVerticalAlignment(0);
        encode_Button.setVerticalTextPosition(0);
        encode_Button.putClientProperty("hideActionText", Boolean.FALSE);
        encode_Button.putClientProperty("html.disable", Boolean.FALSE);
        panel2.add(encode_Button);
        decode_Button = new JButton();
        decode_Button.setHideActionText(false);
        decode_Button.setHorizontalAlignment(0);
        decode_Button.setHorizontalTextPosition(11);
        decode_Button.setMinimumSize(new Dimension(150, 35));
        decode_Button.setPreferredSize(new Dimension(100, 35));
        decode_Button.setText("解密");
        panel2.add(decode_Button);
        addIntruder = new JButton();
        addIntruder.setPreferredSize(new Dimension(120, 35));
        addIntruder.setText("添加Intruder");
        panel2.add(addIntruder);
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(1, 1, new Insets(5, 5, 5, 5), -1, -1));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        UI.add(panel3, gbc);
        JSdecode = new JScrollPane();
        panel3.add(JSdecode, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(-1, 150), null, 0, false));
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
