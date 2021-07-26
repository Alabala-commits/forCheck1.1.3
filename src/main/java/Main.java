import dao.UserDao;
import dao.UserDaoJDBCImpl;
import model.User;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        UserDao userDao = new UserDaoJDBCImpl();
        userDao.createUsersTable();

        User user1 = new User("Gordan", "Freeman", (byte) 32);
        User user2 = new User("Bailey", "Whitfield", (byte) 77);
        User user3 = new User("Martin", "Hellman", (byte) 75);
        User user4 = new User("Mikle", "Jackson", (byte) 50);
        addUsersAndPrintInfo(userDao, user1, user2, user3, user4);

        List<User> users = userDao.getAllUsers();
        printUsers(users);

        userDao.cleanUsersTable();
        userDao.dropUsersTable();
    }

    private static void addUsersAndPrintInfo(UserDao userDao, User ... users) {
        for (User user : users) {
            userDao.saveUser(user.getName(), user.getLastName(), user.getAge());
            System.out.println("User с именем – \"" + user.getName() + "\" добавлен в базу данных");
        }
    }

    private static void printUsers(List<User> users) {
        for (User user : users) {
            System.out.println(user);
        }
    }
}
