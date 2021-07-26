package dao;

import dao.UserDao;
import model.User;
import util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private static final String CLEAN_TABLE_SQL  = "TRUNCATE TABLE usersTable";
    private static final String SELECT_ALL_SQL   = "SELECT * FROM usersTable";
    private static final String REMOVE_BY_ID_SQL = "DELETE FROM usersTable WHERE id = ?";
    private static final String SAVE_USER_SQL    = "INSERT INTO usersTable (name, lastName, age) VALUES (?, ?, ?)";
    private static final String DROP_TABLE_SQL   = "DROP TABLE IF EXISTS usersTable";
    private static final String CREATE_TABLE_SQL = "CREATE TABLE IF NOT EXISTS usersTable " +
                                                   "(id INT AUTO_INCREMENT PRIMARY KEY, " +
                                                   " name VARCHAR(30), " +
                                                   " lastName VARCHAR (30), " +
                                                   " age INT)";

    public UserDaoJDBCImpl() {

    }

    @Override
    public void createUsersTable() {
        executionSql(CREATE_TABLE_SQL);
    }

    @Override
    public void dropUsersTable() {
        executionSql(DROP_TABLE_SQL);
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Connection        conn  = null;
        PreparedStatement pstmt = null;

        try {
            conn = Util.connection();
            conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            conn.setAutoCommit(false);

            pstmt = conn.prepareStatement(SAVE_USER_SQL);
            pstmt.setString(1, name);
            pstmt.setString(2, lastName);
            pstmt.setByte(3, age);
            pstmt.executeUpdate();

            conn.commit();
        } catch (SQLException e) {
            rollback(conn);
            e.printStackTrace();
        } finally {
            close(pstmt);
            close(conn);
        }

    }

    @Override
    public void removeUserById(long id) {
        Connection        conn  = null;
        PreparedStatement pstmt = null;

        try {
            conn = Util.connection();
            conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            conn.setAutoCommit(false);

            pstmt = conn.prepareStatement(REMOVE_BY_ID_SQL);
            pstmt.setLong(1, id);
            pstmt.executeUpdate();

            conn.commit();
        } catch (SQLException e) {
            rollback(conn);
            e.printStackTrace();
        } finally {
            close(pstmt);
            close(conn);
        }
    }

    @Override
    public List<User> getAllUsers() {
        Connection conn   = null;
        Statement  stmt   = null;
        ResultSet  rs     = null;
        List<User> result = new ArrayList<>();

        try {
            conn = Util.connection();
            conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            conn.setAutoCommit(false);

            stmt = conn.createStatement();
            rs   = stmt.executeQuery(SELECT_ALL_SQL);

            while (rs.next()) {
                User user = new User(rs.getString(2), rs.getString(3), rs.getByte(4));
                user.setId(rs.getLong(1));
                result.add(user);
            }
            conn.commit();
        } catch (SQLException e) {
            rollback(conn);
            e.printStackTrace();
        } finally {
            close(rs);
            close(stmt);
            close(conn);
        }
        return result;
    }

    @Override
    public void cleanUsersTable() {
        executionSql(CLEAN_TABLE_SQL);
    }

    private void executionSql(String SQL) {
        Connection conn = null;
        Statement  stmt = null;

        try {
            conn = Util.connection();
            conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            conn.setAutoCommit(false);

            stmt = conn.createStatement();
            stmt.executeUpdate(SQL);

            conn.commit();
        } catch (SQLException e) {
            rollback(conn);
            e.printStackTrace();
        } finally {
            close(stmt);
            close(conn);
        }
    }

    private void close(AutoCloseable res) {
        if (res != null) {
            try {
                res.close();
            } catch (Exception ignored) {}
        }
    }

    private void rollback(Connection conn) {
        if (conn != null) {
            try {
                conn.rollback();
            } catch (SQLException ignor) {}
        }
    }
}
