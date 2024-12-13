package pragoti.controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import pragoti.shared.DispatchInfo;
import pragoti.shared.Driver;
import pragoti.shared.Vehicle;

import java.util.ArrayList;

public class CancelVehicleDispatchViewController
{
    @javafx.fxml.FXML
    private ComboBox<String> selectDispatchComboBox;

    @javafx.fxml.FXML
    public void initialize() {
        selectDispatchComboBox.getItems().clear();
        refreshComboBox();
    }

    @javafx.fxml.FXML
    public void cancelVehicleDispatchOnAction(ActionEvent actionEvent) {
        String selectedDispatch = selectDispatchComboBox.getValue();
        if (selectedDispatch == null) {
            return;
        }

        String[] parts = selectedDispatch.split(" -- ");
        int dispatchId = Integer.parseInt(parts[4]);
        DispatchInfo dispatchInfo = DispatchInfo.getDispatchInfo(dispatchId);
        if (dispatchInfo == null) {
            return;
        }

        dispatchInfo.setStatus("Cancelled");
        dispatchInfo.updateAndSaveToFile();
        selectDispatchComboBox.getItems().clear();
        refreshComboBox();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Dispatch cancelled successfully");
        alert.showAndWait();
    }

    public void refreshComboBox() {
        ArrayList<DispatchInfo> dispatchInfos = DispatchInfo.getAllDispatchInfo();

        for (DispatchInfo dispatchInfo : dispatchInfos) {
            Vehicle vehicle = Vehicle.getVehicle(dispatchInfo.getVehicleId());
            Driver driver = Driver.getDriver(dispatchInfo.getDriverId());
            if (vehicle == null || driver == null || dispatchInfo.getStatus().equals("Delivered") || dispatchInfo.getStatus().equals("Cancelled")) {
                continue;
            }

            String key = vehicle.getModel() + " " + vehicle.getBrand() + " -- " + vehicle.getId() + " -- " + driver.getName() + " -- " + driver.getId() + " -- " + dispatchInfo.getId();
            selectDispatchComboBox.getItems().add(key);
        }
    }
}