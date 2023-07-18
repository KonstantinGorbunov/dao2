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
        try (Connection connection1 = establishConnection(); PreparedStatement statement = connection1.prepareStatement("CREATE TABLE IF NOT EXISTS `test01`.`users` (\n" +
                "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
                "  `name` VARCHAR(45) NULL,\n" +
                "  `lastName` VARCHAR(45) NULL,\n" +
                "  `age` INT NULL,\n" +
                "  PRIMARY KEY (`id`));\n")) {
            connection1.setAutoCommit(false);
            statement.execute();
            connection1.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void dropUsersTable() {
        try (Connection connection1 = establishConnection();
             PreparedStatement statement = connection1.prepareStatement("DROP TABLE IF EXISTS `test01`.`users`;")) {
            connection1.setAutoCommit(false);
            statement.execute();
            connection1.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection connection1 = establishConnection();
             PreparedStatement statement = connection1.prepareStatement("INSERT INTO `test01`.`users` (name, lastName, age) VALUES(?, ?,?);")) {
            connection1.setAutoCommit(false);
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setInt(3, (int) age);
            statement.execute();
            connection1.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try (Connection connection1 = establishConnection();
             PreparedStatement statement = connection1.prepareStatement("DELETE FROM `test01`.`users` as u WHERE id = ?;")) {
            connection1.setAutoCommit(false);
            statement.setLong(1, id);
            statement.execute();
            connection1.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        ResultSet resultSet = null;
        List<User> listOfUsers = new ArrayList<>();
        try (Connection connection1 = establishConnection();
             PreparedStatement statement = connection1.prepareStatement("SELECT * FROM `test01`.`users`;")) {
            connection1.setAutoCommit(false);
            resultSet = statement.executeQuery();
            connection1.commit();
            while (resultSet.next()) {
                listOfUsers.add(new User(resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getString("lastName"),
                        resultSet.getByte("age")));
            }

            System.out.println("Users list has been created");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return listOfUsers;
    }

    public void cleanUsersTable() {
        try (Connection connection1 = establishConnection();
             PreparedStatement statement = connection1.prepareStatement("TRUNCATE `test01`.`users`;")) {
            connection1.setAutoCommit(false);
            statement.execute();
            connection1.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
