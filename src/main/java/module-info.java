module com.pragoti.pragoti {
    requires javafx.controls;
    requires javafx.fxml;


    opens pragoti to javafx.fxml;
    exports pragoti;
    exports pragoti.controllers;
    opens pragoti.controllers to javafx.fxml;
}