package pragoti.controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import pragoti.shared.LeaveInformation;
import pragoti.shared.ValidationError;
import pragoti.users.LogisticOfficer;

import java.io.File;
import java.time.LocalDate;

public class ApplyForLeaveViewController {
    @javafx.fxml.FXML
    private DatePicker endDatePicker;
    @javafx.fxml.FXML
    private ComboBox<String> typeComboBox;
    @javafx.fxml.FXML
    private DatePicker startDatePicker;
    @javafx.fxml.FXML
    private TextArea reasonTextArea;

    private LogisticOfficer logisticOfficer;
    private File selectedFile;
    @javafx.fxml.FXML
    private Button selectFileButton;


    @javafx.fxml.FXML
    public void initialize() {
        typeComboBox.getItems().addAll("Casual", "Medical", "Maternity", "Paternity", "Earned");
    }

    @javafx.fxml.FXML
    public void validateAndApplyLeaveOnAction(ActionEvent actionEvent) {
        LocalDate startDate = startDatePicker.getValue();
        LocalDate endDate = endDatePicker.getValue();
        String type = typeComboBox.getValue();
        String reason = reasonTextArea.getText();

        Alert alert = new Alert(Alert.AlertType.ERROR);
        if (startDate == null || endDate == null || type == null || reason.isEmpty() || selectedFile == null) {
            alert.setTitle("Error");
            alert.setHeaderText("All fields are required");
            alert.showAndWait();
            return;
        }

        try {
            logisticOfficer.validateApplyLeave(reason, type, startDate, endDate);
        } catch (ValidationError error) {
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.setContentText(error.getMessage());
            alert.showAndWait();
            return;
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        String fileName = selectedFile.getName();
        boolean success = logisticOfficer.applyForLeave(reason, type, startDate, endDate, fileName);
        if (!success) {
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.setContentText("Failed to apply for leave");
            alert.showAndWait();
            return;
        }

        alert.setAlertType(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Leave applied successfully");
        alert.showAndWait();
    }

    public void setLogisticOfficer(LogisticOfficer logisticOfficer) {
        this.logisticOfficer = logisticOfficer;
    }

    @javafx.fxml.FXML
    public void selectFileOnAction(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
        fileChooser.setTitle("Select a PDF file");
        File selectedFile = fileChooser.showOpenDialog(null);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        if (selectedFile == null) {
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("No file selected");
            alert.showAndWait();
            return;
        }

        String fileName = selectedFile.getName();
        if (!fileName.endsWith(".pdf")) {
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Please select a PDF file");
            alert.showAndWait();
            return;
        }

        selectFileButton.setText(fileName);
        this.selectedFile = selectedFile;
    }
}