package pragoti.controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import pragoti.users.Admin;
import pragoti.users.User;

import java.util.ArrayList;

public class DeleteUserViewController
{
    @javafx.fxml.FXML
    private ComboBox<String> selectUserComboBox;

    @javafx.fxml.FXML
    public void initialize() {
        refreshSelectUserComboBox();
    }

    @javafx.fxml.FXML
    public void deleteUserOnAction(ActionEvent actionEvent) {
        String selectedUser = selectUserComboBox.getValue();
        String[] parts = selectedUser.split(" -- ");
        int id = Integer.parseInt(parts[1]);

        Admin.deleteUser(id);
        refreshSelectUserComboBox();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
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