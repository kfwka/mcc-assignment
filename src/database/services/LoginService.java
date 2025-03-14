package database.services;

import java.sql.*;

public class LoginService {

    private final String username;
    private final String password;
    private final boolean isCustomer;

    public LoginService(String username, String password, Boolean isCustomer) {
        this.username = username;
        System.out.println(username);
        System.out.println(password);
        System.out.println(isCustomer);
        this.password = password;
        this.isCustomer = isCustomer;
    }

    public boolean validate() throws SQLException, ClassNotFoundException {
        Class.forName(DatabaseInitService.getDbDriver());

        Connection connection = DriverManager.getConnection(DatabaseInitService.getDbUrl(), DatabaseInitService.getDbUser(), DatabaseInitService.getDbPassword());

        String sql;
        String sql1;

        if (isCustomer){
            sql = "SELECT * FROM Customers WHERE username = ? AND passwordHash = ?";
            sql1 = "SELECT salt FROM Customers WHERE username = ?";
        } else {
            sql = "SELECT * FROM Drivers WHERE username = ? AND passwordHash = ?";
            sql1 = "SELECT salt FROM Drivers WHERE username = ?";
        }

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, username);

        PreparedStatement statement1 = connection.prepareStatement(sql1);
        statement1.setString(1, username);
        ResultSet result1 = statement1.executeQuery();

        if(result1.next()){
            statement.setString(2, HashingService.hashPassword(password, HashingService.convertSalt(result1.getString(1))));
        }
        else {
            statement.setString(2, "");
        }

        ResultSet result = statement.executeQuery();
        if (result.next()) {
            System.out.println("Login Successful! Welcome, " + username);
            connection.close();
            return true;
        } else {
            System.out.println("Invalid Username or Password!");
            connection.close();
            return false;
        }
    }
}
