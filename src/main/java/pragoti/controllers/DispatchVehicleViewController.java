package pragoti.controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import pragoti.shared.Driver;
import pragoti.shared.ValidationError;
import pragoti.shared.Vehicle;
import pragoti.users.LogisticOfficer;

import java.time.LocalDate;
import java.util.ArrayList;

public class DispatchVehicleViewController {
    @javafx.fxml.FXML
    private ComboBox<String> priorityComboBox;
    @javafx.fxml.FXML
    private ComboBox<String> driverIdComboBox;
    @javafx.fxml.FXML
    private DatePicker arrivalDatePicker;
    @javafx.fxml.FXML
    private ComboBox<String> statusComboBox;
    @javafx.fxml.FXML
    private ComboBox<String> vehicleIdComboBox;
    @javafx.fxml.FXML
    private TextArea remarksTextArea;
    @javafx.fxml.FXML
    private TextField destinationField;
    @javafx.fxml.FXML
    private DatePicker dispatchDatePicker;


    private LogisticOfficer logisticOfficer;

    @javafx.fxml.FXML
    public void initialize() {
        String[] priorities = {"High", "Medium", "Low"};
        priorityComboBox.getItems().addAll(priorities);

        String[] statuses = {"Scheduled", "Dispatched", "Delivered"};
        statusComboBox.getItems().addAll(statuses);

        ArrayList<Vehicle> vehicles = Vehicle.getAllVehicles();
        for (Vehicle vehicle : vehicles) {
            if (vehicle.getStatus().equals("Available")) {
                vehicleIdComboBox.getItems().add(vehicle.getModel() + " " + vehicle.getBrand() + " -- " + vehicle.getId());
            }
        }

        ArrayList<Driver> drivers = Driver.getAllDrivers();
        for (Driver driver : drivers) {
            driverIdComboBox.getItems().add(driver.getName() + " -- " + driver.getId());
        }
    }

    @javafx.fxml.FXML
    public void submitDispatchInfo(ActionEvent actionEvent) {
        String priority = priorityComboBox.getValue();
        String driver = driverIdComboBox.getValue();
        String status = statusComboBox.getValue();
        String vehicle = vehicleIdComboBox.getValue();
        String remarks = remarksTextArea.getText();
        String destination = destinationField.getText();
        LocalDate dispatchDate = dispatchDatePicker.getValue();
        LocalDate arrivalDate = arrivalDatePicker.getValue();

        Alert alert = new Alert(Alert.AlertType.ERROR);
        if (priority.isEmpty() || driver.isEmpty() || status.isEmpty() || vehicle.isEmpty() || remarks.isEmpty() || destination.isEmpty() || logisticOfficer == null || dispatchDate == null || arrivalDate == null) {
            alert.setTitle("Error");
            alert.setHeaderText("All fields are required");
            alert.showAndWait();
            return;
        }

        String[] parts = driver.split(" -- ");
        int driverId = Integer.parseInt(parts[1]);

        parts = vehicle.split(" -- ");
        int vehicleId = Integer.parseInt(parts[1]);

        try {
            logisticOfficer.validateDispatchVehicle(vehicleId, driverId, priority, destination, dispatchDate, arrivalDate, status, remarks);
        } catch (ValidationError error) {
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.setContentText(error.getMessage());
            alert.showAndWait();
            return;
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        boolean success = logisticOfficer.dispatchVehicle(vehicleId, driverId, priority, destination, dispatchDate, arrivalDate, status, remarks);
        if (!success) {
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.setContentText("Failed to dispatch vehicle");
            alert.showAndWait();
            return;
        }

        alert.setAlertType(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Vehicle dispatched successfully");
        alert.showAndWait();
    }

    public void setLogisticOfficer(LogisticOfficer logisticOfficer) {
        this.logisticOfficer = logisticOfficer;
    }
}