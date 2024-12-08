package pragoti.shared;

import java.io.Serializable;
import java.time.LocalDate;

public abstract class User implements Serializable {
    protected int id;
    protected String name, gender, email;
    protected LocalDate dob, doj;
    private String password;

    public User() {
    }

    public User(int id, String name, String gender, String email, LocalDate dob, LocalDate doj, String password) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.email = email;
        this.dob = dob;
        this.doj = doj;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public LocalDate getDoj() {
        return doj;
    }

    public void setDoj(LocalDate doj) {
        this.doj = doj;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", email='" + email + '\'' +
                ", dob=" + dob +
                ", doj=" + doj +
                ", password='" + password + '\'' +
                '}';
    }

    public static User verifyLogin(int id, String password) {
        // TODO: implement with file handling later
        return null;
    }

    public static boolean isUserExist(int id) {
        // TODO: implement with file handling later
        return false;
    }
}
