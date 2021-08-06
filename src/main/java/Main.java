import model.User;
import service.UserService;
import service.UserServiceImpl;

import java.sql.SQLException;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        UserService userService = new UserServiceImpl();
        userService.createUsersTable();

        User user1 = new User("Gordan", "Freeman", (byte) 32);
        User user2 = new User("Bailey", "Whitfield", (byte) 77);
        User user3 = new User("Martin", "Hellman", (byte) 75);
        User user4 = new User("Mikle", "Jackson", (byte) 50);
        addUsersAndPrintInfo(userService, user1, user2, user3, user4);

        List<User> users = userService.getAllUsers();
        printUsers(users);

        userService.cleanUsersTable();
        userService.dropUsersTable();
    }

    private static void addUsersAndPrintInfo(UserService userService, User ... users) {
        for (User user : users) {
            userService.saveUser(user.getName(), user.getLastName(), user.getAge());
            System.out.println("User с именем – \"" + user.getName() + "\" добавлен в базу данных");
        }
    }

    private static void printUsers(List<User> users) {
        for (User user : users) {
            System.out.println(user);
        }
    }
}
