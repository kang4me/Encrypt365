/*
 * @Author: Kang
 * @Version: 1.0
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.kang;

import burp.api.montoya.BurpExtension;
import burp.api.montoya.MontoyaApi;
import com.google.inject.Guice;
import com.kang.burpApi.contextMenuItemsProvider.Encrypt365ContextMenus;
import burp.api.montoya.ui.editor.extension.ExtensionProvidedHttpRequestEditor;
import com.kang.config.BillingModules;
import com.kang.view.MainUI;

public class Encrypt365_Main implements BurpExtension {
    ExtensionProvidedHttpRequestEditor myExtensionHttpRequestEditor;
    @Override
    public void initialize(MontoyaApi api)
    {
        api.extension().setName("Encrypt365");
        api.logging().logToOutput("===========================");
        api.logging().logToOutput("[+]  load successful!      ");
        api.logging().logToOutput("[+]  code by Kang          ");
        api.logging().logToOutput("===========================");
        MainUI mainUI = new MainUI( api, Guice.createInjector(new BillingModules()));
        api.userInterface().registerSuiteTab("Encrypt365", mainUI.UI);
        //Registration registerPayloadProcessor(PayloadProcessor payloadProcessor)注册一个 PayloadProcessor
        //api.intruder().registerPayloadGeneratorProvider(new MyPayloadGeneratorProvider());
        api.userInterface().registerContextMenuItemsProvider(new Encrypt365ContextMenus( api, mainUI));
    }
}
