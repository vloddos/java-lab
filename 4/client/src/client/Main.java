package client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import client.*;

public class Main extends Application {

    //public Parent child;

    @Override
    public void start(Stage stage) throws Exception {
        Model.INSTANCE.stage = stage;
        stage.setTitle("Login");
        stage.setScene(new Scene(Model.INSTANCE.root, 300, 275));
        stage.show();
        Client.main(new String[]{});
    }

    public static void main(String[] args) {
        launch(args);
    }
}
