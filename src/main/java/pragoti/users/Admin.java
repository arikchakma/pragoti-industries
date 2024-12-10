package pragoti.users;

import java.io.Serializable;
import java.time.LocalDate;

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
        admin = new Admin(1111, "Arik Chakma", "Male", "hello@arikko.dev", "Admin", LocalDate.of(2003, 11, 21), LocalDate.now(), "admin", 100000);
        User.saveUser(admin);
    }
}
