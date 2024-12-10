package pragoti.users;

import java.io.Serializable;
import java.time.LocalDate;

public class LogisticOfficer extends User implements Serializable {
    public LogisticOfficer() {
    }

    public LogisticOfficer(int id, String name, String gender, String email, String designation, LocalDate dob, LocalDate doj, String password, float salary) {
        super(id, name, gender, email, designation, dob, doj, password, salary);
    }

    public boolean applyForLeave(LocalDate startDate, LocalDate endDate, String reason) {
        return true;
    }
}
