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

import burp.api.montoya.BurpExtension;
import burp.api.montoya.MontoyaApi;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.kang.Encrypt365;
import com.kang.entity.EnumEntity;
import com.kang.view.rewrite.TabbedMenu;
import com.kang.util.Utils;
import com.kang.view.asymmetricCryptography.RsaCreate.RsaCreateUi;
import com.kang.view.asymmetricCryptography.RsaEncrypt.RsaEncryptUI;
import com.kang.view.historyUi.historyUI;
import com.kang.view.aesui.AesUI;
import com.kang.view.baseui.BaseUI;
import com.kang.view.hashui.HashUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainUI {
    public JPanel UI;
    private JTabbedPane uiList;
    private JPanel panel;
    private final JPopupMenu popup = new JPopupMenu();
    public static int sequence = 1; // 序号
    private final JMenuItem base = new JMenuItem(EnumEntity.Base.name());
    private final JMenuItem hash = new JMenuItem(EnumEntity.Hash.name());
    private final JMenuItem aes = new JMenuItem(EnumEntity.Aes.name());
    private final JMenu ac = new JMenu(EnumEntity.非对称加解密.name());
    private final JMenuItem rsaEncrypt = new JMenuItem(EnumEntity.Rsa加解密.name());
    private final JMenuItem rsaCreate = new JMenuItem(EnumEntity.Rsa生成.name());
    @Inject
    private historyUI history;
    public Injector injector;

    public MainUI(MontoyaApi api, Injector injector) {
        this.injector = injector;
        history = injector.getInstance(historyUI.class);//注入
        history.setInjector(injector, api.logging());//传递注入
        init();

        //菜单添加监听
        uiList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                if (evt.getButton() == MouseEvent.BUTTON1) {
                    int tabIndex = uiList.indexAtLocation(evt.getX(), evt.getY());
                    if (tabIndex >= 0 && uiList.getTitleAt(tabIndex).equals("+")) {
                        popup.show(uiList, evt.getX(), evt.getY());
                    }
                }
            }
        });

        //按钮监听
        base.addActionListener(e -> {
            addTable(new BaseUI(api, injector).UI, EnumEntity.Base$.name());
        });
        hash.addActionListener(e -> {
            addTable(new HashUI(api, injector).UI, EnumEntity.Hash$.name());
        });
        aes.addActionListener(e -> {
            addTable(new AesUI(api, injector).UI, EnumEntity.Aes$.name());
        });
        rsaEncrypt.addActionListener(e -> {
            addTable(new RsaEncryptUI(api, injector).UI, EnumEntity.Rsa加解密$.name());
        });
        rsaCreate.addActionListener(e -> {
            addTable(new RsaCreateUi(api, injector).UI, EnumEntity.Rsa生成$.name());
        });

        //选项卡右键菜单
        TabbedMenu tabbedMenu = new TabbedMenu(uiList);
        uiList.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int index = uiList.indexAtLocation(e.getX(), e.getY()); // 鼠标点击位置的选项卡索引
                // 实现左键切换选项卡
/*                if (e.getModifiers() == MouseEvent.BUTTON1_MASK) {
                    if (index > -1) {
                        uiList.setSelectedIndex(index);
                    }
                }*/
                if (e.getModifiers() == MouseEvent.BUTTON3_MASK) {
                    // 右键点击，弹出菜单
                    if (index > -1) {
                        String title = uiList.getTitleAt(index);
                        if (!"+".equalsIgnoreCase(title)) {
                            tabbedMenu.showTabbedMenu(e);
                        }
                    }
                }
            }
        });

    }

    /**
     * addTable
     *
     * @param UI
     * @param title
     * @return void
     * @Author: Kang on 2023/10/10 15:00
     * 创建新的选项卡
     */
    public void addTable(JPanel UI, String title) {
        ImageIcon closeImage = Utils.loadImage("images/close.png", 18, 18);
        // 创建一个标签和按钮，以便关闭选项卡
        JButton closeButton = new JButton("x");

        closeButton.setBorderPainted(false);
        closeButton.addActionListener(e -> {
            int index = uiList.indexOfComponent(UI);
            if (index != -1) {
                uiList.removeTabAt(index);
            }
        });

        // 将标签和按钮添加到选项卡的标题组件
        uiList.insertTab(null, null, UI, null, uiList.getTabCount() - 1);
        JPanel tabTitle = new JPanel();
        tabTitle.setOpaque(false);
        tabTitle.setLayout(new BorderLayout());
        JLabel label = new JLabel(title + sequence + " ");
        tabTitle.add(label, BorderLayout.WEST);
        JLabel ico = new JLabel();
        tabTitle.add(ico, BorderLayout.EAST);
        ico.setIcon(closeImage);
        ico.setSize(10, 10);

        uiList.setTabComponentAt(uiList.getTabCount() - 2, tabTitle);

        //图片监听，放置在该选项卡前移位
        ico.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getModifiers() == MouseEvent.BUTTON1_MASK) {
                    JLabel source = (JLabel) e.getSource();
                    Component parent = source.getParent();
                    int index = uiList.indexOfTabComponent(parent);
                    uiList.removeTabAt(index);
                    if (index == uiList.getSelectedIndex() && index == uiList.getTabCount() - 1)
                        uiList.setSelectedIndex(index - 1);
                }
            }
        });
        uiList.setSelectedComponent(UI);
        sequence++;
    }

    /**
     * init
     *
     * @param
     * @return void
     * @Author: Kang on 2023/10/10 15:01
     * 初始化
     */
    public void init() {
        uiList.addTab("历史", history.UI);
        uiList.addTab("+", new JPanel());
        //给uiList.addTab("+", new JPanel());添加图片
        ImageIcon closeImage = Utils.loadImage("images/add.png", 17, 17);
        JPanel tabTitle = new JPanel();
        JLabel label = new JLabel();
        tabTitle.setOpaque(false);
        tabTitle.setLayout(new BorderLayout());
        label.setIcon(closeImage);
        tabTitle.add(label, BorderLayout.CENTER);

        uiList.setTabComponentAt(uiList.getTabCount() - 1, tabTitle);
        uiList.setEnabledAt(1, false);

        ac.add(rsaEncrypt);
        ac.add(rsaCreate);
        popup.add(base);
        popup.add(hash);
        popup.add(aes);
        popup.add(ac);
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
        panel1.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        UI.add(panel1, gbc);
        final JScrollPane scrollPane1 = new JScrollPane();
        panel1.add(scrollPane1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, 1, 1, null, null, null, 0, false));
        uiList = new JTabbedPane();
        scrollPane1.setViewportView(uiList);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return UI;
    }

}
