/*
 * @Author: Kang
 * @Version: 1.0
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.kang.service;

import com.google.inject.Injector;

public interface History {
    public void baseHistory(Injector injector);
    public void hashHistory(Injector injector);
    public void aesHistory(Injector injector);
    public void rsaCreateHistory(Injector injector);
    public void rsaEncryptHistory(Injector injector);
}
