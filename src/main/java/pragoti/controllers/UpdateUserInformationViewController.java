package pragoti.controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import pragoti.users.Admin;
import pragoti.users.User;

import java.time.LocalDate;
import java.util.ArrayList;

public class UpdateUserInformationViewController {
    @javafx.fxml.FXML
    private TextField salaryTextField;
    @javafx.fxml.FXML
    private TextField passwordTextField;
    @javafx.fxml.FXML
    private ComboBox<String> selectIdComboBox;
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

        designationComboBox.getItems().addAll("Admin", "Logistic Officer");
        refreshSelectUserIdComboBox();
    }

    @javafx.fxml.FXML
    public void validateAndUpdateUserInfoOnAction(ActionEvent actionEvent) {
        String selectedUser = selectIdComboBox.getValue();
        String[] parts = selectedUser.split(" -- ");
        int id = Integer.parseInt(parts[1]);
        User user = User.getUser(id);

        if (user == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("User not found");
            alert.setContentText("User with id " + id + " not found");
            alert.showAndWait();
            return;
        }

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

        boolean isValidPassword = User.validatePassword(password);
        if (!isValidPassword) {
            alert.setTitle("Error");
            alert.setHeaderText("Password must be at least 8 characters long and contain at least one digit, and one special character");
            alert.showAndWait();
            return;
        }

        String gender = ((RadioButton) genderToggleGroup.getSelectedToggle()).getText();

        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        user.setDesignation(designation);
        user.setSalary(salary);
        user.setDob(dob);
        user.setDoj(doj);
        user.setGender(gender);

        Admin.updateAndSaveUser(user);
        refreshSelectUserIdComboBox();

        alert.setAlertType(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("User information updated successfully");
        alert.showAndWait();
    }

    private void refreshSelectUserIdComboBox() {
        String prevSelectedUser = selectIdComboBox.getValue();

        selectIdComboBox.getItems().clear();
        ArrayList<User> users = User.getAllUsers();
        for (User user : users) {
            String key = user.getName() + " (" + user.getDesignation() + ")" + " -- " + user.getId();
            selectIdComboBox.getItems().add(key);
        }

        if (prevSelectedUser != null) {
            String[] parts = prevSelectedUser.split(" -- ");
            int id = Integer.parseInt(parts[1]);

            for (User user : users) {
                if (user.getId() == id) {
                    selectIdComboBox.setValue(user.getName() + " (" + user.getDesignation() + ")" + " -- " + user.getId());
                    break;
                }
            }
        }

    }

    @javafx.fxml.FXML
    public void selectIdOnAction(ActionEvent actionEvent) {
        String selectedUser = selectIdComboBox.getValue();
        if (selectedUser == null) {
            return;
        }

        String[] parts = selectedUser.split(" -- ");
        int id = Integer.parseInt(parts[1]);
        User user = User.getUser(id);

        if (user == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("User not found");
            alert.setContentText("User with id " + id + " not found");
            alert.showAndWait();
            return;
        }

        nameTextField.setText(user.getName());
        emailTextField.setText(user.getEmail());
        passwordTextField.setText(user.getPassword());
        designationComboBox.setValue(user.getDesignation());
        salaryTextField.setText(String.valueOf(user.getSalary()));
        dobDatePicker.setValue(user.getDob());
        dojDatePicker.setValue(user.getDoj());

        String gender = user.getGender();
        if (gender.equals("Male")) {
            maleRadioButton.setSelected(true);
        } else if (gender.equals("Female")) {
            femaleRadioButton.setSelected(true);
        } else {
            otherRadioButton.setSelected(true);
        }
    }
}