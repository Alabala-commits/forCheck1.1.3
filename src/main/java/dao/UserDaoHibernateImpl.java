package dao;

import model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import util.Util;

import java.sql.SQLException;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    SessionFactory sessionFactory = null;

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
        Session session = sessionFactory.openSession();



        session.close();
    }

    @Override
    public void removeUserById(long id) {
        Session session = sessionFactory.openSession();



        session.close();
    }

    @Override
    public List<User> getAllUsers() {
        Session session = sessionFactory.openSession();



        session.close();
        return null;
    }

    @Override
    public void cleanUsersTable() {
        Session session = sessionFactory.openSession();



        session.close();
    }

    private void initSessionFactory() {
        try {
            this.sessionFactory = Util.sessionFactory();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void executionSql(String sql) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            session.createSQLQuery(sql).addEntity(User.class);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}
