package sample.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect {
    public Connection getConnection() throws ClassNotFoundException, SQLException {
        DBConfig config = new DBConfig();
        Class.forName("com.mysql.cj.jdbc.Driver");

        return DriverManager.getConnection(config.getDbUri(), config.getDbUsername(), config.getDbPassword());
    }
}
