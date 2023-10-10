/*
 * @Author: Kang
 * @Version: 1.0
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.kang.test;


import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Test{
    private JFrame frame;
    private JList<String> list;
    private JTable table;
    private DefaultTableModel tableModel;
    private List<Object[]> data; // 用于存储表格数据

    public Test() {
        frame = new JFrame("Dynamic List and Table Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // 创建JList并添加选项
        String[] listItems = {"All", "Base", "Other"};
        list = new JList<>(listItems);
        frame.add(new JScrollPane(list), BorderLayout.WEST);

        // 创建JTable和DefaultTableModel
        tableModel = new DefaultTableModel();
        tableModel.addColumn("Column 1");
        tableModel.addColumn("Column 2");
        tableModel.addColumn("Column 3");
        tableModel.addColumn("Column 4");

        table = new JTable(tableModel);
        frame.add(new JScrollPane(table), BorderLayout.CENTER);

        // 初始化数据结构并添加初始数据
        data = new ArrayList<>();
        data.add(new Object[]{1, 2, 3, 4});
        data.add(new Object[]{5, 6, 7, 8});

        // 添加JList的选择事件监听器
        list.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                updateTableData();
            }
        });

        // 初始化JTable数据为"All"
        list.setSelectedValue("All", true);

        frame.pack();
        frame.setVisible(true);
    }

    // 更新JTable的数据
    private void updateTableData() {
        String selectedValue = list.getSelectedValue();
        tableModel.setRowCount(0); // 清空表格数据

        for (Object[] row : data) {
            if ("All".equals(selectedValue) || selectedValue.equals(row[0])) {
                tableModel.addRow(row);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Test();
            }
        });
    }
}
