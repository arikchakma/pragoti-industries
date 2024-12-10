package pragoti.controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import pragoti.users.User;
import pragoti.utils.ActivityLogger;

import java.time.LocalDate;
import java.util.ArrayList;

public class UserActivityViewController {
    @javafx.fxml.FXML
    private ComboBox<String> selectUserComboBox;
    @javafx.fxml.FXML
    private TableColumn<ActivityLogger, Integer> userIdTableColumn;
    @javafx.fxml.FXML
    private TableView<ActivityLogger> activityLogTableView;
    @javafx.fxml.FXML
    private TableColumn<ActivityLogger, String> activityTableColumn;
    @javafx.fxml.FXML
    private TableColumn<ActivityLogger, LocalDate> dateTableColumn;

    @javafx.fxml.FXML
    public void initialize() {
        selectUserComboBox.getItems().clear();
        ArrayList<User> users = User.getAllUsers();
        for (User user : users) {
            String key = user.getName() + " (" + user.getDesignation() + ")" + " -- " + user.getId();
            selectUserComboBox.getItems().add(key);
        }

        userIdTableColumn.setCellValueFactory(new PropertyValueFactory<ActivityLogger, Integer>("userId"));
        activityTableColumn.setCellValueFactory(new PropertyValueFactory<ActivityLogger, String>("activity"));
        dateTableColumn.setCellValueFactory(new PropertyValueFactory<ActivityLogger, LocalDate>("date"));
    }

    @javafx.fxml.FXML
    public void checkLogsOnAction(ActionEvent actionEvent) {
        String selectedUser = selectUserComboBox.getValue();
        Alert alert = new Alert(Alert.AlertType.ERROR);
        if (selectedUser == null) {
            alert.setTitle("Error");
            alert.setHeaderText("Please select a user");
            alert.showAndWait();
            return;
        }

        String[] parts = selectedUser.split(" -- ");
        Integer userId = Integer.parseInt(parts[1]);
        ArrayList<ActivityLogger> userActivityLogs = ActivityLogger.getUserActivityLogs(userId);
//        System.out.println(userActivityLogs);
//        activityLogTableView.getItems().clear();
//        activityLogTableView.getItems().addAll(userActivityLogs);

        for (ActivityLogger activityLogger : userActivityLogs) {
            activityLogTableView.getItems().add(activityLogger);
        }
    }

}