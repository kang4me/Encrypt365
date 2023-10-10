/*
 * @Author: Kang
 * @Version: 1.0
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.kang.util;

import java.text.SimpleDateFormat;

public class DateUtils {

    /**
     * date
     * @param
     * @return java.lang.String
     * @Author: Kang on 2023/10/10 14:36
     * 时间方法
     */
    public static String date(){
        java.util.Date date = new java.util.Date();
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        System.out.println(dateFormat.format(date));
        return dateFormat.format(date);
    }
}
