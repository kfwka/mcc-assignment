package database.services;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseInitService {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/mcc";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";
    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";

    public static void initialize() throws IOException {
        try {
            Class.forName(DB_DRIVER);

            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            if (connection.isValid(3)){
                System.out.println("Connected Successfully");
            }
            else {
                System.out.println("Connection Failed");
            }
            connection.close();
        } catch (Exception e) {
            System.out.println("Database Error: " + e.getMessage());
        }
    }

    public static String getDbUrl() {
        return DB_URL;
    }

    public static String getDbUser() {
        return DB_USER;
    }

    public static String getDbPassword() {
        return DB_PASSWORD;
    }

    public static String getDbDriver() {
        return DB_DRIVER;
    }
}
