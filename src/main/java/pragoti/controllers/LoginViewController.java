package pragoti.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pragoti.MainApplication;
import pragoti.users.Admin;
import pragoti.users.User;
import pragoti.utils.ActivityLogger;

public class LoginViewController {
    @javafx.fxml.FXML
    private TextField passwordTextField;
    @javafx.fxml.FXML
    private TextField userIdTextField;

    @javafx.fxml.FXML
    public void initialize() {
    }

    @javafx.fxml.FXML
    public void loginOnAction(ActionEvent actionEvent) {
        String userIdText = userIdTextField.getText();
        Alert alert = new Alert(Alert.AlertType.ERROR);
        if (userIdText.length() < 4) {
            alert.setTitle("Error");
            alert.setHeaderText("Incorrect user id");
            alert.setContentText("User id must be at least 4 digits");
            alert.showAndWait();
            return;
        }

        int userId = Integer.parseInt(userIdText);

        String password = passwordTextField.getText();

        if(!User.validatePassword(password)){
            alert.setTitle("Error");
            alert.setHeaderText("Incorrect password");
            alert.setContentText("Password must be at least 8 characters long and contain at least one digit, and one special character");
            alert.showAndWait();
            return;
        }

        User user = User.verifyLogin(userId, password);
        if (user == null) {
            alert.setTitle("Error");
            alert.setHeaderText("Incorrect password or user id");
            alert.setContentText("Incorrect password or user id for user: " + userId);
            alert.showAndWait();
            return;
        }

        // password is correct
        // show the user's dashboard
        Scene scene = ((Button) actionEvent.getSource()).getScene();
        Stage stage = (Stage) scene.getWindow();

        try {
            FXMLLoader dashboardFxmlLoader = new FXMLLoader(
                    MainApplication.class.getResource("LayoutView.fxml")
            );

            Scene dashboardScene = new Scene(dashboardFxmlLoader.load());
            stage.setScene(dashboardScene);
            LayoutViewController layoutViewController = (LayoutViewController) dashboardFxmlLoader.getController();
            layoutViewController.setCurrentUser(user);
            ActivityLogger.log("Logged in", userId);
        } catch (Exception e) {
        }
    }
}