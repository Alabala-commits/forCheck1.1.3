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

    public UserDaoHibernateImpl() {
        initSessionFactory();
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
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(new User(name, lastName, age));
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete((User)session.load(User.class, id));
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> result = new ArrayList<>();

        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            result = session.createQuery("FROM User").list();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void cleanUsersTable() {
        for (User user : getAllUsers()) {
            removeUserById(user.getId());
        }
    }

    private void initSessionFactory() {
        try {
            this.sessionFactory = Util.sessionFactory();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void executionSql(String sql) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createSQLQuery(sql).addEntity(User.class).executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
