package pragoti.controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import pragoti.users.Admin;
import pragoti.users.User;

import java.util.ArrayList;

public class DisableUserViewController {
    @javafx.fxml.FXML
    private ComboBox<String> selectUserComboBox;

    @javafx.fxml.FXML
    public void initialize() {
        refreshSelectUserComboBox();
    }

    private void refreshSelectUserComboBox() {
        selectUserComboBox.getItems().clear();
        ArrayList<User> users = User.getAllUsers();
        for (User user : users) {
            if (user.getStatus().equals("active")) {
                String key = user.getName() + " (" + user.getDesignation() + ")" + " -- " + user.getId();
                selectUserComboBox.getItems().add(key);
            }
        }
    }

    @javafx.fxml.FXML
    public void disableUserOnAction(ActionEvent actionEvent) {
        String selectedUser = selectUserComboBox.getValue();
        if (selectedUser == null) {
            System.out.println("No user selected");
            return;
        }

        String[] parts = selectedUser.split(" -- ");
        int id = Integer.parseInt(parts[1]);

        User user = User.getUser(id);
        Alert alert = new Alert(Alert.AlertType.ERROR);
        if (user == null) {
            alert.setTitle("Error");
            alert.setHeaderText("User not found");
            alert.setContentText("User with id " + id + " not found");
            alert.showAndWait();
            return;
        }

        user.setStatus("inactive");
        Admin.updateAndSaveUser(user);

        alert.setAlertType(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("User disabled");
        alert.setContentText("User with id " + id + " disabled");
        alert.showAndWait();
        refreshSelectUserComboBox();
    }
}