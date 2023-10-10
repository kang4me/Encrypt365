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

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;

public class Utils {
    
    /**
     * loadImage
     * @param filename
     * @param width
     * @param height
     * @return javax.swing.ImageIcon
     * @Author: Kang on 2023/10/10 14:36
     * 获取图片，设置图片大小
     */
    public static ImageIcon loadImage(String filename, int width, int height){
        ClassLoader cldr = Utils.class.getClassLoader();
        URL imageURLMain = cldr.getResource(filename);

        if(imageURLMain != null) {
            Image scaled = new ImageIcon(imageURLMain).getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(scaled);
            BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = (Graphics2D) bufferedImage.getGraphics();
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.drawImage(scaledIcon.getImage(), null, null);
            return new ImageIcon(bufferedImage);
        }
        return null;
    }
}
