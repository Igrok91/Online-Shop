package ru.innopolis.uni.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by innopolis on 24.12.2016.
 */
public class DBConnection {

    public static final String DATABASE_URL = "jdbc:mysql://localhost:3306/online_shop?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private static Connection conn;


    private DBConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(
                    DATABASE_URL, "root",
                    "Sparta1991");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static Connection getConnecton() {
        if (conn != null) {
            return conn;
        } else {
            return new DBConnection().conn;
        }
    }

    public void close() throws SQLException {
        if (conn != null && !conn.isClosed()) {
            conn.close();
            conn = null;
        }
    }
}
