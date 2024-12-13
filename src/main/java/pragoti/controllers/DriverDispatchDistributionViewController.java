package pragoti.controllers;

import javafx.event.ActionEvent;
import javafx.scene.chart.PieChart;
import javafx.scene.control.ComboBox;
import pragoti.shared.DispatchInfo;
import pragoti.shared.Driver;

import java.util.ArrayList;

public class DriverDispatchDistributionViewController {
    @javafx.fxml.FXML
    private ComboBox<String> selectDriverComboBox;
    @javafx.fxml.FXML
    private PieChart driverDistributionPieChart;

    @javafx.fxml.FXML
    public void initialize() {
        ArrayList<Driver> drivers = Driver.getAllDrivers();
        for (Driver driver : drivers) {
            selectDriverComboBox.getItems().add(driver.getName() + " -- " + driver.getId());
        }
    }

    @javafx.fxml.FXML
    public void checkStatusDistributionOnAction(ActionEvent actionEvent) {
        String selectedDriver = selectDriverComboBox.getValue();
        if (selectedDriver == null) {
            return;
        }

        String[] parts = selectedDriver.split(" -- ");
        int driverId = Integer.parseInt(parts[1]);
        Driver driver = Driver.getDriver(driverId);
        if (driver == null) {
            return;
        }

        ArrayList<DispatchInfo> dispatchInfos = driver.getDriverDispatchInfo();

        int scheduled = 0;
        int dispatched = 0;
        int delivered = 0;
        for (DispatchInfo dispatchInfo : dispatchInfos) {
            if (dispatchInfo.getStatus().equals("Scheduled")) {
                scheduled++;
            } else if (dispatchInfo.getStatus().equals("Dispatched")) {
                dispatched++;
            } else if (dispatchInfo.getStatus().equals("Delivered")) {
                delivered++;
            }
        }

        driverDistributionPieChart.getData().clear();
        driverDistributionPieChart.getData().add(new PieChart.Data("Scheduled", scheduled));
        driverDistributionPieChart.getData().add(new PieChart.Data("Dispatched", dispatched));
        driverDistributionPieChart.getData().add(new PieChart.Data("Delivered", delivered));
    }
}