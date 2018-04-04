package client;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

public class Model {

    public static final Model INSTANCE = new Model();

    public Stage stage;
    public Parent root;
    public Parent child;

    public Model() {
        try {
            root = FXMLLoader.load(getClass().getResource("/forms/login.fxml"));
            child = FXMLLoader.load(getClass().getResource("/forms/registration.fxml"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
