package ru.kiomaru.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

public class Util {
    // реализуйте настройку соеденения с БД
    private final String URL = "jdbc:mysql://localhost/pp_schema";
    private final String USER = "kiomaru";
    private final String PASSWORD = "P@sTest123";

    Logger logger = Logger.getLogger(getClass().getName());

    public Connection getConnection() {
        try {
            logger.fine("Connecting to database...");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            logger.warning("Error: " + e.getMessage());
        }
        return null;
    }
}
