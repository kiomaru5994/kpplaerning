package ru.kiomaru.ppproject.dao;

import ru.kiomaru.ppproject.model.User;
import ru.kiomaru.ppproject.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class UserDaoJDBCImpl implements UserDao {
    private final Logger logger = Logger.getLogger(getClass().getName());
    private final Util util = new Util();

    public UserDaoJDBCImpl() {

    }
    private boolean tableExists() {
        try (Connection connection = util.getConnection();
             Statement stmt = connection.createStatement()) {
            String tableExistSql = "SELECT EXISTS(" +
                    "    SELECT 1" +
                    "    FROM information_schema.tables" +
                    "    WHERE table_schema = 'pp_schema'" +
                    "      AND table_name = 'Users'" +
                    ") AS table_exists;";
            ResultSet resultSet = stmt.executeQuery(tableExistSql);
            if (resultSet.next()) {
                return resultSet.getBoolean("table_exists");
            }
        } catch (SQLException e) {
            logger.warning("Error: " + e.getMessage());
        }
        return false;
    }
    public void createUsersTable() {
        if (tableExists()) return;
        try (Connection connection = util.getConnection();
             Statement stmt = connection.createStatement()) {
            String createUserTableSQL = "CREATE TABLE `pp_schema`.`Users` (" +
                    "  `id` BIGINT NOT NULL AUTO_INCREMENT," +
                    "  `name` VARCHAR(15) NOT NULL," +
                    "  `lastName` VARCHAR(15) NOT NULL," +
                    "  `age` TINYINT NOT NULL," +
                    "  PRIMARY KEY (`id`)," +
                    "  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE);";
            stmt.executeUpdate(createUserTableSQL);
            logger.fine("Users table created");
        } catch (SQLException e) {
            logger.warning("Error: " + e.getMessage());
        }
    }

    public void dropUsersTable() {
        if (!tableExists()) return;
        try (Connection connection = util.getConnection();
             Statement stmt = connection.createStatement()) {
            String dropUserTableSQL = "DROP TABLE `pp_schema`.`Users`;";
            stmt.executeUpdate(dropUserTableSQL);
            logger.fine("Users table dropped");
        } catch (SQLException e) {
            logger.warning("Error: " + e.getMessage());
        }

    }

    public void saveUser(String name, String lastName, byte age) {
        String saveUserToSQL = "INSERT INTO pp_schema.Users (name, lastName, age) VALUES (?, ?, ?)";
        try (Connection connection = util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(saveUserToSQL)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            logger.fine("User saved to database");
            System.out.println("User с именем - " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            logger.warning("Error: " + e.getMessage());
        }
    }

    public void removeUserById(long id) {
        String removeUserFromSQL = "DELETE FROM pp_schema.Users WHERE id = ?";
        try (Connection connection = util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(removeUserFromSQL)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            logger.fine("User removed from database");
        } catch (SQLException e) {
            logger.warning("Error: " + e.getMessage());
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String selectAllUsersSQL = "SELECT * FROM pp_schema.Users;";
        try (Connection connection = util.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(selectAllUsersSQL);
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                users.add(user);
            }
            logger.fine("Users table loaded");
        } catch (SQLException e) {
            logger.warning("Error: " + e.getMessage());
        }
        return users;
    }

    public void cleanUsersTable() {
        @SuppressWarnings("SqlWithoutWhere")
        String cleanUsersTableSQL = "DELETE FROM `pp_schema`.`Users`;";
        try (Connection conn = util.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(cleanUsersTableSQL);
            logger.fine("Users table cleaned");
        } catch (SQLException e) {
            logger.warning("Error: " + e.getMessage());
        }
    }
}
