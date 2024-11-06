package test;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Myframe3 extends JFrame implements KeyListener {

    public Myframe3() {
        this.setSize(603, 680);
        this.setTitle("拼图游戏");
        this.setAlwaysOnTop(true);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.setDefaultCloseOperation(3);
        this.addKeyListener(this);
        this.setVisible(true);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println("按下了");
    }

    @Override
    public void keyReleased(KeyEvent e) {
        System.out.println("松开了");
        int code = e.getKeyCode();
        if (code == 65) {
            System.out.println("按下了A");
        } else if (code == 66) {
            System.out.println("按下了B");
        }


    }
}
