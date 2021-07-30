package dao;

import model.User;
import org.hibernate.SessionFactory;
import util.Util;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    SessionFactory sessionFactory = null;

    public UserDaoHibernateImpl() {
        this.sessionFactory = Util.sessionFactory();
    }


    @Override
    public void createUsersTable() {

    }

    @Override
    public void dropUsersTable() {

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {

    }

    @Override
    public void removeUserById(long id) {

    }

    @Override
    public List<User> getAllUsers() {
        return null;
    }

    @Override
    public void cleanUsersTable() {

    }
}
