package ru.kiomaru.ppproject.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Logger;

public class Util {
    // реализуйте настройку соеденения с БД
    private final Logger logger = Logger.getLogger(getClass().getName());
    private final String dbUrl = "jdbc:mysql://localhost/pp_schema";
    private final String dbUser = "kiomaru";
    private final String dbPass = "P@sTest123";

    public Util() {

    }

    public Connection getConnectionJDBC() {
        try {
            logger.fine("Connecting to database...");
            return DriverManager.getConnection(dbUrl, dbUser, dbPass);
        } catch (SQLException e) {
            logger.warning("Error: " + e.getMessage());
        }
        return null;
    }
}
