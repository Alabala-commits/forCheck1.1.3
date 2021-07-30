package util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.sql.*;

public class Util {

    static final String URL      = "jdbc:mysql://localhost:3306/testbase";
    static final String LOGIN    = "root";
    static final String PASSWORD = "Password";

    public static Connection connection() throws SQLException {
        return DriverManager.getConnection(URL, LOGIN, PASSWORD);
    }

    public static SessionFactory sessionFactory() {
        Configuration cfg = new Configuration().configure();
        return cfg.buildSessionFactory(new StandardServiceRegistryBuilder().applySettings(cfg.getProperties())
                                                                           .build());
    }
}
