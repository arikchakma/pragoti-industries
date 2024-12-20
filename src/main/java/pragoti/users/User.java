package pragoti.users;

import pragoti.utils.FileHandler;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

public abstract class User implements Serializable {
    protected int id;
    protected String name, gender, email, designation;
    // the status can be either "active" or "inactive"
    // by default, the status is "active"
    protected String status;

    protected LocalDate dob, doj;
    private String password;
    protected float salary;

    public User() {
    }

    public User(int id, String name, String gender, String email, String designation, LocalDate dob, LocalDate doj, String password, float salary) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.email = email;
        this.designation = designation;
        this.dob = dob;
        this.doj = doj;
        this.password = password;
        this.salary = salary;

        // by default, the status is "active"
        this.status = "active";
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

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public float getSalary() {
        return salary;
    }

    public void setSalary(float salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", email='" + email + '\'' +
                ", designation='" + designation + '\'' +
                ", status='" + status + '\'' +
                ", dob=" + dob +
                ", doj=" + doj +
                ", password='" + password + '\'' +
                ", salary=" + salary +
                '}';
    }

    public static User verifyLogin(int id, String password) {
        User user = getUser(id);
        if (user != null && user.getPassword().equals(password) && user.getStatus().equals("active")) {
            return user;
        }

        return null;
    }

    public static boolean isUserExist(int id) {
        ArrayList<User> users = getAllUsers();
        if (users == null) {
            return false;
        }

        for (User user : users) {
            if (user.getId() == id) {
                return true;
            }
        }

        return false;
    }

    public static ArrayList<User> getAllUsers() {
        return FileHandler.<User>readObjectsFromFile("users.bin");
    }

    public static User getUser(int id) {
        ArrayList<User> users = getAllUsers();
        if (users == null) {
            return null;
        }

        for (User user : users) {
            if (user.getId() == id) {
                return user;
            }
        }

        return null;
    }

    public static boolean validatePassword(String password) {
        String[] specialCharacters = {"!", "@", "#", "$", "%", "^", "&", "*", "(", ")", "_", "+", "-", "=", "{", "}", "[", "]", ":", ";", "'", "\"", "<", ">", ",", ".", "?", "/", "|", "\\", "~", "`"};
        String[] numbers = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
        boolean hasSpecialCharacter = false;
        boolean hasNumber = false;
        String[] passwordCharacters = password.split("");
        for (String character : passwordCharacters) {
            for (String specialCharacter : specialCharacters) {
                if (character.equals(specialCharacter)) {
                    hasSpecialCharacter = true;
                    break;
                }
            }
            for (String number : numbers) {
                if (character.equals(number)) {
                    hasNumber = true;
                    break;
                }
            }
        }

        return hasSpecialCharacter && hasNumber && password.length() >= 8;
    }
}
