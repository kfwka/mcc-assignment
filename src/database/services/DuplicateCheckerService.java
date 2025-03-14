package database.services;

import java.sql.*;

public class DuplicateCheckerService {

    private final String table;
    private final String column;
    private final String value;

    public DuplicateCheckerService(String table, String column, String value) {
        this.table = table;
        this.column = column;
        this.value = value;
    }

    public boolean isExist() throws SQLException, ClassNotFoundException {
        Class.forName(DatabaseInitService.getDbDriver());

        Connection connection = DriverManager.getConnection(DatabaseInitService.getDbUrl(), DatabaseInitService.getDbUser(), DatabaseInitService.getDbPassword());

        String sql = "SELECT " + column + " FROM " + table + " WHERE " + column + " = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, value);
        ResultSet result = statement.executeQuery();

        connection.close();
        return result.next();
    }
}
