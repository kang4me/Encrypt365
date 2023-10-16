/*
 * @Author: Kang
 * @Version: 1.0
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.kang.burpApi.PayloadGeneratorProvider;

import burp.api.montoya.MontoyaApi;
import burp.api.montoya.core.ByteArray;
import burp.api.montoya.intruder.PayloadData;
import burp.api.montoya.intruder.PayloadProcessingResult;
import burp.api.montoya.intruder.PayloadProcessor;
import burp.api.montoya.utilities.URLUtils;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.kang.entity.HashEntity;
import com.kang.entity.HistoryEntity;
import com.kang.entity.RsaEntity;
import com.kang.service.Md5Encrypt;
import com.kang.service.RsaEncrypt;

import static burp.api.montoya.intruder.PayloadProcessingResult.usePayload;

public class RsaPayloadProcessor implements PayloadProcessor {
    private final MontoyaApi api;
    private String name;
    private Object object;
    private URLUtils urlUtils;
    @Inject
    private HistoryEntity history;
    @Inject
    private RsaEncrypt rsaEncrypt;

    public RsaPayloadProcessor(MontoyaApi api, Injector injector, Object object)
    {
        this.api = api;
        this.object = object;
        this.urlUtils = api.utilities().urlUtils();
        history = injector.getInstance(HistoryEntity.class);
        rsaEncrypt = injector.getInstance(RsaEncrypt.class);
    }

    @Override
    public String displayName() {
        return "Encrypt365-"+object;
    }

    @Override
    public PayloadProcessingResult processPayload(PayloadData payloadData) {
        RsaEntity rsaEntity = history.getRsaEntity();
        rsaEntity.setTextValue(payloadData.currentPayload().toString());
        rsaEntity = rsaEncrypt.selecteEncodeMode(rsaEntity);

        return usePayload(ByteArray.byteArray(urlUtils.encode(rsaEntity.getOutputValue())));
    }
}
