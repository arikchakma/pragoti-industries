package pragoti.controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import pragoti.users.Admin;
import pragoti.users.LogisticOfficer;
import pragoti.users.User;

import java.time.LocalDate;
import java.util.ArrayList;

public class CreateUserViewController {
    @javafx.fxml.FXML
    private TextField idTextField;
    @javafx.fxml.FXML
    private TextField salaryTextField;
    @javafx.fxml.FXML
    private TextField passwordTextField;
    @javafx.fxml.FXML
    private RadioButton otherRadioButton;
    @javafx.fxml.FXML
    private ComboBox<String> designationComboBox;
    @javafx.fxml.FXML
    private DatePicker dojDatePicker;
    @javafx.fxml.FXML
    private RadioButton femaleRadioButton;
    @javafx.fxml.FXML
    private TextField emailTextField;
    @javafx.fxml.FXML
    private TextField nameTextField;
    @javafx.fxml.FXML
    private RadioButton maleRadioButton;
    @javafx.fxml.FXML
    private DatePicker dobDatePicker;

    ToggleGroup genderToggleGroup = new ToggleGroup();

    @javafx.fxml.FXML
    public void initialize() {
        femaleRadioButton.setToggleGroup(genderToggleGroup);
        maleRadioButton.setToggleGroup(genderToggleGroup);
        otherRadioButton.setToggleGroup(genderToggleGroup);

        designationComboBox.getItems().addAll("Admin", "Logistic Officer", "HR", "Customer Support");
    }

    @javafx.fxml.FXML
    public void validateAndCreateUserOnAction(ActionEvent actionEvent) {
        String name = nameTextField.getText();
        String email = emailTextField.getText();
        String password = passwordTextField.getText();
        String designation = designationComboBox.getValue();
        LocalDate dob = dobDatePicker.getValue();
        LocalDate doj = dojDatePicker.getValue();

        Alert alert = new Alert(Alert.AlertType.ERROR);
        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || designation == null) {
            alert.setTitle("Error");
            alert.setHeaderText("All fields are required");
            alert.showAndWait();
            return;
        }

        if (!email.contains("@") || !email.contains(".")) {
            alert.setTitle("Error");
            alert.setHeaderText("Invalid Email");
            alert.showAndWait();
            return;
        }

        String idString = idTextField.getText();
        if (idString.length() < 4) {
            alert.setTitle("Error");
            alert.setHeaderText("ID must be at least 4 characters long");
            alert.showAndWait();
            return;
        }

        int id = Integer.parseInt(idString);
        float salary = Float.parseFloat(salaryTextField.getText());
        if (salary < 10 * 1000) {
            alert.setTitle("Error");
            alert.setHeaderText("Salary must be at least 10,000");
            alert.showAndWait();
            return;
        }

        if (dob == null || doj == null) {
            alert.setTitle("Error");
            alert.setHeaderText("Date of Birth and Date of Joining are required");
            alert.showAndWait();
            return;
        }

        if (dob.isAfter(LocalDate.now()) || doj.isAfter(LocalDate.now())) {
            alert.setTitle("Error");
            alert.setHeaderText("Date of Birth and Date of Joining can't be in future");
            alert.showAndWait();
            return;
        }

        // password must be at least 5 characters long
        // TODO: add more password validation rules
        if (password.length() < 5) {
            alert.setTitle("Error");
            alert.setHeaderText("Password must be at least 5 characters long");
            alert.showAndWait();
            return;
        }

        if(User.isUserExist(id)) {
            alert.setTitle("Error");
            alert.setHeaderText("User with this ID already exists");
            alert.showAndWait();
            return;
        }

        String gender = ((RadioButton) genderToggleGroup.getSelectedToggle()).getText();
        User user = Admin.createAndSaveUser(id, name, gender, email, designation, dob, doj, password, salary);
        if(user == null) {
            alert.setTitle("Error");
            alert.setHeaderText("Invalid Designation");
            alert.showAndWait();
            return;
        }

        nameTextField.clear();
        emailTextField.clear();
        passwordTextField.clear();
        idTextField.clear();
        salaryTextField.clear();
        dobDatePicker.getEditor().clear();
        dojDatePicker.getEditor().clear();
        designationComboBox.getSelectionModel().clearSelection();
        genderToggleGroup.selectToggle(null);

        alert.setAlertType(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("User created successfully");
        alert.setContentText("User with ID '" + id + "' designation '" + designation + "' created successfully with password '" + password + "'");
        alert.showAndWait();
    }

}