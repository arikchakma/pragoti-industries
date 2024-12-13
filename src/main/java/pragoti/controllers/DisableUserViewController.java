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
    private Admin admin;

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

    public void setAdmin(Admin admin) {
        this.admin = admin;
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

        Alert alert = new Alert(Alert.AlertType.ERROR);
        if (id == admin.getId()) {
            alert.setTitle("Error");
            alert.setHeaderText("Cannot disable yourself");
            alert.setContentText("You cannot disable yourself");
            alert.showAndWait();
            return;
        }

        User user = User.getUser(id);
        if (user == null) {
            alert.setTitle("Error");
            alert.setHeaderText("User not found");
            alert.setContentText("User with id " + id + " not found");
            alert.showAndWait();
            return;
        }

        user.setStatus("inactive");
        if(!Admin.updateAndSaveUser(user)) {
            alert.setTitle("Error");
            alert.setHeaderText("Failed to disable user");
            alert.showAndWait();
            return;
        }

        alert.setAlertType(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("User disabled");
        alert.setContentText("User with id " + id + " disabled");
        alert.showAndWait();
        refreshSelectUserComboBox();
    }
}