package ru.kiomaru.ppproject.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ru.kiomaru.ppproject.model.User;

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

    //Поле для Hibernate
    private final Properties props = new Properties();
    private final SessionFactory sessionFactory;

    {
        props.setProperty("hibernate.connection.url", dbUrl);
        props.setProperty("hibernate.connection.username", dbUser);
        props.setProperty("hibernate.connection.password", dbPass);
        props.setProperty("dialect", "org.hibernate.dialect.MariaDBDialect");
        SessionFactory tempSessionFactory = null;
        try {
            tempSessionFactory = new Configuration().addAnnotatedClass(User.class).addProperties(props).buildSessionFactory();
        } catch (Throwable ex) {
            logger.warning("Error: " + ex.getMessage());
        }
        sessionFactory = tempSessionFactory;
    }


    public Util() {

    }

    public SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            logger.warning("Error: sessionFactory is null");
            return null;
        }
        return sessionFactory;
    }

    public void shutdownFactory() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }

    //Подключение к MySQL через JDBC
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
