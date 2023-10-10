/*
 * @Author: Kang
 * @Version: 1.0
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.kang.view.historyUi;

import burp.api.montoya.logging.Logging;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.kang.entity.EnumEntity;
import com.kang.entity.HistoryEntity;
import com.kang.util.DateUtils;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.util.Locale;
import java.util.Objects;

public class historyUI {
    public JPanel UI;
    public JTable table1;
    private JList<EnumEntity> list1;
    public DefaultTableModel all = new DefaultTableModel();
    private final String[] column_names = {"#", "加/解密模式", "原文/公钥", "结果/私钥", "密码/公私钥", "时间"};
    private Injector injector;
    private Logging logging;
    @Inject
    private HistoryEntity historyEntity;

    public historyUI() {

        ini();

        //判断当前list选项，并设置Table
        list1.addListSelectionListener(e -> {
            all.setRowCount(0);//清除table
            switch (list1.getSelectedIndex()) {
                case 1 -> {
                    for (Object[] objects : historyEntity.data) {
                        if (objects[1].toString().equals("Base")) {
                            all.insertRow(0, objects);
                        }
                    }
                }
                case 2 -> {
                    for (Object[] objects : historyEntity.data) {
                        if (objects[1].toString().equals("Hash")) {
                            all.insertRow(0, objects);
                        }
                    }
                }
                case 3 -> {
                    for (Object[] objects : historyEntity.data) {
                        if (objects[1].toString().equals("Aes")) {
                            all.insertRow(0, objects);
                        }
                    }
                }
                case 4 -> {
                    for (Object[] objects : historyEntity.data) {
                        if (objects[1].toString().equals("Rsa加解密")) {
                            all.insertRow(0, objects);
                        }
                    }
                }
                case 5 -> {
                    for (Object[] objects : historyEntity.data) {
                        if (objects[1].toString().equals("Rsa生成")) {
                            all.insertRow(0, objects);
                        }
                    }
                }
                default -> {
                    for (Object[] objects : historyEntity.data) {
                        all.insertRow(0, objects);
                    }
                }
            }
        });
    }

    /**
     * ini
     * @param 
     * @return void
     * @Author: Kang on 2023/10/10 14:50
     * 初始化
     */
    public void ini() {
        DefaultListModel<EnumEntity> dlm = new DefaultListModel<>();
        dlm.addElement(EnumEntity.所有);
        dlm.addElement(EnumEntity.Base);
        dlm.addElement(EnumEntity.Hash);
        dlm.addElement(EnumEntity.Aes);
        dlm.addElement(EnumEntity.Rsa加解密);
        dlm.addElement(EnumEntity.Rsa生成);

        list1.setModel(dlm);
        list1.setSelectedIndex(0);

        for (String value : column_names) {
            all.addColumn(value);
        }
        table1.setModel(all);

        //居中
        DefaultTableCellRenderer dc = new DefaultTableCellRenderer();
        dc.setHorizontalAlignment(JLabel.CENTER);
        table1.setDefaultRenderer(Object.class, dc);

        //TODO 设置列宽
        table1.getColumnModel().getColumn(0).setPreferredWidth(1);
        table1.getColumnModel().getColumn(1).setPreferredWidth(1);
        table1.getColumnModel().getColumn(5).setPreferredWidth(5);

        JTableHeader header = table1.getTableHeader();
        header.setBackground(new Color(245, 246, 247)); // 设置表头背景色
        header.setForeground(Color.BLACK); // 设置表头前景色
        header.setFont(new Font("宋体", Font.BOLD, 12)); // 设置表头字体
    }

    /**
     * setInjector
     * @param injector
     * @param logging
     * @return void
     * @Author: Kang on 2023/10/10 14:50
     * 该类是注入类，于MainUI的第52行，该类由于注入原因，无法带参构造注入，setInjector需要先赋值
     */
    public void setInjector(Injector injector, Logging logging) {
        this.logging = logging;
        historyEntity = injector.getInstance(HistoryEntity.class);
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
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 5, 5, 5);
        UI.add(panel1, gbc);
        final JLabel label1 = new JLabel();
        label1.setText("日志：");
        panel1.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 5, 5, 5);
        UI.add(panel2, gbc);
        final JScrollPane scrollPane1 = new JScrollPane();
        panel2.add(scrollPane1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        list1 = new JList();
        Font list1Font = this.$$$getFont$$$(null, -1, 14, list1.getFont());
        if (list1Font != null) list1.setFont(list1Font);
        scrollPane1.setViewportView(list1);
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 5, 5, 5);
        UI.add(panel3, gbc);
        final JScrollPane scrollPane2 = new JScrollPane();
        panel3.add(scrollPane2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        table1 = new JTable();
        scrollPane2.setViewportView(table1);
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        Font font = new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
        boolean isMac = System.getProperty("os.name", "").toLowerCase(Locale.ENGLISH).startsWith("mac");
        Font fontWithFallback = isMac ? new Font(font.getFamily(), font.getStyle(), font.getSize()) : new StyleContext().getFont(font.getFamily(), font.getStyle(), font.getSize());
        return fontWithFallback instanceof FontUIResource ? fontWithFallback : new FontUIResource(fontWithFallback);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return UI;
    }

}
