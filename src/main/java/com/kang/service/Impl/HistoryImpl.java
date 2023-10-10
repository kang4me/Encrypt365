/*
 * @Author: Kang
 * @Version: 1.0
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.kang.service.Impl;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.kang.entity.EnumEntity;
import com.kang.entity.HistoryEntity;
import com.kang.service.History;
import com.kang.util.DateUtils;
import com.kang.view.historyUi.historyUI;

public class HistoryImpl implements History {
    @Inject
    private historyUI history;
    @Inject
    private HistoryEntity historyEntity;

    /**
     * baseHistory
     * @param injector
     * @return void
     * @Author: Kang on 2023/10/10 14:21
     * 使用注入将 所有加解密功能添加到历史记录当中
     */
    public void baseHistory(Injector injector){
        history = injector.getInstance(historyUI.class);
        historyEntity = injector.getInstance(HistoryEntity.class);
        history.all.insertRow(0,new Object[]{
                historyEntity.id,
                EnumEntity.Base,
                historyEntity.getBaseEntity().getInput(),
                historyEntity.getBaseEntity().getOutput(),
                "",
                DateUtils.date()});
        historyEntity.data.add(new Object[]{
                historyEntity.id,
                EnumEntity.Base,
                historyEntity.getBaseEntity().getInput(),
                historyEntity.getBaseEntity().getOutput(),
                "",
                DateUtils.date()});
        historyEntity.id++;
    }
    
    /**
     * hashHistory
     * @param injector
     * @return void
     * @Author: Kang on 2023/10/10 14:56
     */
    public void hashHistory(Injector injector){
        history = injector.getInstance(historyUI.class);
        historyEntity = injector.getInstance(HistoryEntity.class);
        history.all.insertRow(0,new Object[]{
                historyEntity.id,
                EnumEntity.Hash,
                historyEntity.getMd5Entity().getInputString(),
                historyEntity.getMd5Entity().getOutputString(),
                "",
                DateUtils.date()});
        historyEntity.data.add(new Object[]{
                historyEntity.id,
                EnumEntity.Hash,
                historyEntity.getMd5Entity().getInputString(),
                historyEntity.getMd5Entity().getOutputString(),
                "",
                DateUtils.date()});
        historyEntity.id++;
    }
    
    /**
     * aesHistory
     * @param injector
     * @return void
     * @Author: Kang on 2023/10/10 14:57
     */
    public void aesHistory(Injector injector){
        history = injector.getInstance(historyUI.class);
        historyEntity = injector.getInstance(HistoryEntity.class);
        history.all.insertRow(0,new Object[]{
                historyEntity.id,
                EnumEntity.Aes,
                historyEntity.getAesEntity().getInputValue(),
                historyEntity.getAesEntity().getOutputValue(),
                historyEntity.getAesEntity().getKey(),
                DateUtils.date()});
        historyEntity.data.add(new Object[]{
                historyEntity.id,
                EnumEntity.Aes,
                historyEntity.getAesEntity().getInputValue(),
                historyEntity.getAesEntity().getOutputValue(),
                historyEntity.getAesEntity().getKey(),
                DateUtils.date()});
        historyEntity.id++;
    }
    
    /**
     * rsaCreateHistory
     * @param injector
     * @return void
     * @Author: Kang on 2023/10/10 14:57
     */
    public void rsaCreateHistory(Injector injector){
        history = injector.getInstance(historyUI.class);
        historyEntity = injector.getInstance(HistoryEntity.class);
        history.all.insertRow(0,new Object[]{
                historyEntity.id,
                EnumEntity.Rsa生成,
                historyEntity.getRsaEntity().getPublicKey(),
                historyEntity.getRsaEntity().getPrivateKey(),
                "",
                DateUtils.date()});
        historyEntity.data.add(new Object[]{
                historyEntity.id,
                EnumEntity.Rsa生成,
                historyEntity.getRsaEntity().getPublicKey(),
                historyEntity.getRsaEntity().getPrivateKey(),
                "",
                DateUtils.date()});
        historyEntity.id++;
    }
    
    /**
     * rsaEncryptHistory
     * @param injector
     * @return void
     * @Author: Kang on 2023/10/10 14:57
     */
    public void rsaEncryptHistory(Injector injector){
        history = injector.getInstance(historyUI.class);
        historyEntity = injector.getInstance(HistoryEntity.class);
        history.all.insertRow(0,new Object[]{
                historyEntity.id,
                EnumEntity.Rsa加解密,
                historyEntity.getRsaEntity().getTextValue(),
                historyEntity.getRsaEntity().getOutputValue(),
                historyEntity.getRsaEntity().getPublicKey(),
                DateUtils.date()});
        historyEntity.data.add(new Object[]{
                historyEntity.id,
                EnumEntity.Rsa加解密,
                historyEntity.getRsaEntity().getTextValue(),
                historyEntity.getRsaEntity().getOutputValue(),
                historyEntity.getRsaEntity().getPublicKey(),
                DateUtils.date()});
        historyEntity.id++;
    }
}
