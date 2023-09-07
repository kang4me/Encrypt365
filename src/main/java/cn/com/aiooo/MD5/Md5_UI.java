/*
 * @Author: Kang
 * @Version: 1.0
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package cn.com.aiooo.MD5;


import burp.api.montoya.MontoyaApi;
import burp.api.montoya.logging.Logging;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Md5_UI {
    public JPanel UI;
    private JComboBox<String> Link_URL;
    private JTextField textField1;
    private JTextField textField2;
    private JButton Save_Button;
    private JButton Reset_Button;
    private JTextArea EncodeMd5;
    private JTextArea DecodeMd5;
    private JTextArea Log_Text;
    private JButton Decode_butten;
    private JButton Encode_butten;
    private JComboBox<String> Hash_ComboBox;
    private JScrollPane JS1;
    private JScrollPane JS2;
    private JRadioButton radioButton1;
    private JRadioButton radioButton2;
    private MontoyaApi api;

    public Md5_UI(MontoyaApi api) {
        this.api = api;
        Logging logging = this.api.logging();

        ButtonGroup bg = new ButtonGroup();
        bg.add(radioButton1);
        bg.add(radioButton2);
        radioButton1.setSelected(true);


        String[] str = {"MD5", "SHA-1", "SHA-224", "SHA-256", "SHA-384", "SHA-512", "SHA-1024"};
        for (String s : str) {
            Hash_ComboBox.addItem(s);
        }
        String[] link = {"CMD5", "MD5免费在线"};
        String[] url = {"https://www.cmd5.com/", "https://www.somd5.com/"};
        for (int i = 0; i < link.length; i++) {
            Link_URL.addItem(link[i] + " (" + url[i] + ")");
        }
        this.Encode_butten.addActionListener((e) -> {
            int s = Hash_ComboBox.getSelectedIndex();
            String result = "";
            String inputString = this.EncodeMd5.getText();
            switch (s) {
                case 0:
                    //MD5
                    result = MD5_Encode(inputString);
                    break;
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 6:
                    break;
            }
            this.DecodeMd5.setText(result);
            this.DecodeMd5.setLineWrap(true);
            this.DecodeMd5.setWrapStyleWord(true);
        });
        this.Decode_butten.addActionListener((e) -> {
            int s = Link_URL.getSelectedIndex();
            String result = "";
            String inputString = this.EncodeMd5.getText();
            String email = this.textField1.getText();
            String key = this.textField2.getText();
            switch (s) {
                case 0:
                    if (this.radioButton1.isSelected()) {
                        result = CMD5_url(inputString, url[0]);
                    } else if (this.radioButton2.isSelected()) {
                        result = CMD5_url(inputString, email, key, url[0]);
                    }
                    break;
                case 1:
                    break;
                case 2:
                    break;
            }

            this.DecodeMd5.setText(result);
            this.DecodeMd5.setLineWrap(true);
            this.DecodeMd5.setWrapStyleWord(true);
        });
    }

    private static String MD5_Encode(String inputString) {
        String signature = "";
        try {
            //创建一个提供信息摘要算法的对象，初始化为md5算法对象
            MessageDigest md = MessageDigest.getInstance("MD5");
            //计算后获得字节数组
            byte[] bytes = md.digest(inputString.getBytes("utf-8"));

            StringBuffer md5str = new StringBuffer();
            //把数组每一字节换成16进制连成md5字符串
            int digital;
            for (int i = 0; i < bytes.length; i++) {
                digital = bytes[i];
                if (digital < 0) {
                    digital += 256;
                }
                if (digital < 16) {
                    md5str.append("0");
                }
                md5str.append(Integer.toHexString(digital));
                //把数组每一字节换成16进制连成md5字符串
                signature = md5str.toString();
            }
        } catch (Exception e) {

        }
        return signature;
    }

    private String CMD5_url(String inputString, String email, String key, String link_url) {
        link_url = link_url + "api.ashx?email=" + email + "&key=" + key + "&hash=" + inputString;
        String result = "查询失败，查看日志\n\r";
        try {
            URL url = new URL(link_url);
            HttpURLConnection connectionGet = (HttpURLConnection) url.openConnection();

            connectionGet.setRequestMethod("GET");
            int responseCode = connectionGet.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader inGet = new BufferedReader(new InputStreamReader(connectionGet.getInputStream()));
                String inputLine;
                StringBuffer responseGet = new StringBuffer();
                while ((inputLine = inGet.readLine()) != null) {
                    responseGet.append(inputLine);
                }
                inGet.close();
                switch (responseGet.toString().trim()) {
                    case "CMD5-ERROR:0":
                        System.out.println("解密失败");
                        this.Log_Text.append("解密失败\n\r");
                        break;
                    case "CMD5-ERROR:-1":
                        System.out.println("无效的用户名密码");
                        this.Log_Text.append("无效的用户名密码\n\r");
                        break;
                    case "CMD5-ERROR:-2":
                        System.out.println("余额不足");
                        this.Log_Text.append("余额不足\n\r");
                        break;
                    case "CMD5-ERROR:-3":
                        System.out.println("解密服务器故障");
                        this.Log_Text.append("解密服务器故障\n\r");
                        break;
                    case "CMD5-ERROR:-4":
                        System.out.println("不识别的密文");
                        this.Log_Text.append("不识别的密文\n\r");
                        break;
                    case "CMD5-ERROR:-7":
                        System.out.println("不支持的类型");
                        this.Log_Text.append("不支持的类型\n\r");
                        break;
                    case "CMD5-ERROR:-8":
                        System.out.println("api权限被禁止");
                        this.Log_Text.append("api权限被禁止\n\r");
                        break;
                    case "CMD5-ERROR:-9":
                        System.out.println("条数超过200条");
                        this.Log_Text.append("条数超过200条\n\r");
                        break;
                    case "CMD5-ERROR:-9999":
                        System.out.println("其它错误");
                        this.Log_Text.append("其它错误\n\r");
                        break;
                    default:
                        this.Log_Text.append("查询成功\n\r");
                        return responseGet.toString();
                }
            } else {
                this.Log_Text.append("服务器未响应\n\r");
                System.out.println("服务器未响应");
            }
        } catch (Exception e) {
            this.Log_Text.append("服务器异常\n\r");
            throw new RuntimeException(e);
        }
        return result;
    }

    private String CMD5_url(String inputString, String link_url) {
        String result = "查询失败，查看日志\n\r";
        try {
            // 设置请求的URL
            URL url = new URL(link_url);
            HttpURLConnection connectionPost = (HttpURLConnection) url.openConnection();
            connectionPost.setRequestMethod("POST");
            HttpConnection(connectionPost);
            connectionPost.setDoOutput(true);
            // 构建请求参数

            // 将参数写入请求体
            OutputStream os = connectionPost.getOutputStream();
            os.write(requestGet(inputString, link_url).getBytes());
            os.flush();
            os.close();
            Thread.sleep(500);
            // 发送请求并获取响应
            int responseCode = connectionPost.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // 读取响应内容
                BufferedReader inPost = new BufferedReader(new InputStreamReader(connectionPost.getInputStream()));
                String inputLine;
                StringBuffer responsePost = new StringBuffer();
                while ((inputLine = inPost.readLine()) != null) {
                    responsePost.append(inputLine);
                }
                inPost.close();
                String regex = "<span\\s*id=\"LabelAnswer\"[^>]*>(.*?)</span>";
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(responsePost.toString());
                // 提取匹配的字符串
                if (matcher.find()) {
                    String str = new String(matcher.group(1).getBytes(), StandardCharsets.UTF_8);
                    if (str.contains("未查到") || str.contains("验证码错误") || str.contains("登录")) {
                        this.Log_Text.append("cmd5平台：" + new String(matcher.group(1).getBytes(), StandardCharsets.UTF_8) + "\n\r");
                    } else {
                        this.Log_Text.append("查询成功\n\r");
                        result = str;
                    }
                } else {
                    this.Log_Text.append("未知错误\n\r");
                }
                this.Log_Text.setLineWrap(true);
                this.Log_Text.setWrapStyleWord(true);
            } else {
                this.Log_Text.append("服务器未响应\n\r");
            }
        } catch (Exception e) {
            this.Log_Text.append("服务器错误\n\r");
        }
        return result;
    }

    private static String requestGet(String inputString, String link_url) throws Exception {
        URL url = new URL(link_url);
        // 创建连接并设置请求属性
        HttpURLConnection connectionGet = (HttpURLConnection) url.openConnection();

        connectionGet.setRequestMethod("GET");
        HttpConnection(connectionGet);

        int responseCode = connectionGet.getResponseCode();

        // 读取响应内容
        BufferedReader inGet = new BufferedReader(new InputStreamReader(connectionGet.getInputStream()));
        String inputLine;
        StringBuffer responseGet = new StringBuffer();
        while ((inputLine = inGet.readLine()) != null) {
            responseGet.append(inputLine);
        }
        inGet.close();

        String __EVENTTARGET = extractValue(responseGet.toString(), "__EVENTTARGET");
        String __EVENTARGUMENT = extractValue(responseGet.toString(), "__EVENTARGUMENT");
        String __VIEWSTATE = extractValue(responseGet.toString(), "__VIEWSTATE");
        String __VIEWSTATEGENERATOR = extractValue(responseGet.toString(), "__VIEWSTATEGENERATOR");
        String $HiddenField1 = extractValue(responseGet.toString(), "ctl00\\$ContentPlaceHolder1\\$HiddenField1");
        String $HiddenField2 = extractValue(responseGet.toString(), "ctl00\\$ContentPlaceHolder1\\$HiddenField2");
        __VIEWSTATE = URLEncoder.encode(__VIEWSTATE, "UTF-8");
        $HiddenField2 = URLEncoder.encode($HiddenField2, "UTF-8");

        return "__EVENTTARGET=" + __EVENTTARGET + "&__EVENTARGUMENT=" + __EVENTARGUMENT + "&__VIEWSTATE=" + __VIEWSTATE + "&__VIEWSTATEGENERATOR=" + __VIEWSTATEGENERATOR + "&ctl00%24ContentPlaceHolder1%24TextBoxInput=" + inputString + "&ctl00%24ContentPlaceHolder1%24InputHashType=md5&ctl00%24ContentPlaceHolder1%24Button1=%E6%9F%A5%E8%AF%A2&ctl00%24ContentPlaceHolder1%24HiddenField1=" + $HiddenField1 + "&ctl00%24ContentPlaceHolder1%24HiddenField2=" + $HiddenField2;
    }

    private static void HttpConnection(HttpURLConnection connection) {
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        connection.setRequestProperty("Referer", "https://www.cmd5.com/");
        connection.setRequestProperty("Origin", "https://www.cmd5.com");
        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:109.0) Gecko/20100101 Firefox/112.0");
        connection.setRequestProperty("Sec-Fetch-Dest", "document");
        connection.setRequestProperty("Sec-Fetch-Mode", "navigate");
        connection.setRequestProperty("Sec-Fetch-Site", "same-origin");
        connection.setRequestProperty("Sec-Fetch-User", "?1");
    }

    private static String extractValue(String html, String name) {
        String regex = "<input type=\"hidden\" name=\"" + name + "\" id=\"[^\"]*\" value=\"([^\"]*)\"\\s*/?>";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(html);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return "";
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        UI = new JPanel();
        UI.setLayout(new GridBagLayout());
        UI.setAlignmentX(0.0f);
        UI.setAlignmentY(0.0f);
        UI.setDoubleBuffered(true);
        UI.setFocusable(true);
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(3, 3, new Insets(0, 0, 0, 0), -1, -1));
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 5, 5, 5);
        UI.add(panel1, gbc);
        JS1 = new JScrollPane();
        panel1.add(JS1, new GridConstraints(0, 0, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        EncodeMd5 = new JTextArea();
        JS1.setViewportView(EncodeMd5);
        JS2 = new JScrollPane();
        panel1.add(JS2, new GridConstraints(2, 0, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        DecodeMd5 = new JTextArea();
        JS2.setViewportView(DecodeMd5);
        Hash_ComboBox = new JComboBox();
        panel1.add(Hash_ComboBox, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        Encode_butten = new JButton();
        Encode_butten.setText("加密");
        panel1.add(Encode_butten, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(9, 3, new Insets(0, 0, 0, 0), -1, -1));
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 5, 5, 5);
        UI.add(panel2, gbc);
        Link_URL = new JComboBox();
        panel2.add(Link_URL, new GridConstraints(0, 1, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        textField1 = new JTextField();
        panel2.add(textField1, new GridConstraints(1, 1, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        textField2 = new JTextField();
        panel2.add(textField2, new GridConstraints(2, 1, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        Save_Button = new JButton();
        Save_Button.setText("保存");
        panel2.add(Save_Button, new GridConstraints(4, 0, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        Reset_Button = new JButton();
        Reset_Button.setText("重置");
        panel2.add(Reset_Button, new GridConstraints(5, 0, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JScrollPane scrollPane1 = new JScrollPane();
        panel2.add(scrollPane1, new GridConstraints(8, 0, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        Log_Text = new JTextArea();
        scrollPane1.setViewportView(Log_Text);
        final JLabel label1 = new JLabel();
        label1.setText("email：");
        panel2.add(label1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("key：");
        panel2.add(label2, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("日志：");
        panel2.add(label3, new GridConstraints(7, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label4 = new JLabel();
        label4.setText("接口：");
        panel2.add(label4, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        Decode_butten = new JButton();
        Decode_butten.setText("解密");
        panel2.add(Decode_butten, new GridConstraints(6, 0, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        radioButton1 = new JRadioButton();
        radioButton1.setText("免费接口(有限制)");
        panel2.add(radioButton1, new GridConstraints(3, 0, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        radioButton2 = new JRadioButton();
        radioButton2.setText("付费接口");
        panel2.add(radioButton2, new GridConstraints(3, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label5 = new JLabel();
        label5.setText("说明：");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);
        UI.add(label5, gbc);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return UI;
    }

}
