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
    private final Properties properties = new Properties();
    private final Logger logger = Logger.getLogger(getClass().getName());

    public Util() {
        try (InputStream in = getClass().getClassLoader().getResourceAsStream("db.properties")) {
            properties.load(in);
        } catch (IOException e) {
            logger.warning("Error: " + e.getMessage());
        }
    }
    public Connection getConnectionJDBC() {
        try {
            String url = properties.getProperty("db.url");
            String user = properties.getProperty("db.username");
            String password = properties.getProperty("db.password");
            logger.fine("Connecting to database...");
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            logger.warning("Error: " + e.getMessage());
        }
        return null;
    }

    public Connection getConnectionHibernate() {

        return null;
    }
}
