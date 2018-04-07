package client;

import common.*;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class View {

    public static final View INSTANCE = new View();

    public Text rstmessage = new Text();
    public Button rsbok = new Button("OK");

    public Stage login_stage = new Stage();
    public Stage registration_stage = new Stage();
    public Stage registration_status_stage = new Stage();

    public Parent login_form;
    public Parent registration_form;

    private View() {
        try {
            login_form = FXMLLoader.load(getClass().getResource("/forms/login.fxml"));
            registration_form = FXMLLoader.load(getClass().getResource("/forms/registration.fxml"));
        } catch (Exception e) {
            //e.printStackTrace();
        }

        login_stage.setTitle("Login");
        login_stage.setScene(new Scene(login_form, 300, 275));

        registration_stage.initOwner(login_stage);
        registration_stage.initModality(Modality.WINDOW_MODAL);
        registration_stage.setTitle("Registration");
        registration_stage.setScene(new Scene(registration_form, 300, 275));

        registration_status_stage.initOwner(registration_stage);
        registration_status_stage.initModality(Modality.WINDOW_MODAL);
        VBox vBox = new VBox();
        vBox.getChildren().addAll(rstmessage, rsbok);
        registration_status_stage.setScene(new Scene(vBox, 300, 275));
    }

    public void parseAnswer(Query.Type type, Answer answer) {
        switch (type) {
            case REGISTRATION:
                rsbok.setOnAction(
                        answer.status == Answer.Status.OK ?
                                e -> {
                                    registration_status_stage.close();
                                    registration_stage.close();
                                } :
                                event -> registration_status_stage.close()
                );
                rstmessage.setText(answer.message);
                registration_status_stage.show();
                break;
        }
    }
}
