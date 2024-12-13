package pragoti.controllers;

import javafx.event.ActionEvent;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import pragoti.users.User;

import java.util.ArrayList;

public class EmployeeActiveInactiveDistributionViewController {

    @javafx.fxml.FXML
    private BarChart<String, Integer> employeeActiveInactiveBarChart;

    @javafx.fxml.FXML
    public void initialize() {
        employeeActiveInactiveBarChart.getData().clear();
        employeeActiveInactiveBarChart.setAnimated(false);

        // for some reason, bar chart's legend is not showing up
        // so, adding a dummy series to show the legend
        employeeActiveInactiveBarChart.getData().add(getStatusSeries("Active", 0, 0, 0, 0));
        employeeActiveInactiveBarChart.getData().add(getStatusSeries("Inactive", 0, 0, 0, 0));
    }

    @javafx.fxml.FXML
    public void showActiveInactiveDistributionBarChartOnAction(ActionEvent actionEvent) {
        int adminActive = 0;
        int adminInactive = 0;

        int logisticOfficerActive = 0;
        int logisticOfficerInactive = 0;

        int customerSupportActive = 0;
        int customerSupportInactive = 0;

        int hrActive = 0;
        int hrInactive = 0;

        ArrayList<User> users = User.getAllUsers();
        for (User user : users) {
            String designation = user.getDesignation();
            String status = user.getStatus();

            if (designation.equals("Admin")) {
                if (status.equals("active")) {
                    adminActive++;
                } else {
                    adminInactive++;
                }
            } else if (designation.equals("Logistic Officer")) {
                if (status.equals("active")) {
                    logisticOfficerActive++;
                } else {
                    logisticOfficerInactive++;
                }
            }
        }

        employeeActiveInactiveBarChart.getData().clear();
        employeeActiveInactiveBarChart.getData().add(getStatusSeries("Active", adminActive, logisticOfficerActive, customerSupportActive, hrActive));
        employeeActiveInactiveBarChart.getData().add(getStatusSeries("Inactive", adminInactive, logisticOfficerInactive, customerSupportInactive, hrInactive));
    }

    private XYChart.Series<String, Integer> getStatusSeries(String status, int adminCount, int logisticOfficerCount, int customerSupportCount, int hrCount) {
        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        series.setName(status);
        series.getData().add(new XYChart.Data<String, Integer>("Admin", adminCount));
        series.getData().add(new XYChart.Data<String, Integer>("Logistic Officer", logisticOfficerCount));
        return series;
    }

}