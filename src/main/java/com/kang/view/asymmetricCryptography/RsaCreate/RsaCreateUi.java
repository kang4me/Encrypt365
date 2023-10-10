/*
 * @Author: Kang
 * @Version: 1.0
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.kang.view.asymmetricCryptography.RsaCreate;

import burp.api.montoya.MontoyaApi;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.kang.entity.HistoryEntity;
import com.kang.entity.RsaEntity;
import com.kang.service.History;
import com.kang.service.Impl.RsaCreateImpl;
import com.kang.service.RsaCreate;
import org.apache.commons.codec.binary.Hex;

import javax.swing.*;
import java.awt.*;

public class RsaCreateUi {
    public JPanel UI;
    private JComboBox<String> createBit;
    private JComboBox<String> keyFormat;
    private JComboBox<String> outPut;
    private JButton encodeButton;
    private JTextArea publicKey;
    private JTextArea privateKey;
    private RsaEntity rsaEntity;

    private final MontoyaApi api;
    @Inject
    private HistoryEntity historyEntity;
    @Inject
    private History history;
    @Inject
    private RsaCreate rsaCreate;

    public RsaCreateUi(MontoyaApi api, Injector injector) {
        this.api = api;
        rsaCreate = injector.getInstance(RsaCreate.class);
        history = injector.getInstance(History.class);
        historyEntity = injector.getInstance(HistoryEntity.class);

        ini();

        this.encodeButton.addActionListener(e -> {
            setEntity();
            create();
            historyEntity.setRsaEntity(rsaEntity);//数据传递
            history.rsaCreateHistory(injector);//注入传递
        });
    }

    /**
     * create
     * @param 
     * @return void
     * @Author: Kang on 2023/10/10 14:40
     * 数据生成
     */
    public void create() {
        try {
            rsaEntity = rsaCreate.Create(rsaEntity);
            if (keyFormat.getSelectedIndex() == 0) {
                if (outPut.getSelectedIndex() == 0) {
                    rsaEntity.setPublicKey(rsaCreate.publicKeyToPEM(rsaEntity.getPublicKeyApi()));
                    rsaEntity.setPrivateKey(rsaCreate.privateKeyToPEM(rsaEntity.getPrivateKeyApi()));
                } else if (outPut.getSelectedIndex() == 2) {
                    rsaEntity.setPublicKey(new String(Hex.encodeHex(rsaEntity.getPublicKeyApi().getEncoded())));
                    rsaEntity.setPrivateKey(rsaCreate.privateKeyToPEM(rsaEntity.getPrivateKeyApi()));
                } else if (outPut.getSelectedIndex() == 3) {
                    rsaEntity.setPublicKey(rsaCreate.publicKeyToPEM(rsaEntity.getPublicKeyApi()));
                    rsaEntity.setPrivateKey(new String(Hex.encodeHex(rsaEntity.getPrivateKeyApi().getEncoded())));
                }
            } else if (keyFormat.getSelectedIndex() == 1) {
                if (outPut.getSelectedIndex() == 0) {
                    rsaEntity.setPublicKey(rsaCreate.getPublicKeyAsPem(rsaEntity.getPublicKeyApi()));
                    rsaEntity.setPrivateKey(rsaCreate.getPrivateKeyAsPem(rsaEntity.getPrivateKeyApi()));
                } else if (outPut.getSelectedIndex() == 2) {
                    rsaEntity.setPublicKey(new String(Hex.encodeHex(rsaEntity.getPublicKeyApi().getEncoded())));
                    rsaEntity.setPrivateKey(rsaCreate.getPrivateKeyAsPem(rsaEntity.getPrivateKeyApi()));
                } else if (outPut.getSelectedIndex() == 3) {
                    rsaEntity.setPublicKey(rsaCreate.getPublicKeyAsPem(rsaEntity.getPublicKeyApi()));
                    rsaEntity.setPrivateKey(new String(Hex.encodeHex(rsaEntity.getPrivateKeyApi().getEncoded())));
                }
            }
            if (outPut.getSelectedIndex() == 1) {
                rsaEntity.setPublicKey(new String(Hex.encodeHex(rsaEntity.getPublicKeyApi().getEncoded())));
                rsaEntity.setPrivateKey(new String(Hex.encodeHex(rsaEntity.getPrivateKeyApi().getEncoded())));
            }
            this.publicKey.setText(rsaEntity.getPublicKey());
            this.privateKey.setText(rsaEntity.getPrivateKey());
        } catch (Exception e) {
            api.logging().logToError(e);
            throw new RuntimeException(e);
        }

    }

    /**
     * setEntity
     * @param
     * @return void
     * @Author: Kang on 2023/10/10 14:42
     * 设置数据信息
     */
    public void setEntity() {
        rsaEntity = new RsaEntity();
        if (createBit.getSelectedIndex() == 0) {//512
            rsaEntity.setBit(512);
        } else if (createBit.getSelectedIndex() == 1) {//1024
            rsaEntity.setBit(1024);
        } else if (createBit.getSelectedIndex() == 2) {//2048
            rsaEntity.setBit(2048);
        } else if (createBit.getSelectedIndex() == 3) {//3072
            rsaEntity.setBit(3072);
        } else if (createBit.getSelectedIndex() == 4) {//4096
            rsaEntity.setBit(4096);
        }

        if (keyFormat.getSelectedIndex() == 0) {

        } else if (keyFormat.getSelectedIndex() == 1) {

        }


    }

    /**
     * ini
     * @param
     * @return void
     * @Author: Kang on 2023/10/10 14:42
     * 初始化设置
     */
    private void ini() {
        String[] createBitStr = new String[]{"512位(bit)", "1024位(bit)", "2048位(bit)", "3072位(bit)", "4096位(bit)"};
        String[] keyFormatStr = new String[]{"PKCS#1", "PKCS#8"};
        String[] outPutStr = new String[]{"PEM", "Hex", "Hex公钥", "Hex私钥"};

        for (String value : createBitStr) {
            createBit.addItem(value);
        }
        for (String value : keyFormatStr) {
            keyFormat.addItem(value);
        }
        for (String value : outPutStr) {
            outPut.addItem(value);
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
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 7, new Insets(0, 0, 0, 0), -1, -1));
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        UI.add(panel1, gbc);
        final JLabel label1 = new JLabel();
        label1.setText("生成密钥位数：");
        panel1.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        createBit = new JComboBox();
        panel1.add(createBit, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(100, -1), null, 1, false));
        final JLabel label2 = new JLabel();
        label2.setText("密钥格式：");
        panel1.add(label2, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        keyFormat = new JComboBox();
        panel1.add(keyFormat, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(100, -1), null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("输出格式：");
        panel1.add(label3, new GridConstraints(0, 4, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        outPut = new JComboBox();
        panel1.add(outPut, new GridConstraints(0, 5, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(100, -1), null, 0, false));
        encodeButton = new JButton();
        encodeButton.setText("生成");
        panel1.add(encodeButton, new GridConstraints(0, 6, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, new Dimension(100, 35), null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 0.7;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 5, 5, 5);
        UI.add(panel2, gbc);
        final JLabel label4 = new JLabel();
        label4.setForeground(new Color(-15932142));
        label4.setText("非对称加密公钥：");
        panel2.add(label4, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JScrollPane scrollPane1 = new JScrollPane();
        panel2.add(scrollPane1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(-1, 150), null, 0, false));
        publicKey = new JTextArea();
        publicKey.setLineWrap(true);
        publicKey.setWrapStyleWord(true);
        scrollPane1.setViewportView(publicKey);
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
        final JLabel label5 = new JLabel();
        label5.setForeground(new Color(-1763052));
        label5.setText("非对称加密私钥：");
        panel3.add(label5, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JScrollPane scrollPane2 = new JScrollPane();
        panel3.add(scrollPane2, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(-1, 200), null, 0, false));
        privateKey = new JTextArea();
        privateKey.setLineWrap(true);
        privateKey.setWrapStyleWord(true);
        scrollPane2.setViewportView(privateKey);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return UI;
    }

}
