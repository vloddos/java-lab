package client;

import common.*;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
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
    public Stage request_authorship_status_stage = new Stage();

    public Parent login_form;
    public StackPane login_status_root = getStatusWindow();
    public Parent registration_form;
    public StackPane registration_status_root = getStatusWindow();
    public Parent main_form;
    public StackPane request_authorship_status_root = getStatusWindow();

    public StackPane getStatusWindow() {
        StackPane stackPane = new StackPane();
        stackPane.setPrefSize(150, 50);
        Text text = new Text();
        StackPane.setAlignment(text, Pos.CENTER);
        stackPane.getChildren().add(text);
        return stackPane;
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
        login_stage.setScene(new Scene(login_form));

        login_status_stage.initOwner(login_stage);
        login_status_stage.initModality(Modality.WINDOW_MODAL);
        login_status_stage.setScene(new Scene(login_status_root));

        registration_stage.initOwner(login_stage);
        registration_stage.initModality(Modality.WINDOW_MODAL);
        registration_stage.setTitle("Registration");
        registration_stage.setScene(new Scene(registration_form));

        registration_status_stage.initOwner(registration_stage);
        registration_status_stage.initModality(Modality.WINDOW_MODAL);
        registration_status_stage.setScene(new Scene(registration_status_root));

        main_stage.setTitle("Main");
        main_stage.setOnCloseRequest(e -> Client.request(new Query(Query.Type.SESSION_CLOSE, Client.session)));
        main_stage.setScene(new Scene(main_form));

        request_authorship_status_stage.initOwner(main_stage);
        request_authorship_status_stage.initModality(Modality.WINDOW_MODAL);
        request_authorship_status_stage.setScene(new Scene(request_authorship_status_root));
    }

    public void parseAnswer(Query.Type type, Answer answer) {
        // TODO: 08.04.2018
        // frozen buttons if not enough permissions???
        switch (type) {
            case REGISTRATION:
                if (answer.status == Answer.Status.OK)//nuzhno voobshe???
                    registration_status_stage.setOnCloseRequest(e -> registration_stage.close());
                ((Text) registration_status_root.getChildren().get(0)).setText(answer.message);
                registration_status_stage.show();
                break;
            case LOGIN:
                if (answer.status == Answer.Status.OK) {
                    Client.session = answer.session;
                    Client.role = answer.role;
                    System.out.println(Client.role);//debug
                    login_stage.close();
                    main_stage.show();
                } else {
                    ((Text) login_status_root.getChildren().get(0)).setText(answer.message);
                    login_status_stage.show();
                }
                break;
            case SESSION_CLOSE:
                //System.out.println(answer.message);
                break;
            case REQUEST_AUTHORSHIP:
                ((Text) request_authorship_status_root.getChildren().get(0)).setText(answer.message);
                request_authorship_status_stage.show();

        }
    }
}
