package ru.kiomaru.ppproject;

import ru.kiomaru.ppproject.model.User;
import ru.kiomaru.ppproject.service.UserService;
import ru.kiomaru.ppproject.service.UserServiceImpl;

import java.util.List;


public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Alexey", "Ivanov", (byte) 22);
        userService.saveUser("Dmitry", "Antipov", (byte) 27);
        userService.saveUser("Dmitry", "Kokorin", (byte) 58);
        userService.saveUser("Темный", "Эльф", (byte) 124);
        List<User> users = userService.getAllUsers();
        for (User user : users) {
            System.out.println(user.toString());
        }
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
