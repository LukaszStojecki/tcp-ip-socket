package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class JDBCConfiguration {
    private static final String URL = "jdbc:mysql://localhost:3306/primesoft?serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "q1we2rty3";

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
}
