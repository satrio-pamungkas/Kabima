package sample.config;

public class DBConfig {
    private static final String DB_URI = "jdbc:mysql://localhost:3306/kabima";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "";

    public String getDbUri() {
        return DB_URI;
    }

    public String getDbUsername() {
        return DB_USERNAME;
    }

    public String getDbPassword() {
        return DB_PASSWORD;
    }
}
