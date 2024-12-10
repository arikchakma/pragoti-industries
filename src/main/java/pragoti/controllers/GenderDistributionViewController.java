package pragoti.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import pragoti.users.User;

import java.util.ArrayList;

public class GenderDistributionViewController
{
    @javafx.fxml.FXML
    private PieChart genderDistributionPieChart;

    @javafx.fxml.FXML
    public void initialize() {
    }

    @javafx.fxml.FXML
    public void showGenderDistributionPieChartOnAction(ActionEvent actionEvent) {
        int maleCount = 0;
        int femaleCount = 0;
        int otherCount = 0;

        ArrayList<User> users = User.getAllUsers();
        for (User user : users) {
            String gender = user.getGender();
            if(gender.equals("Male")){
                maleCount++;
            } else if (gender.equals("Female")){
                femaleCount++;
            } else {
                otherCount++;
            }
        }

        genderDistributionPieChart.getData().clear();
        genderDistributionPieChart.getData().
                addAll(new PieChart.Data("Male", maleCount),
                        new PieChart.Data("Female", femaleCount),
                        new PieChart.Data("Other", otherCount));

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        for (PieChart.Data data : genderDistributionPieChart.getData()) {
            data.getNode().addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    alert.setContentText("Number of employees " + data.getName() + " count is: " + data.getPieValue());
                    alert.showAndWait();
                }
            });
        }
    }
}