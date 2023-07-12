package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

public class Main {
    private final static UserService userService = new UserServiceImpl();

    public static void main(String[] args) {

        userService.createUsersTable();

        userService.saveUser("Nikita", "Mishenev", (byte) 19);
        userService.saveUser("Igor", "Vasylev", (byte) 29);
        userService.saveUser("Max", "Alehin", (byte) 20);
        userService.saveUser("Oleynikov", "Egor", (byte) 25);

        userService.removeUserById(2);

        userService.getAllUsers();

        userService.cleanUsersTable();

        userService.dropUsersTable();

        Util.closeSessionFactory();
    }
}






