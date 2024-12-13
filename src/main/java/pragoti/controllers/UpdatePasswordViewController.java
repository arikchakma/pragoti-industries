package pragoti.controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import pragoti.shared.ValidationError;
import pragoti.users.LogisticOfficer;

public class UpdatePasswordViewController
{
    @javafx.fxml.FXML
    private TextField currentPasswordTextField;
    @javafx.fxml.FXML
    private TextField newPasswordTextField;
    @javafx.fxml.FXML
    private TextField confirmNewPasswordTextField;

    private LogisticOfficer logisticOfficer;

    @javafx.fxml.FXML
    public void initialize() {
    }

    @javafx.fxml.FXML
    public void updatePasswordOnAction(ActionEvent actionEvent) {
        String currentPassword = currentPasswordTextField.getText();
        String newPassword = newPasswordTextField.getText();
        String confirmNewPassword = confirmNewPasswordTextField.getText();

        Alert alert = new Alert(Alert.AlertType.ERROR);
        if (!newPassword.equals(confirmNewPassword)) {
            alert.setContentText("New password and confirm new password do not match");
            alert.showAndWait();
            return;
        }

        try {
            logisticOfficer.validateUpdatePassword(currentPassword, newPassword);
        } catch (ValidationError e) {
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            return;
        }

        logisticOfficer.updatePassword(newPassword);
        alert.setAlertType(Alert.AlertType.INFORMATION);
        alert.setContentText("Password updated successfully");
        alert.showAndWait();
        currentPasswordTextField.clear();
        newPasswordTextField.clear();
        confirmNewPasswordTextField.clear();
    }

    public void setLogisticOfficer(LogisticOfficer logisticOfficer) {
        this.logisticOfficer = logisticOfficer;
    }
}