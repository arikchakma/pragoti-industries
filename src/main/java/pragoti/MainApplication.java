package pragoti;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pragoti.shared.Vehicle;
import pragoti.users.Admin;
import pragoti.shared.Driver;

import java.io.IOException;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // before starting the application, check if the admin user exists or not
        // if not, create the admin user
        Admin.checkOrCreateAdmin();
        Vehicle.generateVehiclesData();
        Driver.generateDriversData();

        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("LoginView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Pragoti Industries");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}