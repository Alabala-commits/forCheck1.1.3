package util;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;

public class Util {

    static final String URL      = "jdbc:mysql://localhost:3306/testbase";
    static final String LOGIN    = "root";
    static final String PASSWORD = "Password";

    public static Connection connection() throws SQLException {

        Connection conn = DriverManager.getConnection(URL, LOGIN, PASSWORD);
        return conn;
    }
}
