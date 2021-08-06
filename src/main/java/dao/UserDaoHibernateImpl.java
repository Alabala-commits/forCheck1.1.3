package dao;

import model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import util.Util;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    private SessionFactory sessionFactory = null;

    public UserDaoHibernateImpl() throws SQLException {
        this.sessionFactory = Util.sessionFactory();
    }


    @Override
    public void createUsersTable() {
        executionSql("CREATE TABLE IF NOT EXISTS users " +
                "(id INT AUTO_INCREMENT PRIMARY KEY, " +
                " name VARCHAR(30), " +
                " lastName VARCHAR (30), " +
                " age INT)");
    }

    @Override
    public void dropUsersTable() {
        executionSql("DROP TABLE IF EXISTS users");
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.persist(new User(name, lastName, age));
        session.getTransaction().commit();
    }

    @Override
    public void removeUserById(long id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.delete((User)session.load(User.class, id));
        session.getTransaction().commit();
    }

    @Override
    public List<User> getAllUsers() {
        List<User> result = new ArrayList<>();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        result = session.createQuery("FROM User").list();
        session.getTransaction().commit();
        return result;
    }

    @Override
    public void cleanUsersTable() {
        for (User user : getAllUsers()) {
            removeUserById(user.getId());
        }
    }

    private void executionSql(String sql) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.createSQLQuery(sql).addEntity(User.class).executeUpdate();
        session.getTransaction().commit();
    }
}
