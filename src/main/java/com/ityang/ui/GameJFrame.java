package com.ityang.ui;

import com.google.gson.Gson;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.security.PublicKey;
import java.sql.*;
import java.util.Arrays;
import java.util.Random;

public class GameJFrame extends JFrame implements KeyListener, ActionListener {
    private int userId;
    private String ImagePath;
    private boolean isLoaded = false;

    public GameJFrame(int userId) {
        this.userId = userId;
//        System.out.println("用户id为：" + userId);
//        // 保存 userId
//        // 加载游戏进度
//        System.out.println(userId);
//        loadGameProgress(userId);
    }

    public int getUserId() {
        return userId;
    }

    int[][] data = new int[4][4];

    public void setUserId(int userId) {
        this.userId = userId;
        System.out.println("用户id为：" + userId);
        loadGameProgress(userId);  // 在设置完 userId 后，调用加载进度的方法
    }

    int x = 0;
    int y = 0;
    static Random random = new Random();
    static String path = "..\\NewGame\\image\\";
    static String[] array = {"animal", "girl", "sport"};
    static int R = 0;
    static int r = random.nextInt(8) + 1;
    static String P = array[R];
    static String resultPath = P + "\\" + P + r + "\\";
    int[][] win = {{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}, {13, 14, 15, 0}};
    // 步数
    int step = 0;
    JMenuItem girl = new JMenuItem("美女");
    JMenuItem animal = new JMenuItem("动物");
    JMenuItem sport = new JMenuItem("运动");
    JMenuItem replayItem = new JMenuItem("重新游戏");
    JMenuItem reLoginItem = new JMenuItem("重新登录");
    JMenuItem closeItem = new JMenuItem("关闭游戏");
    JMenuItem accountItem = new JMenuItem("公众号");
    JMenuItem MoreItem = new JMenuItem("更多功能");
    JMenuItem saveItem = new JMenuItem("保存");

    public GameJFrame() {
        //初始化界面
        initJFrame();
        //初始化菜单
        initJMenuBar();

        InitDate();
        //初始化图片
//            initImage("");
        this.setVisible(true);
    }

    private void InitDate() {
        int tempArray[] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 0};
        Random random = new Random();
        for (int i = 0; i < tempArray.length; i++) {
            int index = random.nextInt(tempArray.length);
            int temp = tempArray[i];
            tempArray[i] = tempArray[index];
            tempArray[index] = temp;
        }
//        int index = 0;
//        for (int i = 0; i < 4; i++) {
//            for (int j = 0; j < 4; j++) {
//                data[i][j] = tempArray[index];
//                index++;
//            }
//        }
        for (int i = 0; i < tempArray.length; i++) {
            if (tempArray[i] == 0) {
                x = i / 4;
                y = i % 4;
            }
            data[i / 4][i % 4] = tempArray[i];
        }
    }

    public void FindZero(){
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (data[i][j] == 0) {
                    x = i;  // 找到0所在的行
                    y = j;  // 找到0所在的列
                    break;  // 找到0后，跳出循环
                }
            }
            if (x != -1 && y != -1) break;  // 如果找到了0，跳出外层循环
        }

    }
//        private void initImage () {
//            this.getContentPane().removeAll();
//            if (victory()) {
//                JLabel winJlable = new JLabel(new ImageIcon("..\\puzzlegame\\image\\win.png"));
//                winJlable.setBounds(203, 283, 284, 238);
//                this.getContentPane().add(winJlable);
//
//            }
//            //计数器
//            JLabel stepCount = new JLabel("步数：" + step);
//            stepCount.setBounds(50, 30, 100, 50);
//            this.getContentPane().add(stepCount);

//            for (int i = 0; i < 4; i++) {
//                for (int j = 0; j < 4; j++) {
//                    int num = data[i][j];
//                    //        ImageIcon icon = new ImageIcon("D:\\develop\\puzzlegame\\animal\\1.jpg");
//                    JLabel jLabel = new JLabel(new ImageIcon(path + resultPath + num + ".jpg"));
//                    jLabel.setBounds(105 * j + 83, 105 * i + 134, 105, 105);
//                    //this.add(jLabel);
//                    jLabel.setBorder(new BevelBorder(1));
//                    //取消默认的居中放置
//                    this.getContentPane().add(jLabel);
//                }
//            }
//            //背景图片
//            JLabel background = new JLabel(new ImageIcon("..\\puzzlegame\\image\\background.png"));
//            background.setBounds(40, 40, 508, 560);
//            this.getContentPane().add(background);
//            this.getContentPane().repaint();
//        }


    private void initImage(String imagePath) {
        FindZero();
        System.out.println("Received imagePath: " + imagePath);
        System.out.println(Arrays.deepToString(data));
        if (imagePath != null && !imagePath.isEmpty()) {
            ImagePath = path + imagePath;
        }
//        if (isLoaded) {
//            return;  // 防止重复加载
//        }
        if ("fresh".equals(imagePath)) {
            // 如果没有读取到任何数据和路径，则显示默认设置
            System.out.println("未能加载游戏进度，使用默认设置77777");
            // 使用默认设置的路径
            ImagePath = path + resultPath;
            if (victory()) {
                JLabel winJlable = new JLabel(new ImageIcon("..\\NewGame\\image\\win.png"));
                winJlable.setBounds(203, 283, 284, 238);
                this.getContentPane().add(winJlable);
            }
            // 计数器
            JLabel stepCount = new JLabel("步数：" + step);
            stepCount.setBounds(50, 30, 100, 50);
            this.getContentPane().add(stepCount);
            // 默认的 data 数组
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    int num = data[i][j];  // 使用当前的 data 数组，确保即使是默认的也能正常显示
                    JLabel jLabel = new JLabel(new ImageIcon(ImagePath + num + ".jpg"));
                    jLabel.setBounds(105 * j + 83, 105 * i + 134, 105, 105);
                    jLabel.setBorder(new BevelBorder(1));
                    this.getContentPane().add(jLabel);
                }
            }
            // 背景图片
            JLabel background = new JLabel(new ImageIcon("..\\NewGame\\image\\background.png"));
            background.setBounds(40, 40, 508, 560);
            this.getContentPane().add(background);
            this.getContentPane().revalidate();
            this.getContentPane().repaint();
        }
        // 只有当 imagePath 为 null 或空字符串且 data 为空时才使用默认设置
        if ((imagePath != null && !imagePath.isEmpty()) && data != null) {
            this.getContentPane().removeAll();
            // 使用读取到的路径和数据
            if (victory()) {
                JLabel winJlable = new JLabel(new ImageIcon("..\\NewGame\\image\\win.png"));
                winJlable.setBounds(203, 283, 284, 238);
                this.getContentPane().add(winJlable);
            }
            // 计数器
            JLabel stepCount = new JLabel("步数：" + step);
            stepCount.setBounds(50, 30, 100, 50);
            this.getContentPane().add(stepCount);
            // 加载游戏数据图片
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    int num = data[i][j];
                    JLabel jLabel = new JLabel(new ImageIcon(path + ImagePath + num + ".jpg"));
                    jLabel.setBounds(105 * j + 83, 105 * i + 134, 105, 105);
                    jLabel.setBorder(new BevelBorder(1));
                    this.getContentPane().add(jLabel);
                }
            }
            // 背景图片
            JLabel background = new JLabel(new ImageIcon("..\\NewGame\\image\\background.png"));
            background.setBounds(40, 40, 508, 560);
            this.getContentPane().add(background);
            this.getContentPane().revalidate();
            this.getContentPane().repaint();
        }
        if (data != null) {
            this.getContentPane().removeAll();
            System.out.println("data != null时" + ImagePath);
            // 如果 imagePath 为 null，但已经加载了有效的 data 数据，继续显示数据而不使用默认路径
            if (victory()) {
                JLabel winJlable = new JLabel(new ImageIcon("..\\NewGame\\image\\win.png"));
                winJlable.setBounds(203, 283, 284, 238);
                this.getContentPane().add(winJlable);
            }
            // 计数器
            JLabel stepCount = new JLabel("步数：" + step);
            stepCount.setBounds(50, 30, 100, 50);
            this.getContentPane().add(stepCount);
            // 使用当前的 data 数组显示图片
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    int num = data[i][j];  // 使用当前的 data 数组，确保即使是默认的也能正常显示
                    JLabel jLabel = new JLabel(new ImageIcon(ImagePath + num + ".jpg"));
                    jLabel.setBounds(105 * j + 83, 105 * i + 134, 105, 105);
                    jLabel.setBorder(new BevelBorder(1));
                    this.getContentPane().add(jLabel);
                }
            }
            // 背景图片
            JLabel background = new JLabel(new ImageIcon("..\\NewGame\\image\\background.png"));
            background.setBounds(40, 40, 508, 560);
            this.getContentPane().add(background);
            this.getContentPane().revalidate();
            this.getContentPane().repaint();
        }
    }


    private void initJMenuBar() {
        //初始化菜单
        JMenuBar jMenuBar = new JMenuBar();
        JMenu functionJmenu = new JMenu("功能");
        JMenu aboutJmenu = new JMenu("关于我们");
        JMenu changeImage = new JMenu("更换图片");
        JMenu MoreJmenu = new JMenu("更多");
        MoreItem = new JMenuItem("排行榜");
        saveItem = new JMenuItem("保存");
        replayItem = new JMenuItem("重新游戏");
        reLoginItem = new JMenuItem("重新登录");
        closeItem = new JMenuItem("关闭游戏");
        accountItem = new JMenuItem("公众号");


        changeImage.add(girl);
        changeImage.add(animal);
        changeImage.add(sport);

        //将每个选项下面的条目添加到菜单中
        functionJmenu.add(changeImage);
        functionJmenu.add(replayItem);
        functionJmenu.add(reLoginItem);
        functionJmenu.add(closeItem);
        //把公众号添加到关于我们当中
        aboutJmenu.add(accountItem);
        //给条目绑定事件
        MoreJmenu.add(MoreItem);
        MoreJmenu.add(saveItem);
        //给条目绑定事件
        replayItem.addActionListener(this);
        reLoginItem.addActionListener(this);
        closeItem.addActionListener(this);
        accountItem.addActionListener(this);
        MoreItem.addActionListener(this);
        saveItem.addActionListener(this);
        //给更换事件绑定
        girl.addActionListener(this);
        animal.addActionListener(this);
        sport.addActionListener(this);
        //将菜单添加到菜单栏中
        jMenuBar.add(functionJmenu);
        jMenuBar.add(aboutJmenu);
        jMenuBar.add(MoreJmenu);
        //将菜单栏添加到界面中
        this.setJMenuBar(jMenuBar);
    }

    private void initJFrame() {
        this.setSize(603, 680);
        this.setTitle("拼图游戏单机 v1.0");
        this.setAlwaysOnTop(true);
        this.setLocationRelativeTo(null);
        setDefaultCloseOperation(3);
//        this.setLayout(null);
        this.addKeyListener(this);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (victory()) {
            return;
        }
        int code = e.getKeyCode();
        if (code == 65) {
            this.getContentPane().removeAll();
            JLabel all = new JLabel(new ImageIcon(path + resultPath + "all.jpg"));
            all.setBounds(83, 134, 420, 420);
            this.getContentPane().add(all);
            JLabel background = new JLabel(new ImageIcon(path + "background.png"));
            background.setBounds(40, 40, 508, 560);
            this.getContentPane().add(background);
            this.getContentPane().repaint();
        }
    }

    // 保存进度
    public void saveGameProgress(int userId) {
        String imagePath = resultPath;  // 当前的图片路径
        DatabaseHelper.saveGameState(userId, data, imagePath, step);
    }

    //读取存档
    public void loadGameProgress(int userId) {
        GameState gameState = DatabaseHelper.loadGameState(userId);
        if (gameState.getData() != null) {
            data = gameState.getData();
            String imagePath = gameState.getImagePath();
            resultPath = imagePath != null ? imagePath : resultPath;  // 如果读取到的路径为空，使用之前的路径
            if (!isLoaded) {
                initImage(resultPath);  // 加载游戏进度中的路径
                isLoaded = true;
                System.out.println("读取存档成功，使用路径：" + imagePath);
                System.out.println("读取到的游戏数据: " + Arrays.deepToString(gameState.getData()));
            }
        } else {
            // 如果没有读取到存档，使用默认设置
            System.out.println("未能加载游戏进度，使用默认设置111");
            InitDate();
            initImage("fresh"); // 默认路径
        }
    }


    @Override
    public void keyReleased(KeyEvent e) {
        //判断游戏是否胜利
        if (victory()) {
            return;
        }
        int code = e.getKeyCode();
        System.out.println(code);
//        if (x == 3 & y == 3) {
//            return;
//        }
        if (code == 37) { // 左箭头
            if (y == 0) {
                return;
            }
            System.out.println("向左");
            data[x][y] = data[x][y - 1];
            data[x][y - 1] = 0;
            y--;
            step++;
            System.out.println(Arrays.deepToString(data));
            initImage(null);
        } else if (code == 38) { // 上箭头
            if (x == 0) {
                return;
            }
            System.out.println("向上");
            data[x][y] = data[x - 1][y];
            data[x - 1][y] = 0;
            x--;
            step++;
            System.out.println(Arrays.deepToString(data));
            initImage(null);
        } else if (code == 39) { // 右箭头
            if (y == 3) {
                return;
            }
            System.out.println("向右");
            data[x][y] = data[x][y + 1];
            data[x][y + 1] = 0;
            y++;
            step++;
            System.out.println(Arrays.deepToString(data));
            initImage(null);
        } else if (code == 40) { // 下箭头
            if (x == 3) {
                return;
            }
            System.out.println("向下");
            data[x][y] = data[x + 1][y];
            data[x + 1][y] = 0;
            x++;
            step++;
            System.out.println(Arrays.deepToString(data));
            initImage(null);
        } else if (code == 65) {
            initImage(null);
        } else if (code == 87) {
            data = new int[][]{{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}, {13, 14, 15, 0}};
            initImage(null);
        }
    }
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
    public boolean victory() {
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                if (data[i][j] != win[i][j])
                    return false;
            }
        }
        return true;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if (o == replayItem) {
            step = 0;
            InitDate();
            initImage(null);
        } else if (o == reLoginItem) {
            this.setVisible(false);
            new LoginJFrame();
        } else if (o == closeItem) {
            System.exit(0);
        } else if (o == accountItem) {
            System.out.println("公众号");
            //创建弹窗对象
            JDialog jDialog = new JDialog();
            //创建管理图片对象容器
            JLabel jLabel = new JLabel(new ImageIcon("..\\NewGameNewGame\\image\\wechat.png"));
            //设置图片大小
            jLabel.setBounds(0, 0, 450, 500);
            jDialog.getContentPane().add(jLabel);
            jDialog.setSize(450, 500);
            //设置弹窗置顶
            jDialog.setAlwaysOnTop(true);
            //设置弹窗位置
            jDialog.setLocationRelativeTo(null);
            //设置弹窗模式
            jDialog.setModal(true);
            jDialog.setVisible(true);
        } else if (o == MoreItem) {
            System.out.println("More");
            // 创建弹窗对象
            JDialog jDialog = new JDialog();
            // 创建显示文本的标签
            JLabel jLabel = new JLabel("功能尚未开发", SwingConstants.CENTER); // 使用中心对齐
            // 设置标签大小和位置
            jLabel.setBounds(0, 0, 450, 500);
            jDialog.getContentPane().add(jLabel);
            jLabel.setFont(jLabel.getFont().deriveFont(50f)); // 调整大小为50
            jDialog.setSize(450, 500);
            // 设置弹窗置顶
            jDialog.setAlwaysOnTop(true);
            // 设置弹窗位置
            jDialog.setLocationRelativeTo(null);
            // 设置弹窗模式
            jDialog.setModal(true);
            jDialog.setVisible(true);

        } else if (o == saveItem) {
            saveGameProgress(userId);
            System.out.println("save");
            showJDialog("保存成功");

        } else if (o == girl) {
            step = 0;
            R = 1;
            r = random.nextInt(8) + 1; // 更新r值，确保每次点击时随机选择一个图片
            resultPath = array[R] + "\\" + array[R] + r + "\\";
            InitDate();
            initImage(resultPath);
        } else if (o == animal) {
            step = 0;
            R = 0;
            r = random.nextInt(8) + 1;
            resultPath = array[R] + "\\" + array[R] + r + "\\";
            InitDate();
            initImage(resultPath);
        } else if (o == sport) {
            step = 0;
            R = 2;
            r = random.nextInt(8) + 1;
            resultPath = array[R] + "\\" + array[R] + r + "\\";
            System.out.println("运动的路径为" + resultPath);
            InitDate();
            initImage(resultPath);
        }
    }
}

