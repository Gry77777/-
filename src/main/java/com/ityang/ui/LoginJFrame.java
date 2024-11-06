package com.ityang.ui;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
//导入连接数据库相关的包
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginJFrame extends JFrame implements ActionListener, MouseListener {

    static ArrayList<User> list = new ArrayList<>();
    private JButton login;
    private JButton register;
    private JTextField username;
    private JTextField password;
    private JTextField code;
    private JLabel rightCode;


    static {
        list.add(new User("zhangsan", "123"));
        list.add(new User("lisi", "1234"));
    }

    public LoginJFrame() {
        //初始化界面
        initJFrame();
        //在这个界面中添加内容
        initView();
        //让当前界面显示出来
        this.setVisible(true);
    }

    public void initView() {
        //1. 添加用户名文字
        JLabel usernameText = new JLabel(new ImageIcon("..\\puzzlegame\\image\\login\\用户名.png"));
        usernameText.setBounds(116, 135, 47, 17);
        this.getContentPane().add(usernameText);
        //2.添加用户名输入框
        username = new JTextField();
        username.setBounds(195, 134, 200, 30);
        this.getContentPane().add(username);
        //3.添加密码文字
        JLabel passwordText = new JLabel(new ImageIcon("..\\puzzlegame\\image\\login\\密码.png"));
        passwordText.setBounds(130, 195, 32, 16);
        this.getContentPane().add(passwordText);
        //4.密码输入框
        password = new JTextField();
        password.setBounds(195, 195, 200, 30);
        this.getContentPane().add(password);
        //验证码提示
        JLabel codeText = new JLabel(new ImageIcon("..\\puzzlegame\\image\\login\\验证码.png"));
        codeText.setBounds(133, 256, 50, 30);
        this.getContentPane().add(codeText);
        //验证码的输入框
        code = new JTextField();
        code.setBounds(195, 256, 100, 30);
        this.getContentPane().add(code);
        String codeStr = CodeUtil.getCode();
        rightCode = new JLabel();
        //设置内容
        rightCode.setText(codeStr);
        //位置和宽高
        rightCode.setBounds(300, 256, 50, 30);
        //添加到界面
        this.getContentPane().add(rightCode);
        rightCode.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                refreshCaptcha();
            }
        });
        System.out.println(rightCode.getText());

        //5.添加登录按钮
        login = new JButton();
        login.setBounds(123, 310, 128, 47);
        login.setIcon(new ImageIcon("..\\puzzlegame\\image\\login\\登录按钮.png"));
        //去除按钮的默认边框
        login.setBorderPainted(false);
        //去除按钮的默认背景
        login.setContentAreaFilled(false);
        this.getContentPane().add(login);
        login.addActionListener(this);
        login.addMouseListener(this);
        //6.添加注册按钮
        register = new JButton();
        register.addActionListener(this);
        this.getContentPane().add(register);
        register.setBounds(256, 310, 128, 47);
        register.setIcon(new ImageIcon("..\\puzzlegame\\image\\login\\注册按钮.png"));
        //去除按钮的默认边框
        register.setBorderPainted(false);
        //去除按钮的默认背景
        register.setContentAreaFilled(false);
        register.addMouseListener(this);
        this.getContentPane().add(register);

        //7.添加背景图片
        JLabel background = new JLabel(new ImageIcon("..\\puzzlegame\\image\\login\\background.png"));
        background.setBounds(0, 0, 470, 390);
        this.getContentPane().add(background);
    }


    public void initJFrame() {
        this.setSize(488, 430);//设置宽高
        this.setTitle("拼图游戏 V1.0登录");//设置标题
        this.setDefaultCloseOperation(3);//设置关闭模式
        this.setLocationRelativeTo(null);//居中
        this.setAlwaysOnTop(true);//置顶
        this.setLayout(null);//取消内部默认布局
    }


    //要展示用户名或密码错误
    public void showJDialog(String content) {
        // 创建一个弹框对象
        JDialog jDialog = new JDialog();
        // 给弹框设置大小
        jDialog.setSize(300, 150);
        // 让弹框置顶
        jDialog.setAlwaysOnTop(true);
        // 让弹框居中
        jDialog.setLocationRelativeTo(null);
        // 弹框不关闭永远无法操作下面的界面
        jDialog.setModal(true);
        // 创建JLabel对象管理文字并添加到弹框当中
        JLabel warning = new JLabel();
        warning.setText(content); // 设置显示的文字
        warning.setBounds(0, 0, 200, 150);
        jDialog.getContentPane().add(warning);
        // 让弹框展示出来
        jDialog.setVisible(true);
    }


    //重新生成新的验证码
    private void refreshCaptcha() {
        String newCodeStr = CodeUtil.getCode();
        rightCode.setText(newCodeStr);
        System.out.println(rightCode.getText());
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == login) {
            // 获取输入框中的内容
            String enteredUsername = username.getText();
            String enteredPassword = password.getText();
            String enteredCode = code.getText();
            System.out.println(rightCode.getText());

            // 检查输入框内容是否为空
            if (enteredUsername.isEmpty() || enteredPassword.isEmpty() || enteredCode.isEmpty()) {
                showJDialog("请输入完整的用户名、密码和验证码");
                return;
            }

            // 假设验证码是从某个地方生成的
            String correctCode = rightCode.getText(); // 这里是示例，你可以替换为实际的验证码生成逻辑

            // 检查验证码是否正确
            if (!enteredCode.equals(correctCode)) {
                System.out.println("验证码错误");
                showJDialog("验证码错误");
                return;
            }

            Connection connection = null;
            PreparedStatement preparedStatement = null;
            ResultSet resultSet = null;

            try {
                // 使用 DatabaseConnector 获取连接
                connection = DatabaseConnector.getConnection();
                if (connection == null) {
                    showJDialog("无法连接到数据库");
                    return;
                }

                // 查询用户信息，获取用户名对应的 id 和密码
                String sql = "SELECT id, password FROM user WHERE username = ?";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, enteredUsername);
                resultSet = preparedStatement.executeQuery();

                // 检查是否有结果
                if (resultSet.next()) {
                    String dbPassword = resultSet.getString("password");
                    int userId = resultSet.getInt("id"); // 获取 userId

                    // 检查密码是否匹配
                    if (enteredPassword.equals(dbPassword)) {
                        System.out.println("登录成功");

                        // 将 userId 传递给 GameJFrame 构造函数
                        GameJFrame gameJFrame = new GameJFrame();
                        gameJFrame.setUserId(userId);
                        gameJFrame.setVisible(true);
//                        GameJFrame game1JFrame = new GameJFrame();
                        // 传递 userId
                        this.setVisible(false); // 隐藏当前登录界面
                    } else {
                        System.out.println("登录失败");
                        showJDialog("用户名或密码错误");
                    }
                } else {
                    System.out.println("登录失败");
                    showJDialog("用户名不存在");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                showJDialog("数据库错误：" + ex.getMessage());
            } finally {
                // 关闭资源
                try {
                    if (resultSet != null) resultSet.close();
                    if (preparedStatement != null) preparedStatement.close();
                    if (connection != null) connection.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        } else if (e.getSource() == register) {
            new RegisterJFrame();
            this.setVisible(false);
            System.out.println("点击了注册");
        }
    }



//    public void actionPerformed(ActionEvent e) {
//        if (e.getSource() == login) {
//            // 获取输入框中的内容
//            String enteredUsername = username.getText();
//            System.out.println("输入的用户名：" + enteredUsername);
//            String enteredPassword = password.getText();
//            String enteredCode = code.getText();
//            String actualCode = rightCode.getText();
//
//            // 检查输入框内容是否为空
//            if (enteredUsername.isEmpty() || enteredPassword.isEmpty() || enteredCode.isEmpty() || actualCode.isEmpty()) {
//                System.out.println("请输入完整的用户名、密码和验证码");
//                showJDialog("请输入完整的用户名、密码和验证码");
//                return; // 退出方法，避免继续执行下面的代码
//            }
//
//            // 检查用户名、密码和验证码是否正确
//            if (enteredUsername.equals("ityang") && enteredPassword.equals("123456") && enteredCode.equals(actualCode)) {
//                System.out.println("登录成功");
//                // 创建一个新窗口
//                GameJFrame gameJFrame = new GameJFrame();
//                // 隐藏当前窗口
//                this.setVisible(false);
//            } else {
//                System.out.println("登录失败");
//                showJDialog("用户名或密码错误");
//            }
//        }
//    }

    @Override
    public void mouseClicked(MouseEvent e) {


    }

    @Override
    public void mousePressed(MouseEvent e) {
//        if (e.getSource() instanceof JButton) {
//            JButton clickedButton = (JButton) e.getSource();
//            if (clickedButton.getIcon() != null && clickedButton.getIcon().toString().equals("..\\puzzlegame\\image\\login\\登录按钮.png")) {
//                Icon newIcon = new ImageIcon("..\\puzzlegame\\image\\login\\登录按下.png");
//                clickedButton.setIcon(newIcon);
//                System.out.println("按下");
//            }
//        }
        if (e.getSource() == login) {
            login.setIcon(new ImageIcon("..\\puzzlegame\\image\\login\\登录按下.png"));
        } else if (e.getSource() == register) {
            register.setIcon(new ImageIcon("..\\puzzlegame\\image\\login\\注册按下.png"));
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
//        if (e.getSource() instanceof JButton) {
//            JButton clickedButton = (JButton) e.getSource();
//            if (clickedButton.getIcon() != null && clickedButton.getIcon().toString().equals("..\\puzzlegame\\image\\login\\登录按下.png")) {
//                Icon newIcon = new ImageIcon("..\\puzzlegame\\image\\login\\登录按钮.png");
//                clickedButton.setIcon(newIcon);
//                System.out.println("松开");
//            }
//        }
        if (e.getSource() == login) {
            login.setIcon(new ImageIcon("..\\puzzlegame\\image\\login\\登录按钮.png"));

        } else if (e.getSource() == register) {
            register.setIcon(new ImageIcon("..\\puzzlegame\\image\\login\\注册按钮.png"));
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
