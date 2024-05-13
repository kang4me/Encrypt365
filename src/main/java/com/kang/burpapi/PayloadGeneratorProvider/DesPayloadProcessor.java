/*
 * @Author: Kang
 * @Version: 1.0
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.kang.burpapi.PayloadGeneratorProvider;

import burp.api.montoya.MontoyaApi;
import burp.api.montoya.core.ByteArray;
import burp.api.montoya.intruder.PayloadData;
import burp.api.montoya.intruder.PayloadProcessingResult;
import burp.api.montoya.intruder.PayloadProcessor;
import burp.api.montoya.utilities.URLUtils;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.kang.entity.DesEntity;
import com.kang.entity.HistoryEntity;
import com.kang.service.Impl.SymmetricalEncryptImpl;
import com.kang.service.SymmetricalEncrypt;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

import static burp.api.montoya.intruder.PayloadProcessingResult.usePayload;

public class DesPayloadProcessor implements PayloadProcessor {
    private final MontoyaApi api;
    private String name;
    private Object object;
    private URLUtils urlUtils;
    @Inject
    private HistoryEntity history;

    public DesPayloadProcessor(MontoyaApi api, Injector injector, Object object)
    {
        this.api = api;
        this.object = object;
        this.urlUtils = api.utilities().urlUtils();
        history = injector.getInstance(HistoryEntity.class);
    }

    @Override
    public String displayName() {
        return "Encrypt365-"+object;
    }

    @Override
    public PayloadProcessingResult processPayload(PayloadData payloadData) {
        DesEntity desEntity = history.getDesEntity();
        desEntity.setInputValue(payloadData.currentPayload().toString());
        SymmetricalEncrypt desEncrypt = new SymmetricalEncryptImpl(desEntity);
        byte[] bt = desEncrypt.DES_Encrypt_all();
        //输出方式
        if (desEntity.getOutputEncode().equals("0")) {
            return usePayload(ByteArray.byteArray(urlUtils.encode(new String(Base64.encodeBase64(bt)))));
        } else
            return usePayload(ByteArray.byteArray(urlUtils.encode(new String(Hex.encodeHex(bt)))));
    }
}
