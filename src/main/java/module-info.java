module com.pragoti.pragotiindustries {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.pragoti.pragotiindustries to javafx.fxml;
    exports com.pragoti.pragotiindustries;
}