package database.services;

import java.sql.*;

public class LoginService {

    private final String username;
    private final String password;

    public LoginService(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void validate() throws SQLException, ClassNotFoundException {
        Class.forName(DatabaseInitService.getDbDriver());

        Connection connection = DriverManager.getConnection(DatabaseInitService.getDbUrl(), DatabaseInitService.getDbUser(), DatabaseInitService.getDbPassword());

        String sql = "SELECT * FROM Customers WHERE username = ? AND passwordHash = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, username);

        String sql1 = "SELECT salt FROM Customers WHERE username = ?";
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
        } else {
            System.out.println("Invalid Username or Password!");
        }
    }
}
