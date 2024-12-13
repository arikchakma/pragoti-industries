package pragoti.users;

import pragoti.utils.FileHandler;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

public class Admin extends User implements Serializable {
    public Admin() {
    }

    public Admin(int id, String name, String gender, String email, String designation, LocalDate dob, LocalDate doj, String password, float salary) {
        super(id, name, gender, email, designation, dob, doj, password, salary);
    }

    // check if the admin exists
    // if not, create an admin
    public static void checkOrCreateAdmin() {
        User admin = User.getUser(1111);
        if (admin != null) {
            System.out.println("Admin already exists.");
            System.out.println(admin.toString());
            return;
        }

        System.out.println("Admin does not exist. Creating an admin...");
        admin = new Admin(1111, "Arik Chakma", "Male", "hello@arikko.dev", "Admin", LocalDate.of(2003, 11, 21), LocalDate.now(), "admin@17", 100000);
        saveUser(admin);
    }

    public static User createAndSaveUser(int id, String name, String gender, String email, String designation, LocalDate dob, LocalDate doj, String password, float salary) {
        User user = null;
        if(designation.equals("Admin")) {
            user = new Admin(id, name, gender, email, designation, dob, doj, password, salary);
        } else if (designation.equals("Logistic Officer")) {
            user = new LogisticOfficer(id, name, gender, email, designation, dob, doj, password, salary);
        }

        if (user != null) {
            saveUser(user);
        }

        return user;
    }

    public static boolean saveUser(User user) {
        return FileHandler.<User>writeObjectToFile(user, "users.bin");
    }

    public static boolean deleteUser(int id) {
        ArrayList<User> users = getAllUsers();
        if (users == null) {
            return false;
        }

        users.removeIf(user -> user.getId() == id);
        FileHandler.deleteFile("users.bin");
        return FileHandler.<User>replaceFile(users, "users.bin");
    }

    public static boolean updateAndSaveUser(User user) {
        ArrayList<User> users = getAllUsers();
        if (users == null) {
            return false;
        }


        int indexOfUser = -1;
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId() == user.getId()) {
                indexOfUser = i;
                break;
            }
        }

        users.set(indexOfUser, user);
        FileHandler.deleteFile("users.bin");
        return FileHandler.<User>replaceFile(users, "users.bin");
    }
}
