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

    public Stage login_stage = new Stage();
    public Stage login_status_stage = new Stage();
    public Stage registration_stage = new Stage();
    public Stage registration_status_stage = new Stage();
    public Stage main_stage = new Stage();

    public Parent login_form;
    public VBox login_status_root = getStatusWindow();
    public Parent registration_form;
    public VBox registration_status_root = getStatusWindow();
    public Parent main_form;

    public VBox getStatusWindow() {
        VBox vBox = new VBox();
        vBox.getChildren().addAll(new Text(), new Button("OK"));
        return vBox;
    }

    private View() {
        try {
            login_form = FXMLLoader.load(getClass().getResource("/forms/login.fxml"));
            registration_form = FXMLLoader.load(getClass().getResource("/forms/registration.fxml"));
            main_form = FXMLLoader.load(getClass().getResource("/forms/main.fxml"));
        } catch (Exception e) {
            //e.printStackTrace();
        }

        login_stage.setTitle("Login");
        login_stage.setScene(new Scene(login_form, 300, 275));

        login_status_stage.initOwner(login_stage);
        login_status_stage.initModality(Modality.WINDOW_MODAL);
        login_status_stage.setScene(new Scene(login_status_root, 300, 275));

        registration_stage.initOwner(login_stage);
        registration_stage.initModality(Modality.WINDOW_MODAL);
        registration_stage.setTitle("Registration");
        registration_stage.setScene(new Scene(registration_form, 300, 275));

        registration_status_stage.initOwner(registration_stage);
        registration_status_stage.initModality(Modality.WINDOW_MODAL);
        registration_status_stage.setScene(new Scene(registration_status_root, 300, 275));

        main_stage.setTitle("Main");
        main_stage.setScene(new Scene(main_form, 500, 500));
    }

    public void parseAnswer(Query.Type type, Answer answer) {
        // TODO: 08.04.2018
        // mainform in code???
        // settitle???
        // session in server
        // exit and login again
        // frozen buttons if not enough permissions
        switch (type) {
            case REGISTRATION:
                ((Button) registration_status_root.getChildren().get(1)).setOnAction(
                        answer.status == Answer.Status.OK ?
                                e -> {
                                    registration_status_stage.close();
                                    registration_stage.close();
                                } :
                                event -> registration_status_stage.close()
                );
                ((Text) registration_status_root.getChildren().get(0)).setText(answer.message);
                registration_status_stage.show();
                break;
            case LOGIN:
                if (answer.status == Answer.Status.OK) {
                    Client.session = answer.session;
                    login_stage.close();
                    main_stage.show();
                } else {
                    ((Button) login_status_root.getChildren().get(1)).setOnAction(e -> login_status_stage.close());
                    ((Text) login_status_root.getChildren().get(0)).setText(answer.message);
                    login_status_stage.show();
                }
                break;
        }
    }
}
