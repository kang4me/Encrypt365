/*
 * @Author: Kang
 * @Version: 1.0
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.kang.view.rewrite;

import javax.swing.*;
import java.awt.event.MouseEvent;

public class TabbedMenu extends JPopupMenu{
    JTabbedPane tabbedPane;
    // 右键选中的选项卡索引
    private int selectIndex;
    // 选项卡数量
    private int tabCount;

    private final JMenuItem closeTabItem = new JMenuItem("关闭", null);
    private final JMenuItem closeOtherMenuItem = new JMenuItem("关闭其他", null);
    private final JMenuItem closeAllMenuItem = new JMenuItem("全部关闭", null);
    private final JMenuItem closeRightMenuItem = new JMenuItem("关闭右侧选项卡", null);


    public TabbedMenu(JTabbedPane tabbedPane) {
        this.tabbedPane = tabbedPane;
        this.setFocusable(false);
        initTabbedMenuListeners(); // 添加右键菜单项监听
    }

    private void initTabbedMenuListeners() {
        // 关闭 操作
        this.closeTabItem.addActionListener(e -> closeTabAction());
        // 关闭其它 操作
        this.closeOtherMenuItem.addActionListener(e -> closeOtherTabAction());
        // 全部关闭 操作
        this.closeAllMenuItem.addActionListener(e -> closeAllTabAction());
        // 关闭右侧选项卡 操作
        this.closeRightMenuItem.addActionListener(e -> closeRightTabAction());
    }

    // 关闭 操作
    private void closeTabAction() {
        tabbedPane.remove(selectIndex);
        tabbedPane.setSelectedIndex(selectIndex - 1);
    }

    // 关闭其它 操作
    private void closeOtherTabAction() {
        if (selectIndex == 0) {
            for (int i = 1; i < tabCount - 1; i++) {
                tabbedPane.removeTabAt(1);
            }
        } else if (selectIndex > 0) {
            for (int i = 1; i < selectIndex; i++) {
                tabbedPane.removeTabAt(1);
            }
            for (int i = selectIndex + 1; i < tabCount - 1; i++) {
                tabbedPane.removeTabAt(2);
            }
        }
        tabbedPane.setSelectedIndex(1);
    }

    // 全部关闭 操作
    private void closeAllTabAction() {
        for (int i = 1; i < tabCount - 1; i++) {
            tabbedPane.removeTabAt(1);
        }
        tabbedPane.setSelectedIndex(0);
    }

    // 关闭右侧选项卡 操作
    private void closeRightTabAction() {
        int index = selectIndex + 1;
        for (int i = index; i < tabCount - 1; i++) {
            tabbedPane.removeTabAt(index);
        }
        tabbedPane.setSelectedIndex(selectIndex);
    }

    public void showTabbedMenu(MouseEvent e) {
        this.removeAll(); // 先移去全部组件
        createTabbedMenu();
        selectIndex = tabbedPane.indexAtLocation(e.getX(), e.getY()); // 获取鼠标右键的选项卡索引
        tabCount = tabbedPane.getTabCount();
        if (selectIndex == 0) {
            this.closeTabItem.setEnabled(false);
        } else if (selectIndex > 0) {
            this.closeTabItem.setEnabled(true);
        }
        // 如果只有一个选项卡，则不能全部关闭
        if (tabCount == 2) {
            this.closeOtherMenuItem.setEnabled(false);
            this.closeAllMenuItem.setEnabled(false);
            this.closeRightMenuItem.setEnabled(false);
        } else {
            this.closeOtherMenuItem.setEnabled(true);
            this.closeAllMenuItem.setEnabled(true);
            this.closeRightMenuItem.setEnabled(true);
        }
        this.show(e.getComponent(), e.getX(), e.getY()); // 右键菜单显示位置*/

    }

    // 创建右键菜单
    private void createTabbedMenu() {
        this.add(closeTabItem);
        this.add(new JPopupMenu.Separator());
        this.add(closeOtherMenuItem);
        this.add(closeAllMenuItem);
        this.add(new JPopupMenu.Separator());
        this.add(closeRightMenuItem);
    }
}
