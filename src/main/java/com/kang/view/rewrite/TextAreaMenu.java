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

import com.kang.entity.EnumEntity;

import javax.swing.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.*;

public class TextAreaMenu extends JTextArea implements MouseListener{
    private static final long serialVersionUID = -2308615404205560110L;

    private JPopupMenu pop = new JPopupMenu(); // 弹出菜单
    private JMenu jMenu;
    private JMenuItem copy = null, paste = null, cut = null, base = null, hash = null, aes = null, rsaEncrypt = null, rsaCreate = null; // 三个功能菜单

    public TextAreaMenu() {
        super();
        init();
    }

    private void init() {
        this.addMouseListener(this);
        pop.add(copy = new JMenuItem(EnumEntity.复制.name()));
        pop.add(paste = new JMenuItem(EnumEntity.粘贴.name()));
        pop.add(cut = new JMenuItem(EnumEntity.剪切.name()));
        copy.setAccelerator(KeyStroke.getKeyStroke('C', InputEvent.CTRL_MASK));
        paste.setAccelerator(KeyStroke.getKeyStroke('V', InputEvent.CTRL_MASK));
        cut.setAccelerator(KeyStroke.getKeyStroke('X', InputEvent.CTRL_MASK));
        pop.addSeparator();
        pop.add(base = new JMenuItem(EnumEntity.Base.name()));
        pop.add(hash = new JMenuItem(EnumEntity.Hash.name()));
        pop.add(aes = new JMenuItem(EnumEntity.Aes.name()));
        pop.add(jMenu = new JMenu(EnumEntity.非对称加解密.name()));
        jMenu.add(rsaEncrypt = new JMenuItem(EnumEntity.Rsa加解密.name()));
        jMenu.add(rsaCreate = new JMenuItem(EnumEntity.Rsa生成.name()));

        copy.addActionListener(this::action);
        paste.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                action(e);
            }
        });
        cut.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                action(e);
            }
        });

        /*base.addActionListener(e -> {
            MainUI.addTable(new BaseUI(api).UI, nameMap.Base$.name());
        });
        hash.addActionListener(e -> {
            addTable(new HashUI(api).UI, nameMap.Hash$.name());
        });
        aes.addActionListener(e -> {
            addTable(new AesUI(api).UI, nameMap.Aes$.name());
        });
        rsaEncrypt.addActionListener(e -> {
            addTable(new RsaEncryptUI(api).UI, nameMap.Rsa加解密$.name());
        });
        rsaCreate.addActionListener(e -> {
            addTable(new RsaCreateUi(api).UI, nameMap.Rsa生成$.name());
        });*/


        this.add(pop);
    }


    public void action(ActionEvent e) {
        String str = e.getActionCommand();
        if (str.equals(copy.getText())) { // 复制
            this.copy();
        } else if (str.equals(paste.getText())) { // 粘贴
            this.paste();
        } else if (str.equals(cut.getText())) { // 剪切
            this.cut();
        }
    }

    public JPopupMenu getPop() {
        return pop;
    }

    public void setPop(JPopupMenu pop) {
        this.pop = pop;
    }


    public boolean isClipboardString() {
        boolean b = false;
        Clipboard clipboard = this.getToolkit().getSystemClipboard();
        Transferable content = clipboard.getContents(this);
        try {
            if (content.getTransferData(DataFlavor.stringFlavor) instanceof String) {
                b = true;
            }
        } catch (Exception e) {
        }
        return b;
    }


    public boolean isCanCopy() {
        boolean b = false;
        int start = this.getSelectionStart();
        int end = this.getSelectionEnd();
        if (start != end)
            b = true;
        return b;
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON3) {
            copy.setEnabled(isCanCopy());
            paste.setEnabled(isClipboardString());
            cut.setEnabled(isCanCopy());
            pop.show(this, e.getX(), e.getY());
        }
    }

    public void mouseReleased(MouseEvent e) {
    }

}
