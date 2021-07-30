package util;

import model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import java.sql.*;
import java.util.Properties;

public class Util {

    static final String URL      = "jdbc:mysql://localhost:3306/testbase";
    static final String LOGIN    = "root";
    static final String PASSWORD = "Password";

    private static SessionFactory sessionFactory = null;

    public static Connection connection() throws SQLException {
        return DriverManager.getConnection(URL, LOGIN, PASSWORD);
    }

    public static SessionFactory sessionFactory() throws SQLException {
        if (sessionFactory == null) {
            Properties properties = new Properties();
            properties.put(Environment.DRIVER, DriverManager.getDriver(URL));
            properties.put(Environment.URL, URL);
            properties.put(Environment.USER, LOGIN);
            properties.put(Environment.PASS, PASSWORD);
            properties.put(Environment.DIALECT, "org.hibernate.dialect.MySQLDialect");
            properties.put(Environment.SHOW_SQL, "true");
            properties.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
            properties.put(Environment.HBM2DDL_AUTO, "create-drop");

            Configuration cfg = new Configuration().configure();
            cfg.setProperties(properties);
            cfg.addAnnotatedClass(User.class);

            sessionFactory = cfg.buildSessionFactory(new StandardServiceRegistryBuilder()
                                                            .applySettings(cfg.getProperties())
                                                            .build());
        }
        return sessionFactory;
    }
}
