package jm.task.core.jdbc.util;

import java.sql.*;

public class Util {
    // DB connections settings...
    private static final String URL = "jdbc:mysql://localhost:3306/TaskJDBC";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
