package graphic;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("graphic.fxml"));

        Scene scene = new Scene(root, 500, 500);

        stage.setTitle("FXML Welcome");
        stage.setScene(scene);
        stage.show();//*/
    }

    public static void main(String[] args) {
        launch(args);
    }
}