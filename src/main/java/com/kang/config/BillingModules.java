/*
 * @Author: Kang
 * @Version: 1.0
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.kang.config;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.kang.entity.HistoryEntity;
import com.kang.service.*;
import com.kang.service.Impl.*;
import com.kang.view.historyUi.historyUI;

public class BillingModules extends AbstractModule {
    /**
     * configure
     * @param
     * @return void
     * @Author: Kang on 2023/10/10 14:04
     * Guice注入 配置信息
     */

    protected void configure(){
        bind(BaseEncrypt.class).to(BaseEncryptImpl.class).in(Scopes.SINGLETON);
        bind(Md5Encrypt.class).to(Md5EncryptImpl.class).in(Scopes.SINGLETON);
        bind(Md5EncryptApi.class).to(Md5EncryptApiImpl.class).in(Scopes.SINGLETON);
        //bind(AesEncrypt.class).to(AesEncryptImpl.class).in(Scopes.SINGLETON);
        bind(RsaCreate.class).to(RsaCreateImpl.class).in(Scopes.SINGLETON);
        bind(RsaEncrypt.class).to(RsaEncryptImpl.class).in(Scopes.SINGLETON);
        bind(History.class).to(HistoryImpl.class);
        bind(HistoryEntity.class).in(Scopes.SINGLETON);
        bind(historyUI.class).in(Scopes.SINGLETON);
    }
}
