/*
 * @Author: Kang
 * @Version: 1.0
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.kang.view.hashui;


import burp.api.montoya.MontoyaApi;
import burp.api.montoya.logging.Logging;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.kang.burpApi.PayloadGeneratorProvider.HashPayloadProcessor;
import com.kang.config.ConfigFile;
import com.kang.entity.HistoryEntity;
import com.kang.entity.HashEntity;
import com.kang.service.History;
import com.kang.service.Md5Encrypt;
import com.kang.service.Md5EncryptApi;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import javax.swing.*;
import java.awt.*;

public class HashUI {
    public JPanel UI;
    private JComboBox<String> Link_URL;
    private JTextField Email_text;
    private JTextField Key_text;
    private JButton Save_Button;
    private JButton Reset_Button;
    public JTextArea inputString_Text;
    private JTextArea outputString_Text;
    private JTextArea Log_Text;
    private JButton Decode_butten;
    private JButton Encode_butten;
    private JComboBox<String> Hash_ComboBox;
    private JScrollPane JS1;
    private JScrollPane JS2;
    private JRadioButton No_vip;
    private JRadioButton Yes_vip;
    private JButton addIntruderButton;
    private final String[] str = {"MD5-32", "MD5-16", "SHA-1", "SHA-224", "SHA-256", "SHA-384", "SHA-512"};
    private final String[] link = {"CMD5", "MD5免费在线"};
    private final String[] url = {"https://www.cmd5.com/", "https://www.somd5.com/"};
    private HashEntity md5_entity = new HashEntity();
    private ConfigFile configFile;
    private final Logging log;
    @Inject
    private final Md5Encrypt md5_encrypt;
    @Inject
    private History history;
    @Inject
    private HistoryEntity historyEntity;
    @Inject
    private Md5EncryptApi md5_encrypt_api;

    public HashUI(MontoyaApi api, Injector injector) {
        log = api.logging();
        md5_encrypt = injector.getInstance(Md5Encrypt.class);
        historyEntity = injector.getInstance(HistoryEntity.class);
        history = injector.getInstance(History.class);
        md5_encrypt_api = injector.getInstance(Md5EncryptApi.class);

        init();//初始化

        iniFile();//初始化配置文件

        this.Encode_butten.addActionListener((e) -> {
            setMd5_entity();
            md5_entity = md5_encrypt.selecteEncodeMode(md5_entity);
            this.outputString_Text.setText(md5_entity.getOutputString());
            historyEntity.setMd5Entity(md5_entity);
            history.hashHistory(injector);
        });

        this.Decode_butten.addActionListener((e) -> {
            setMd5_entity();
            md5_entity = md5_encrypt_api.selecteDecodeMode(md5_entity);
            this.Log_Text.setText(md5_entity.getLog_Text());
            this.outputString_Text.setText(md5_entity.getOutputString());
            historyEntity.setMd5Entity(md5_entity);
            history.hashHistory(injector);
        });

        this.Save_Button.addActionListener((e) -> {
            configFile = new ConfigFile();
            setMd5_entity();
            md5_entity.setLog_Text(configFile.save(md5_entity));

            this.Log_Text.setText(md5_entity.getLog_Text());
        });

        this.addIntruderButton.addActionListener((e) -> {
            String extName = JOptionPane.showInputDialog("请自定义Intruder名称:Encrypt365-*");
            if (extName != null) {
                if (extName.length() == 0) {
                    JOptionPane.showMessageDialog(UI, "名称不可为空!");
                    return;
                }
            } else return;

            setMd5_entity();
            historyEntity.setMd5Entity(md5_entity);
            api.intruder().registerPayloadProcessor(new HashPayloadProcessor(api, injector, extName));
        });


    }

    /**
     * iniFile
     *
     * @param
     * @return void
     * @Author: Kang on 2023/10/10 14:48
     * 初始化配置文件
     */
    public void iniFile() {
        configFile = new ConfigFile();
        md5_entity = configFile.getConfig(md5_entity);
        this.Email_text.setText(md5_entity.getEmail_text());
        this.Key_text.setText(md5_entity.getKey_text());
    }

    /**
     * setMd5_entity
     *
     * @param
     * @return void
     * @Author: Kang on 2023/10/10 14:48
     * 设置数据信息
     */
    private void setMd5_entity() {
        md5_entity.setInputString(this.inputString_Text.getText());
        md5_entity.setApiName(link[Link_URL.getSelectedIndex()]);
        md5_entity.setHash_ComboBox(str[Hash_ComboBox.getSelectedIndex()]);
        md5_entity.setUrl_api(url[this.Link_URL.getSelectedIndex()]);
        if (this.No_vip.isSelected()) {
            md5_entity.setVip(false);
        } else if (this.Yes_vip.isSelected()) {
            md5_entity.setVip(true);
        }
        md5_entity.setEmail_text(this.Email_text.getText());
        md5_entity.setKey_text((this.Key_text.getText()));
    }

    /**
     * init
     *
     * @param
     * @return void
     * @Author: Kang on 2023/10/10 14:48
     * 初始化
     */
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
        panel1.setLayout(new GridLayoutManager(3, 1, new Insets(0, 0, 0, 0), -1, -1));
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
        panel1.add(JS1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        inputString_Text = new JTextArea();
        inputString_Text.setLineWrap(true);
        inputString_Text.setWrapStyleWord(true);
        JS1.setViewportView(inputString_Text);
        JS2 = new JScrollPane();
        panel1.add(JS2, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        outputString_Text = new JTextArea();
        outputString_Text.setLineWrap(true);
        outputString_Text.setWrapStyleWord(true);
        JS2.setViewportView(outputString_Text);
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        panel1.add(panel2, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        Hash_ComboBox = new JComboBox();
        Hash_ComboBox.setPreferredSize(new Dimension(200, 30));
        panel2.add(Hash_ComboBox);
        Encode_butten = new JButton();
        Encode_butten.setText("加密");
        panel2.add(Encode_butten);
        addIntruderButton = new JButton();
        addIntruderButton.setText("添加Intruder");
        panel2.add(addIntruderButton);
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(9, 3, new Insets(0, 0, 0, 0), -1, -1));
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 5, 5, 5);
        UI.add(panel3, gbc);
        Link_URL = new JComboBox();
        panel3.add(Link_URL, new GridConstraints(0, 1, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        Email_text = new JTextField();
        panel3.add(Email_text, new GridConstraints(1, 1, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        Key_text = new JTextField();
        panel3.add(Key_text, new GridConstraints(2, 1, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        Save_Button = new JButton();
        Save_Button.setText("保存");
        panel3.add(Save_Button, new GridConstraints(4, 0, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        Reset_Button = new JButton();
        Reset_Button.setText("重置");
        panel3.add(Reset_Button, new GridConstraints(5, 0, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JScrollPane scrollPane1 = new JScrollPane();
        panel3.add(scrollPane1, new GridConstraints(8, 0, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        Log_Text = new JTextArea();
        Log_Text.setLineWrap(true);
        Log_Text.setWrapStyleWord(true);
        scrollPane1.setViewportView(Log_Text);
        final JLabel label1 = new JLabel();
        label1.setText("email：");
        panel3.add(label1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("key：");
        panel3.add(label2, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("日志：");
        panel3.add(label3, new GridConstraints(7, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label4 = new JLabel();
        label4.setText("接口：");
        panel3.add(label4, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        Decode_butten = new JButton();
        Decode_butten.setText("解密");
        panel3.add(Decode_butten, new GridConstraints(6, 0, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        No_vip = new JRadioButton();
        No_vip.setText("免费接口(有限制)");
        panel3.add(No_vip, new GridConstraints(3, 0, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        Yes_vip = new JRadioButton();
        Yes_vip.setText("付费接口");
        panel3.add(Yes_vip, new GridConstraints(3, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
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
