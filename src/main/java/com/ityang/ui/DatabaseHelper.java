package com.ityang.ui;
import java.sql.*;
import com.google.gson.Gson;

public class DatabaseHelper {

    // 保存游戏状态到数据库
    public static void saveGameState(int userId, int[][] data, String imagePath, int step) {
        // 将数据数组转为JSON字符串
        Gson gson = new Gson();
        String gameState = gson.toJson(data);
        String url = "jdbc:mysql://localhost:3306/db01";
        String user = "root";
        String password = "1234";

        // 修改SQL，包含step字段
        String sql = "INSERT INTO game_progress (user_id, game_state, image_path, last_updated, step) " +
                "VALUES (?, ?, ?, NOW(), ?)";  // 添加 step 字段

        System.out.println("传入的用户ID: " + userId);  // 确认用户ID是否正确

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, userId);  // 用户ID
            statement.setString(2, gameState);  // 游戏状态
            statement.setString(3, imagePath);  // 图片路径
            statement.setInt(4, step);  // 设置步数

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("游戏进度保存成功！");
            } else {
                System.out.println("游戏进度保存失败！");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // 从数据库加载游戏状态
    public static GameState loadGameState(int userId) {
        String url = "jdbc:mysql://localhost:3306/db01";
        String user = "root";
        String password = "1234";
        String sql = "SELECT game_state, image_path FROM game_progress WHERE user_id = ? ORDER BY last_updated DESC LIMIT 1";

        int[][] data = null;  // 默认为 null
        String imagePath = null;

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, userId);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String gameState = resultSet.getString("game_state");
                // 使用Gson将JSON转换回二维数组
                Gson gson = new Gson();
                data = gson.fromJson(gameState, int[][].class);
                imagePath = resultSet.getString("image_path");
                System.out.println("加载的图片路径: " + imagePath);
            } else {
                System.out.println("DatabaseHelper：没有找到游戏进度！");
                // 如果没有找到，返回 null
                data = null;
                imagePath = null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // 返回 GameState 对象
        return new GameState(data, imagePath);
    }


}
