package pragoti.controllers;

import javafx.event.ActionEvent;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import pragoti.shared.Vehicle;

import java.util.ArrayList;

public class VehicleDistributionViewController {
    @javafx.fxml.FXML
    private BarChart<String, Number> statusDistributionBarChart;
    @javafx.fxml.FXML
    private ComboBox<String> selectBrandComboBox;

    @javafx.fxml.FXML
    public void initialize() {
        String[] brands = {"Mitsubishi", "Mahindra & Mahindra", "Tata Motors", "Ashok Leyland", "Vauxhall", "Bedford"};
        selectBrandComboBox.getItems().addAll(brands);

        statusDistributionBarChart.setAnimated(false);
        statusDistributionBarChart.getData().add(getStatusSeries("Available", 0, 0, 0, 0));
        statusDistributionBarChart.getData().add(getStatusSeries("Scheduled", 0, 0, 0, 0));
        statusDistributionBarChart.getData().add(getStatusSeries("Dispatched", 0, 0, 0, 0));
        statusDistributionBarChart.getData().add(getStatusSeries("Delivered", 0, 0, 0, 0));
    }

    @javafx.fxml.FXML
    public void checkStatusDistributionOnAction(ActionEvent actionEvent) {
        String selectedBrand = selectBrandComboBox.getValue();
        Alert alert = new Alert(Alert.AlertType.ERROR);
        if (selectedBrand == null) {
            alert.setContentText("Please select a brand");
            alert.showAndWait();
            return;
        }

        statusDistributionBarChart.getData().clear();
        ArrayList<Vehicle> vehicles = Vehicle.getAllVehicles();
        ArrayList<Vehicle> brandVehicles = new ArrayList<>();
        for (Vehicle vehicle : vehicles) {
            if (vehicle.getBrand().equals(selectedBrand)) {
                brandVehicles.add(vehicle);
            }
        }

        ArrayList<String> availableModels = new ArrayList<>();
        for (Vehicle vehicle : brandVehicles) {
            if (!availableModels.contains(vehicle.getModel())) {
                availableModels.add(vehicle.getModel());
            }
        }

        for (String model : availableModels) {
            int availableCount = 0;
            int scheduledCount = 0;
            int dispatchedCount = 0;
            int deliveredCount = 0;

            for (Vehicle vehicle : brandVehicles) {
                if (vehicle.getModel().equals(model)) {
                    if (vehicle.getStatus().equals("Available")) {
                        availableCount++;
                    } else if (vehicle.getStatus().equals("Scheduled")) {
                        scheduledCount++;
                    } else if (vehicle.getStatus().equals("Dispatched")) {
                        dispatchedCount++;
                    } else if (vehicle.getStatus().equals("Delivered")) {
                        deliveredCount++;
                    }
                }
            }

            XYChart.Series<String, Number> series = getStatusSeries(model, availableCount, scheduledCount, dispatchedCount, deliveredCount);
            statusDistributionBarChart.getData().add(series);
        }
    }

    private XYChart.Series<String, Number> getStatusSeries(String status, int availableCount, int scheduledCount, int dispatchedCount, int deliveredCount) {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName(status);
        series.getData().add(new XYChart.Data<String, Number>("Available", availableCount));
        series.getData().add(new XYChart.Data<String, Number>("Scheduled", scheduledCount));
        series.getData().add(new XYChart.Data<String, Number>("Dispatched", dispatchedCount));
        series.getData().add(new XYChart.Data<String, Number>("Delivered", deliveredCount));
        return series;
    }
}