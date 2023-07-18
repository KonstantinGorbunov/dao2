package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("User1", "User1LastName", (byte) 30);
        userService.saveUser("User2", "User2LastName", (byte) 30);
        userService.saveUser("User3", "User3LastName", (byte) 30);
        userService.saveUser("User4", "User4LastName", (byte) 30);
        userService.getAllUsers().stream().forEach(System.out::println);
        userService.cleanUsersTable();
        userService.dropUsersTable();

    }
}
