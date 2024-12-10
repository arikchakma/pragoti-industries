package pragoti.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import pragoti.MainApplication;
import pragoti.users.*;
import pragoti.utils.ActivityLogger;

public class LayoutViewController {
    @javafx.fxml.FXML
    private Menu userNameAndRoleMenu;
    private User currentUser;
    @javafx.fxml.FXML
    private Menu adminMenu;
    @javafx.fxml.FXML
    private Menu logisticOfficerMenu;
    @javafx.fxml.FXML
    private BorderPane layoutBorderPane;

    @javafx.fxml.FXML
    public void initialize() {
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
        userNameAndRoleMenu.setText(user.getName() + " (" + user.getDesignation() + ")");
        String designation = user.getDesignation();
        if (designation.equals("Admin")) {
            adminMenu.setVisible(true);
            logisticOfficerMenu.setVisible(false);
        } else if (designation.equals("Logistic Officer")) {
            adminMenu.setVisible(false);
            logisticOfficerMenu.setVisible(true);
        } else {
            adminMenu.setVisible(false);
            logisticOfficerMenu.setVisible(false);
        }
    }

    @Deprecated
    public void supplierOrdersOnAction(ActionEvent actionEvent) {
    }

    @Deprecated
    public void generateInventoryReportOnAction(ActionEvent actionEvent) {
    }

    @Deprecated
    public void updateInventoryRecordOnAction(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void generateTransportationOrderDetailsOnAction(ActionEvent actionEvent) {
    }

    @Deprecated
    public void newInventoryOnAction(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void updateVehicleStatusOnAction(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void createUserOnAction(ActionEvent actionEvent) {
        switchToScene("CreateUserView.fxml");
    }

    @Deprecated
    public void generateReport(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void checkAlertsOnAction(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void assignDriverOnAction(ActionEvent actionEvent) {
    }

    @Deprecated
    public void stockThresholdOnAction(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void manageTransportationRequestsOnAction(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void monitorVehicleMovementOnAction(ActionEvent actionEvent) {
    }

    @Deprecated
    public void materialRequestsOnAction(ActionEvent actionEvent) {
    }

    @Deprecated
    public void viewStocksOnAction(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void scheduleVehicleDispatchOnAction(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void availableVehiclesOnAction(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void signOutOnAction(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(
                    MainApplication.class.getResource("LoginView.fxml")
            );
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = (Stage) layoutBorderPane.getScene().getWindow();
            stage.setScene(scene);

            ActivityLogger.log("Logged out", currentUser.getId());
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @javafx.fxml.FXML
    public void viewUserActivityOnAction(ActionEvent actionEvent) {
        switchToScene("UserActivityView.fxml");
    }

    @javafx.fxml.FXML
    public void reactiveDisabledUserOnAction(ActionEvent actionEvent) {
        switchToScene("ReactiveUserView.fxml");
    }

    @javafx.fxml.FXML
    public void analyzeGenderDistributionOnAction(ActionEvent actionEvent) {
        switchToScene("GenderDistributionView.fxml");
    }

    @javafx.fxml.FXML
    public void deleteUserOnAction(ActionEvent actionEvent) {
        switchToScene("DeleteUserView.fxml");
    }

    @javafx.fxml.FXML
    public void employeeActiveInactiveDistributionOnAction(ActionEvent actionEvent) {
        switchToScene("EmployeeActiveInactiveDistributionView.fxml");
    }

    @javafx.fxml.FXML
    public void updateUserOnAction(ActionEvent actionEvent) {
        switchToScene("UpdateUserInformationView.fxml");
    }

    @javafx.fxml.FXML
    public void disableUserOnAction(ActionEvent actionEvent) {
        switchToScene("DisableUserView.fxml");
    }

    protected void switchToScene(String fxmlFileName) {
        try {
            FXMLLoader fxmlLoader = new javafx.fxml.FXMLLoader(
                    MainApplication.class.getResource(fxmlFileName)
            );

            ActivityLogger.log("Switched to " + fxmlFileName.replace(".fxml", ""), currentUser.getId());
            layoutBorderPane.setCenter(fxmlLoader.load());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}