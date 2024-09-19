package ru.kiomaru.ppproject.service;

import ru.kiomaru.ppproject.dao.UserDao;
import ru.kiomaru.ppproject.dao.UserDaoHibernateImpl;
import ru.kiomaru.ppproject.dao.UserDaoJDBCImpl;
import ru.kiomaru.ppproject.model.User;

import java.util.List;

public class UserServiceImpl implements UserService {
    //private final UserDao userDao = new UserDaoJDBCImpl();
    private final UserDao userDao = new UserDaoHibernateImpl();

    public void createUsersTable() {
        userDao.createUsersTable();
    }

    public void dropUsersTable() {
        userDao.dropUsersTable();
    }

    public void saveUser(String name, String lastName, byte age) {
        userDao.saveUser(name, lastName, age);
        System.out.println("User с именем - " + name + " добавлен в базу данных");
    }

    public void removeUserById(long id) {
        userDao.removeUserById(id);
    }

    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    public void cleanUsersTable() {
        userDao.cleanUsersTable();
    }
}
