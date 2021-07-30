package dao;

import model.User;
import util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    public UserDaoJDBCImpl() {

    }

    @Override
    public void createUsersTable() {
        executionSql("CREATE TABLE IF NOT EXISTS usersTable " +
                     "(id INT AUTO_INCREMENT PRIMARY KEY, " +
                     " name VARCHAR(30), " +
                     " lastName VARCHAR (30), " +
                     " age INT)");
    }

    @Override
    public void dropUsersTable() {
        executionSql("DROP TABLE IF EXISTS usersTable");
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {

        try (Connection conn = Util.connection()) {
            conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            conn.setAutoCommit(false);

            try (PreparedStatement pstmt = conn.prepareStatement(
                    "INSERT INTO usersTable (name, lastName, age) VALUES (?, ?, ?)")) {
                pstmt.setString(1, name);
                pstmt.setString(2, lastName);
                pstmt.setByte(3, age);
                pstmt.executeUpdate();

                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {

        try (Connection conn = Util.connection()) {
            conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            conn.setAutoCommit(false);

            try (PreparedStatement pstmt = conn.prepareStatement(
                    "DELETE FROM usersTable WHERE id = ?")) {
                pstmt.setLong(1, id);
                pstmt.executeUpdate();

                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> result = new ArrayList<>();

        try (Connection conn = Util.connection()) {
            conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            conn.setAutoCommit(false);

            try (ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM usersTable")) {

                while (rs.next()) {
                    User user = new User(rs.getString(2), rs.getString(3), rs.getByte(4));
                    user.setId(rs.getLong(1));
                    result.add(user);
                }
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void cleanUsersTable() {
        executionSql("TRUNCATE TABLE usersTable");
    }

    private void executionSql(String sql) {

        try (Connection conn = Util.connection()) {
            conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            conn.setAutoCommit(false);

            try (Statement stmt = conn.createStatement()) {
                stmt.executeUpdate(sql);

                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
