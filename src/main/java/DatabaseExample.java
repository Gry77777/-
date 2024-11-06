import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseExample {

    public static void main(String[] args) {
        // 数据库连接参数
        String url = "jdbc:mysql://localhost:3306/db01";
        String user = "root";
        String password = "1234";

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            // 加载 JDBC 驱动
            Class.forName("com.mysql.cj.jdbc.Driver");

            // 创建连接
            connection = DriverManager.getConnection(url, user, password);

            // 创建语句对象
            statement = connection.createStatement();

            // 执行查询
            String sql = "SELECT * FROM tb_user"; // 替换为您的 SQL 查询
            resultSet = statement.executeQuery(sql);

            // 处理结果集
            while (resultSet.next()) {
                System.out.println("Column1: " + resultSet.getString("id")); // 替换为实际列名
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
