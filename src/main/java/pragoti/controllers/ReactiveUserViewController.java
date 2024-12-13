package pragoti.controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import pragoti.users.Admin;
import pragoti.users.User;

import java.util.ArrayList;

public class ReactiveUserViewController {
    @javafx.fxml.FXML
    private ComboBox<String> selectUserComboBox;

    @javafx.fxml.FXML
    public void initialize() {
        refreshSelectUserComboBox();
    }

    @javafx.fxml.FXML
    public void reactiveUserOnAction(ActionEvent actionEvent) {
        String selectedUser = selectUserComboBox.getValue();
        String[] parts = selectedUser.split(" -- ");
        int id = Integer.parseInt(parts[1]);
        User user = User.getUser(id);
        Alert alert = new Alert(Alert.AlertType.ERROR);
        if (user == null) {
            alert.setTitle("Error");
            alert.setHeaderText("Incorrect user id");
            alert.setContentText("User with id " + id + " not found");
            alert.showAndWait();
            return;
        }

        user.setStatus("active");
        if (!Admin.updateAndSaveUser(user)) {
            alert.setTitle("Error");
            alert.setHeaderText("Failed to reactivate user");
            alert.setContentText("Failed to reactivate user with id " + id);
            alert.showAndWait();
            return;
        }
        refreshSelectUserComboBox();

        alert.setAlertType(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("User reactivated");
        alert.setContentText("User with id " + id + " reactivated");
        alert.showAndWait();
    }

    private void refreshSelectUserComboBox() {
        selectUserComboBox.getItems().clear();
        ArrayList<User> users = User.getAllUsers();
        for (User user : users) {
            if (user.getStatus().equals("inactive")) {
                String key = user.getName() + " (" + user.getDesignation() + ")" + " -- " + user.getId();
                selectUserComboBox.getItems().add(key);
            }
        }
    }
}