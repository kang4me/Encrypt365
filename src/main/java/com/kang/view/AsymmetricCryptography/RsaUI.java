/*
 * @Author: Kang
 * @Version: 1.0
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.kang.view.AsymmetricCryptography;

import burp.api.montoya.MontoyaApi;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.kang.view.AsymmetricCryptography.RsaCreate.RsaCreateUi;
import com.kang.view.AsymmetricCryptography.RsaPrivateKeyEncrypt.RsaPrivateKeyEncryptUI;
import com.kang.view.AsymmetricCryptography.RsaPublicKeyEncrypt.RsaPublicKeyEncryptUI;

import javax.swing.*;
import java.awt.*;

public class RsaUI {
    public JPanel root;
    private JTabbedPane RsaUi;

    public RsaUI(MontoyaApi api) {
        RsaUi.addTab("RSA密钥加解密", new RsaPrivateKeyEncryptUI(api).UI);
        RsaUi.addTab("RSA公钥加解密", new RsaPublicKeyEncryptUI(api).UI);
        //RsaUi.addTab("RSA公私钥生成", new RsaCreateUi().UI);
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
        root = new JPanel();
        root.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        RsaUi = new JTabbedPane();
        root.add(RsaUi, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(200, 200), null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return root;
    }

}