package db;

import java.sql.*;
import java.util.Properties;
import java.io.FileInputStream;

public class DbManager {
    private static Connection conn;

    // Método que devuelve una conexión lista para usar
    public static Connection getConnection() throws Exception {
        if (conn == null || conn.isClosed()) {
            Properties p = new Properties();
            p.load(new FileInputStream("config.properties"));

            String url = String.format(
                "jdbc:mysql://%s:%s/%s?serverTimezone=UTC",
                p.getProperty("host"),
                p.getProperty("port"),
                p.getProperty("database")
            );

            conn = DriverManager.getConnection(url, p.getProperty("user"), p.getProperty("password"));
        }
        return conn;
    }
}
