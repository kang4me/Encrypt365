/*
 * @Author: Kang
 * @Version: 1.0
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.kang.entity;

import java.util.ArrayList;
import java.util.List;

public class HistoryEntity {
    public int id = 1;
    public List<Object[]> data = new ArrayList<>();
    private AesEntity aesEntity;
    private BaseEntity baseEntity;
    private Md5Entity md5Entity;
    private RsaEntity rsaEntity;

    public AesEntity getAesEntity() {
        return aesEntity;
    }

    public void setAesEntity(AesEntity aesEntity) {
        this.aesEntity = aesEntity;
    }

    public BaseEntity getBaseEntity() {
        return baseEntity;
    }

    public void setBaseEntity(BaseEntity baseEntity) {
        this.baseEntity = baseEntity;
    }

    public Md5Entity getMd5Entity() {
        return md5Entity;
    }

    public void setMd5Entity(Md5Entity md5Entity) {
        this.md5Entity = md5Entity;
    }

    public RsaEntity getRsaEntity() {
        return rsaEntity;
    }

    public void setRsaEntity(RsaEntity rsaEntity) {
        this.rsaEntity = rsaEntity;
    }
}
