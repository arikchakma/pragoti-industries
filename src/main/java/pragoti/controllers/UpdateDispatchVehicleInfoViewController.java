package pragoti.controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import pragoti.shared.DispatchInfo;
import pragoti.shared.Driver;
import pragoti.shared.ValidationError;
import pragoti.shared.Vehicle;
import pragoti.users.LogisticOfficer;

import java.time.LocalDate;
import java.util.ArrayList;

public class UpdateDispatchVehicleInfoViewController {
    @javafx.fxml.FXML
    private ComboBox<String> priorityComboBox;
    @javafx.fxml.FXML
    private DatePicker arrivalDatePicker;
    @javafx.fxml.FXML
    private Label vehicleIdLabel;
    @javafx.fxml.FXML
    private ComboBox<String> statusComboBox;
    @javafx.fxml.FXML
    private TextArea remarksTextArea;
    @javafx.fxml.FXML
    private TextField destinationField;
    @javafx.fxml.FXML
    private DatePicker dispatchDatePicker;
    @javafx.fxml.FXML
    private ComboBox<String> selectDispatchInfoComboBox;

    private LogisticOfficer logisticOfficer;
    @javafx.fxml.FXML
    private ComboBox<String> driverIdComboBox;

    @javafx.fxml.FXML
    public void initialize() {
        String[] priorities = {"High", "Medium", "Low"};
        priorityComboBox.getItems().addAll(priorities);

        String[] statuses = {"Scheduled", "Dispatched", "Delivered"};
        statusComboBox.getItems().addAll(statuses);


        ArrayList<Driver> drivers = Driver.getAllDrivers();
        for (Driver driver : drivers) {
            driverIdComboBox.getItems().add(driver.getName() + " -- " + driver.getId());
        }
    }

    @javafx.fxml.FXML
    public void updateAndSaveOnAction(ActionEvent actionEvent) {
        String selectedDispatchInfo = selectDispatchInfoComboBox.getValue();
        DispatchInfo dispatchInfo = DispatchInfo.getDispatchInfo(Integer.parseInt(selectedDispatchInfo));
        if (dispatchInfo == null) {
            return;
        }

        String destination = destinationField.getText();
        LocalDate dispatchDate = dispatchDatePicker.getValue();
        LocalDate arrivalDate = arrivalDatePicker.getValue();
        String priority = priorityComboBox.getValue();
        String status = statusComboBox.getValue();
        String remarks = remarksTextArea.getText();

        Alert alert = new Alert(Alert.AlertType.ERROR);
        if (destination.isEmpty() || dispatchDate == null || arrivalDate == null || priority.isEmpty() || status.isEmpty()) {
            alert.setTitle("Error");
            alert.setHeaderText("All fields are required");
            alert.setContentText("All fields are required. Please fill all fields");
            alert.showAndWait();
            return;
        }

        if (dispatchDate.isAfter(arrivalDate)) {
            alert.setTitle("Error");
            alert.setHeaderText("Invalid Date");
            alert.setContentText("Dispatch date cannot be after arrival date");
            alert.showAndWait();
            return;
        }

        // if driver has been changed
        String selectedDriver = driverIdComboBox.getValue();
        String[] parts = selectedDriver.split(" -- ");
        int driverId = Integer.parseInt(parts[1]);
        if (driverId != dispatchInfo.getDriverId()) {
            try {
                logisticOfficer.validateDispatchVehicle(dispatchInfo.getVehicleId(), driverId, priority, destination, dispatchDate, arrivalDate, status, remarks);
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
        }

        dispatchInfo.setDestination(destination);
        dispatchInfo.setDispatchDate(dispatchDate);
        dispatchInfo.setEstimatedArrivalDate(arrivalDate);
        dispatchInfo.setPriority(priority);
        dispatchInfo.setStatus(status);
        dispatchInfo.setRemarks(remarks);

        boolean success = dispatchInfo.updateAndSaveToFile();
        if (!success) {
            alert.setTitle("Error");
            alert.setHeaderText("Failed to update dispatch info");
            alert.setContentText("Failed to update dispatch info. Please try again");
            alert.showAndWait();
            return;
        }

        alert.setAlertType(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Dispatch info updated");
        alert.setContentText("Dispatch info updated successfully");
        alert.showAndWait();
    }

    @javafx.fxml.FXML
    public void selectDispatchInfoOnAction(ActionEvent actionEvent) {
        String selectedDispatchInfo = selectDispatchInfoComboBox.getValue();
        DispatchInfo dispatchInfo = DispatchInfo.getDispatchInfo(Integer.parseInt(selectedDispatchInfo));
        if (dispatchInfo == null) {
            return;
        }

        int vehicleId = dispatchInfo.getVehicleId();
        int driverId = dispatchInfo.getDriverId();

        Vehicle vehicle = Vehicle.getVehicle(vehicleId);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        if (vehicle == null) {
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Vehicle not found");
            alert.setContentText("Vehicle with id " + vehicleId + " not found");
            alert.showAndWait();
            return;
        }

        Driver driver = Driver.getDriver(driverId);
        if (driver == null) {
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Driver not found");
            alert.setContentText("Driver with id " + driverId + " not found");
            alert.showAndWait();
            return;
        }


        vehicleIdLabel.setText(vehicle.getModel() + " " + vehicle.getBrand() + " -- " + vehicle.getId());

        driverIdComboBox.setValue(driver.getName() + " -- " + driver.getId());
        destinationField.setText(dispatchInfo.getDestination());
        dispatchDatePicker.setValue(dispatchInfo.getDispatchDate());
        arrivalDatePicker.setValue(dispatchInfo.getEstimatedArrivalDate());
        priorityComboBox.setValue(dispatchInfo.getPriority());
        statusComboBox.setValue(dispatchInfo.getStatus());
        remarksTextArea.setText(dispatchInfo.getRemarks());
    }

    public void setLogisticOfficer(LogisticOfficer logisticOfficer) {
        this.logisticOfficer = logisticOfficer;

        ArrayList<DispatchInfo> dispatchInfos = DispatchInfo.getAllDispatchInfo();
        for (DispatchInfo dispatchInfo : dispatchInfos) {
            if (dispatchInfo.getDispatcherUserId() == logisticOfficer.getId() && !dispatchInfo.getStatus().equals("Delivered") && !dispatchInfo.getStatus().equals("Cancelled")) {
                selectDispatchInfoComboBox.getItems().add(Integer.toString(dispatchInfo.getId()));
            }
        }

    }
}