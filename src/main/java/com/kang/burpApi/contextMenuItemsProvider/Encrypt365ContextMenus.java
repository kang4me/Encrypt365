/*
 * @Author: Kang
 * @Version: 1.0
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.kang.burpApi.contextMenuItemsProvider;

import burp.api.montoya.MontoyaApi;
import burp.api.montoya.core.Range;
import burp.api.montoya.http.message.HttpRequestResponse;
import burp.api.montoya.ui.contextmenu.ContextMenuEvent;
import burp.api.montoya.ui.contextmenu.ContextMenuItemsProvider;
import burp.api.montoya.ui.contextmenu.MessageEditorHttpRequestResponse;
import com.kang.entity.EnumEntity;
import com.kang.view.MainUI;
import com.kang.view.aesui.AesUI;
import com.kang.view.asymmetricCryptography.RsaCreate.RsaCreateUi;
import com.kang.view.asymmetricCryptography.RsaEncrypt.RsaEncryptUI;
import com.kang.view.baseui.BaseUI;
import com.kang.view.hashui.HashUI;

import javax.swing.*;
import java.awt.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Encrypt365ContextMenus implements ContextMenuItemsProvider {
    private final MontoyaApi api;
    private final MainUI mainUI;
    public Encrypt365ContextMenus(MontoyaApi api, MainUI mainUI){
        this.api = api;
        this.mainUI = mainUI;
    }

    /**
     * provideMenuItems
     * @param event
     * @return java.util.List<java.awt.Component>
     * @Author: Kang on 2023/10/10 14:02
     * Burp 右键菜单快捷创建
     */
    @Override
    public List<Component> provideMenuItems(ContextMenuEvent event) {

        ArrayList<Component> menus = new ArrayList<>();
        JMenu Encrypt365 = new JMenu("Encrypt365");
        JMenuItem Base = new JMenuItem(EnumEntity.Base.name());
        JMenuItem Hash = new JMenuItem(EnumEntity.Hash.name());
        JMenuItem Aes = new JMenuItem(EnumEntity.Aes.name());
        JMenu ac = new JMenu(EnumEntity.非对称加解密.name());
        JMenuItem rsaEncrypt = new JMenuItem(EnumEntity.Rsa加解密.name());
        JMenuItem rsaCreate = new JMenuItem(EnumEntity.Rsa生成.name());

        menus.add(Encrypt365);
        Encrypt365.add(Base);
        Encrypt365.add(Hash);
        Encrypt365.add(Aes);
        Encrypt365.add(ac);
        ac.add(rsaEncrypt);
        ac.add(rsaCreate);

        Base.addActionListener(e ->  {
            String setText = setText(event);
            BaseUI ui = new BaseUI(api, mainUI.injector);
            ui.base_encode.setText(setText);
            mainUI.addTable(ui.UI, EnumEntity.Base$.name());
        });
        Hash.addActionListener(e ->  {
            String setText = setText(event);
            HashUI ui = new HashUI(api, mainUI.injector);
            ui.inputString_Text.setText(setText);
            mainUI.addTable(ui.UI, EnumEntity.Hash$.name());
        });
        Aes.addActionListener(e ->  {
            String setText = setText(event);
            AesUI ui = new AesUI(api, mainUI.injector);
            ui.Aes_encode.setText(setText);
            mainUI.addTable(ui.UI, EnumEntity.Aes$.name());
        });
        rsaEncrypt.addActionListener(e ->  {
            String setText = setText(event);
            RsaEncryptUI ui = new RsaEncryptUI(api, mainUI.injector);
            ui.textValue.setText(setText);
            mainUI.addTable(ui.UI, EnumEntity.Rsa加解密.name());
        });
        rsaCreate.addActionListener(e ->  {
            RsaCreateUi baseUI = new RsaCreateUi(api, mainUI.injector);
            mainUI.addTable(baseUI.UI, EnumEntity.Rsa生成.name());
        });

        return menus;
    }

    /**
     * setText
     * @param event
     * @return java.lang.String
     * @Author: Kang on 2023/10/10 14:03
     * 将数据传输到新建的Tab当中
     */
    private String setText(ContextMenuEvent event){
        HttpRequestResponse optional = event.messageEditorRequestResponse().get().requestResponse();
        Optional<Range> range = event.messageEditorRequestResponse().get().selectionOffsets();
        String str;
        if(event.messageEditorRequestResponse().get().selectionContext() == MessageEditorHttpRequestResponse.SelectionContext.REQUEST){
            if (event.messageEditorRequestResponse().isEmpty()){
                str = new String(optional.request().toByteArray().getBytes(), StandardCharsets.UTF_8);
                return str;
            }
            str = new String(optional.request().toByteArray().getBytes(), StandardCharsets.UTF_8);
        } else {
            if (event.messageEditorRequestResponse().isEmpty()){
                str = new String(optional.response().toByteArray().getBytes(), StandardCharsets.UTF_8);
                return str;
            }
            str = new String(optional.response().toByteArray().getBytes(), StandardCharsets.UTF_8);
        }
        str = str.substring(range.get().startIndexInclusive(),range.get().endIndexExclusive());
        return str;
    }
}
