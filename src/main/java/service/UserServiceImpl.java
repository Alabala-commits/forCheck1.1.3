package service;

import dao.UserDao;
import dao.UserDaoHibernateImpl;
import model.User;

import java.sql.SQLException;
import java.util.List;

public class UserServiceImpl implements UserService {

    private UserDao userDao = null;

    public UserServiceImpl() {
        try {
            this.userDao = new UserDaoHibernateImpl();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createUsersTable() {
        userDao.createUsersTable();
    }

    public void dropUsersTable() {
        userDao.dropUsersTable();
    }

    public void saveUser(String name, String lastName, byte age) {
        userDao.saveUser(name, lastName, age);
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
