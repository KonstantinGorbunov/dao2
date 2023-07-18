package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl extends Util implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String query = "CREATE TABLE IF NOT EXISTS `test01`.`users` (\n" +
                "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
                "  `name` VARCHAR(45) NULL,\n" +
                "  `lastName` VARCHAR(45) NULL,\n" +
                "  `age` INT NULL,\n" +
                "  PRIMARY KEY (`id`));\n";
        try (Connection connection1 = establishConnection(); PreparedStatement statement = connection1.prepareStatement(query)) {
            statement.execute();
            System.out.println("users table has been created");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void dropUsersTable() {
        String query = "DROP TABLE IF EXISTS `test01`.`users`;";
        try (Connection connection1 = establishConnection(); PreparedStatement statement = connection1.prepareStatement(query)) {
            statement.execute();
            System.out.println("users table has been deleted");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String query = String.format("INSERT INTO `test01`.`users` (name, lastName, age) VALUES('%s','%s',%d);", name, lastName, age);
        try (Connection connection1 = establishConnection(); PreparedStatement statement = connection1.prepareStatement(query)) {
            statement.execute();
            System.out.println(String.format("user name: %s lastName: %s age: %d has been saved to the users table", name, lastName, age));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        String query = String.format("DELETE FROM `test01`.`users` as u WHERE id = %d;", id);
        try (Connection connection1 = establishConnection(); PreparedStatement statement = connection1.prepareStatement(query)) {
            statement.execute();
            System.out.println("The user has been deleted from the users table");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        List<User> listOfUsers = new ArrayList<>();
        String query = "SELECT * FROM `test01`.`users`;";
        try (Connection connection1 = establishConnection(); PreparedStatement statement = connection1.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                listOfUsers.add(new User(resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getString("lastName"),
                        resultSet.getByte("age")));
            }

            System.out.println("Users list has been created");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listOfUsers;
    }

    public void cleanUsersTable() {
        String query = "DELETE FROM `test01`.`users` as u;";
        try (Connection connection1 = establishConnection(); PreparedStatement statement = connection1.prepareStatement(query)) {
            statement.execute();
            System.out.println("The users table has been cleared");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
