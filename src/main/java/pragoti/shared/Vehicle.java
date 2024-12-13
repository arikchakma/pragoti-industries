package pragoti.shared;

import pragoti.utils.FileHandler;

import java.io.Serializable;
import java.util.ArrayList;

public class Vehicle implements Serializable {
    private int id;
    private String model, brand, registrationNumber;
    // Status can be "Available", "Scheduled", "Dispatched", "In Transit", "Delivered", "Maintenance"
    private String status;

    public Vehicle() {
    }

    public Vehicle(int id,String model, String brand, String registrationNumber, float mileage, String status) {
        this.id = id;
        this.model = model;
        this.brand = brand;
        this.registrationNumber = registrationNumber;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "id=" + id +
                ", model='" + model + '\'' +
                ", brand='" + brand + '\'' +
                ", registrationNumber='" + registrationNumber + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    public static void generateVehiclesData() {
        String[] mitsubishiModels = {
                "Mitsubishi Pajero V-31",
                "Mitsubishi Pajero Sport CR-45",
                "Mitsubishi L-200 Double-Cabin Pickup"
        };

        // Mahindra & Mahindra
        String[] mahindraModels = {
                "Mahindra Scorpio S10 SUV",
                "Mahindra Scorpio Double Cab Pickup"
        };

        // Tata Motors
        String[] tataModels = {
                "Tata Buses",
                "Tata Mini Trucks"
        };

        // Ashok Leyland
        String[] ashokLeylandModels = {
                "Ashok Leyland Minibuses"
        };

        // Vauxhall
        String[] vauxhallModels = {
                "Vauxhall Viva"
        };

        // Bedford
        String[] bedfordModels = {
                "Bedford Trucks"
        };

        ArrayList<Vehicle> vehicles = getAllVehicles();
        if (!vehicles.isEmpty()) {
            System.out.println("Vehicles data already exists.");
            return;
        }

        int id = 1000;
        for (String model : mitsubishiModels) {
            vehicles.add(new Vehicle(id++, model, "Mitsubishi", "MITSUBISHI-" + model.substring(0, 3).toUpperCase() + "-" + id, 0, "Available"));
        }

        for (String model : mahindraModels) {
            vehicles.add(new Vehicle(id++, model, "Mahindra & Mahindra", "MAHINDRA-" + model.substring(0, 3).toUpperCase() + "-" + id, 0, "Available"));
        }

        for (String model : tataModels) {
            vehicles.add(new Vehicle(id++, model, "Tata Motors", "TATA-" + model.substring(0, 3).toUpperCase() + "-" + id, 0, "Available"));
        }

        for (String model : ashokLeylandModels) {
            vehicles.add(new Vehicle(id++, model, "Ashok Leyland", "ASHOK-" + model.substring(0, 3).toUpperCase() + "-" + id, 0, "Available"));
        }

        for (String model : vauxhallModels) {
            vehicles.add(new Vehicle(id++, model, "Vauxhall", "VAUXHALL-" + model.substring(0, 3).toUpperCase() + "-" + id, 0, "Available"));
        }

        for (String model : bedfordModels) {
            vehicles.add(new Vehicle(id++, model, "Bedford", "BEDFORD-" + model.substring(0, 3).toUpperCase() + "-" + id, 0, "Available"));
        }

        for (Vehicle vehicle : vehicles) {
            FileHandler.<Vehicle>writeObjectToFile(vehicle, "vehicles.bin");
        }
    }

    public static ArrayList<Vehicle> getAllVehicles() {
        return FileHandler.<Vehicle>readObjectsFromFile("vehicles.bin");
    }
}
