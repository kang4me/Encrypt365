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
import com.kang.config.ConfigFile;
import com.kang.view.MainUI;

public class Encrypt365_Main implements BurpExtension {
    @Override
    public void initialize(MontoyaApi api)
    {
        ConfigFile configFile = new ConfigFile();
        configFile.iniFile();

        api.extension().setName("Encrypt365");
        api.logging().logToOutput("===========================");
        api.logging().logToOutput("[+]  load successful!      ");
        api.logging().logToOutput("[+]  code by Kang          ");
        api.logging().logToOutput("===========================");
        api.userInterface().registerSuiteTab("Encrypt365", new MainUI(api).root);
    }

}