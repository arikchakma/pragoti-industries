package pragoti.controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import pragoti.shared.Driver;
import pragoti.shared.ValidationError;
import pragoti.users.LogisticOfficer;

import java.util.ArrayList;

public class DeleteDriverViewController {
    @javafx.fxml.FXML
    private ComboBox<String> selectDriverComboBox;

    private LogisticOfficer logisticOfficer;

    @javafx.fxml.FXML
    public void initialize() {
        refreshSelectDriverComboBox();
    }

    @javafx.fxml.FXML
    public void removeDriverOnAction(ActionEvent actionEvent) {
        String selectedDriver = selectDriverComboBox.getValue();
        Alert alert = new Alert(Alert.AlertType.ERROR);
        if (selectedDriver == null) {
            alert.setContentText("Please select a driver");
            alert.showAndWait();
            return;
        }

        String[] parts = selectedDriver.split(" -- ");
        int driverId = Integer.parseInt(parts[1]);
        Driver driver = Driver.getDriver(driverId);
        if (driver == null) {
            alert.setContentText("Driver not found");
            alert.showAndWait();
            return;
        }

        try {
            logisticOfficer.deleteDriver(driver.getId());
        } catch (ValidationError e) {
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            return;
        }

        refreshSelectDriverComboBox();
        alert.setAlertType(Alert.AlertType.INFORMATION);
        alert.setContentText("Driver removed successfully");
        alert.showAndWait();
    }

    public void refreshSelectDriverComboBox() {
        selectDriverComboBox.getItems().clear();
        ArrayList<Driver> drivers = Driver.getAllDrivers();
        for (Driver driver : drivers) {
            selectDriverComboBox.getItems().add(driver.getName() + " -- " + driver.getId());
        }
    }

    public void setLogisticOfficer(LogisticOfficer logisticOfficer) {
        this.logisticOfficer = logisticOfficer;
    }
}