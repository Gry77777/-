package test;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class Myframe extends JFrame implements ActionListener {
    JButton jbt1 = new JButton("点我");
    JButton jbt2 = new JButton("再点我");


    public Myframe() {
        this.setSize(603, 680);
        this.setTitle("拼图游戏");
        this.setAlwaysOnTop(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLayout(null);


        jbt1.setBounds(100, 100, 100, 50);
        jbt1.addActionListener(this);

        jbt2.setBounds(200, 100, 100, 50);
        jbt2.addActionListener(this);
        this.setVisible(true);
        this.getContentPane().add(jbt1);
        this.getContentPane().add(jbt2);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object sorce = e.getSource();
        if (sorce == jbt1) {
            jbt1.setSize(200, 200);
        } else if (sorce == jbt2) {
            Random random = new Random();
            jbt2.setLocation(random.nextInt(500), random.nextInt(500));
        }
    }
}
