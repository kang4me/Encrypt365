/*
 * @Author: Kang
 * @Version: 1.0
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.kang.test;



import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class Test{
    public static void main(String[] args) {
        String input = "xcvnpiaosndflhojoa//++fdsmf./'";
        StringBuilder encodedString = new StringBuilder();

        for (char c : input.toCharArray()) {
            encodedString.append('%');
            encodedString.append(String.format("%02X", (int) c));
        }

        System.out.println("Encoded string: " + encodedString.toString());
    }

}
