package com.ityang.ui;

import javax.swing.*;

public class text {
    //创建游戏的主界面
    public static void main(String[] args) {
        JFrame gameJframe = new JFrame("拼图游戏");
        gameJframe.setSize(603, 680);
        gameJframe.setVisible(true);
        //创捷登陆界面
        JFrame loginJframe = new JFrame("登陆界面");
        loginJframe.setSize(480, 430);
        loginJframe.setVisible(true);
        //创建注册界面
        JFrame registerJframe = new JFrame("注册界面");
        registerJframe.setSize(488, 500);
        registerJframe.setVisible(true);
    }
}
