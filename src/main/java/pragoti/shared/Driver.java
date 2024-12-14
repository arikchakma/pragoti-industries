package pragoti.shared;

import pragoti.utils.FileHandler;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

public class Driver implements Serializable {
    protected int id;
    protected String name, gender;
    protected String licenseNumber;
    protected LocalDate licenseExpiryDate, dob;
    float salary;

    public Driver() {
    }

    public Driver(int id, String name, String gender, String licenseNumber, LocalDate licenseExpiryDate, LocalDate dob, float salary) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.licenseNumber = licenseNumber;
        this.licenseExpiryDate = licenseExpiryDate;
        this.dob = dob;
        this.salary = salary;
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

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public LocalDate getLicenseExpiryDate() {
        return licenseExpiryDate;
    }

    public void setLicenseExpiryDate(LocalDate licenseExpiryDate) {
        this.licenseExpiryDate = licenseExpiryDate;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public float getSalary() {
        return salary;
    }

    public void setSalary(float salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Driver{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", licenseNumber='" + licenseNumber + '\'' +
                ", licenseExpiryDate=" + licenseExpiryDate +
                ", dob=" + dob +
                ", salary=" + salary +
                '}';
    }

    public static ArrayList<Driver> getAllDrivers() {
        return FileHandler.<Driver>readObjectsFromFile("drivers.bin");
    }

    public static Driver getDriver(int id) {
        ArrayList<Driver> drivers = getAllDrivers();
        if (drivers == null) {
            return null;
        }

        for (Driver driver : drivers) {
            if (driver.getId() == id) {
                return driver;
            }
        }

        return null;
    }

    public static boolean deleteDriver(int id) {
        ArrayList<Driver> drivers = getAllDrivers();
        if (drivers == null) {
            return false;
        }

        drivers.removeIf(driver -> driver.getId() == id);
        FileHandler.deleteFile("drivers.bin");
        return FileHandler.<Driver>replaceFile(drivers, "drivers.bin");
    }

    public ArrayList<DispatchInfo> getDriverDispatchInfo() {
        ArrayList<DispatchInfo> dispatchInfos = DispatchInfo.getAllDispatchInfo();
        ArrayList<DispatchInfo> driverDispatchInfo = new ArrayList<>();
        for (DispatchInfo dispatchInfo : dispatchInfos) {
            if (dispatchInfo.getDriverId() == this.id) {
                driverDispatchInfo.add(dispatchInfo);
            }
        }

        return driverDispatchInfo;
    }

    public static void generateDriversData() {
        if (!getAllDrivers().isEmpty()) {
            System.out.println("Drivers data already exists.");
            return;
        }

        String[][] driverData = {
                {"3000", "John Doe", "Male", "1990-01-01", "50000", "DL123456", "2025-01-01"},
                {"3001", "Jane Smith", "Female", "1988-03-15", "55000", "DL654321", "2027-06-01"},
                {"3002", "Ahmed Ali", "Male", "1985-07-22", "60000", "DL987654", "2026-09-15"},
                {"3003", "Maria Khan", "Female", "1992-11-10", "48000", "DL456789", "2025-03-20"},
                {"3004", "Ravi Kumar", "Male", "1991-05-18", "53000", "DL789123", "2027-11-11"},
                {"3005", "Sara Begum", "Female", "1993-02-08", "47000", "DL321654", "2026-02-01"},
                {"3006", "Paul Johnson", "Male", "1987-09-30", "62000", "DL852369", "2026-08-25"},
                {"3007", "Nusrat Jahan", "Female", "1990-12-25", "51000", "DL963852", "2025-05-15"},
                {"3008", "Samuel Wilson", "Male", "1989-04-05", "57000", "DL741258", "2026-07-10"},
                {"3009", "Ayesha Rahman", "Female", "1995-06-12", "49000", "DL258963", "2026-11-05"}
        };

        for (String[] data : driverData) {
          int id = Integer.parseInt(data[0]);
            String name = data[1];
            String gender = data[2];
            LocalDate dob = LocalDate.parse(data[3]);
            float salary = Integer.parseInt(data[4]);
            String licenseNumber = data[5];
            LocalDate licenseExpiryDate = LocalDate.parse(data[6]);

            Driver d = new Driver(id, name, gender, licenseNumber, licenseExpiryDate, dob, salary);
            FileHandler.<Driver>writeObjectToFile(d, "drivers.bin");
        }
    }
}
