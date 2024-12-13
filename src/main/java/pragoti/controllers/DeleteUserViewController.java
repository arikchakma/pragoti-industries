package pragoti.controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import pragoti.users.Admin;
import pragoti.users.User;

import java.util.ArrayList;

public class DeleteUserViewController {
    @javafx.fxml.FXML
    private ComboBox<String> selectUserComboBox;

    private Admin admin;

    @javafx.fxml.FXML
    public void initialize() {
        refreshSelectUserComboBox();
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    @javafx.fxml.FXML
    public void deleteUserOnAction(ActionEvent actionEvent) {
        String selectedUser = selectUserComboBox.getValue();
        String[] parts = selectedUser.split(" -- ");
        int id = Integer.parseInt(parts[1]);

        if (id == admin.getId()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Cannot delete yourself");
            alert.setContentText("You cannot delete yourself");
            alert.showAndWait();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.ERROR);
        if (!Admin.deleteUser(id)) {
            alert.setTitle("Error");
            alert.setHeaderText("Failed to delete user");
            alert.showAndWait();
            return;
        }

        refreshSelectUserComboBox();

        alert.setAlertType(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("User deleted");
        alert.setContentText("User with id: " + id + " has been deleted");
        alert.showAndWait();
    }

    private void refreshSelectUserComboBox() {
        selectUserComboBox.getItems().clear();
        ArrayList<User> users = User.getAllUsers();
        for (User user : users) {
            String key = user.getName() + " (" + user.getDesignation() + ")" + " -- " + user.getId();
            selectUserComboBox.getItems().add(key);
        }
    }
}