package service;

import model.User;

import java.sql.SQLException;
import java.util.List;

public interface UserService {
    void createUsersTable();

    void dropUsersTable();

    void saveUser(String name, String lastName, byte age) throws SQLException;

    void removeUserById(long id);

    List<User> getAllUsers();

    void cleanUsersTable();
}
