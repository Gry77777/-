package test;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Myframe2 extends JFrame implements MouseListener {
    JButton jbt1 = new JButton("点我");

    public Myframe2() {
        this.setSize(603, 680);
        this.setTitle("拼图游戏");
        this.setAlwaysOnTop(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLayout(null);
        jbt1.setBounds(0, 0, 100, 50);
        jbt1.addMouseListener(this);
        this.getContentPane().add(jbt1);
        this.setVisible(true);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("鼠标点击了");
    }

    @Override
    public void mousePressed(MouseEvent e) {
        System.out.println("鼠标按下了");
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        System.out.println("鼠标释放了");
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        System.out.println("鼠标进入");
    }

    @Override
    public void mouseExited(MouseEvent e) {
        System.out.println("鼠标离开");
    }
}
