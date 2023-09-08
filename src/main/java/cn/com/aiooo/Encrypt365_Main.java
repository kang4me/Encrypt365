/*
 * @Author: Kang
 * @Version: 1.0
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package cn.com.aiooo;

import burp.api.montoya.BurpExtension;
import burp.api.montoya.MontoyaApi;
import cn.com.aiooo.view.MainUI;

public class Encrypt365_Main implements BurpExtension {
    @Override
    public void initialize(MontoyaApi api)
    {
        api.extension().setName("Encrypt365");
        api.logging().logToOutput("Author: Kang");
        api.userInterface().registerSuiteTab("Encrypt365", new MainUI(api).root);
    }

}