package client;

import common.*;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.function.Consumer;

public class View {

    public static final View INSTANCE = new View();

    public Stage login_stage = new Stage();
    public Stage login_status_stage = new Stage();
    public Stage registration_stage = new Stage();
    public Stage registration_status_stage = new Stage();
    public Stage main_stage = new Stage();
    public Stage main_status_stage = new Stage();

    public Parent login_form;
    public StackPane login_status_form;
    public Parent registration_form;
    public StackPane registration_status_form;
    public Parent main_form;
    public StackPane main_status_form;

    private View() {
        try {
            login_form = FXMLLoader.load(getClass().getResource("/forms/login.fxml"));
            login_status_form=FXMLLoader.load(getClass().getResource("/forms/login_status.fxml"));
            registration_form = FXMLLoader.load(getClass().getResource("/forms/registration.fxml"));
            registration_status_form= FXMLLoader.load(getClass().getResource("/forms/registration_status.fxml"));
            main_form = FXMLLoader.load(getClass().getResource("/forms/main.fxml"));
            main_status_form = FXMLLoader.load(getClass().getResource("/forms/main_status.fxml"));
        } catch (Exception e) {
            //e.printStackTrace();
        }

        login_stage.setTitle("Login");
        login_stage.setScene(new Scene(login_form));

        login_status_stage.initOwner(login_stage);
        login_status_stage.initModality(Modality.WINDOW_MODAL);
        login_status_stage.setScene(new Scene(new StackPane(login_status_form)));

        registration_stage.initOwner(login_stage);
        registration_stage.initModality(Modality.WINDOW_MODAL);
        registration_stage.setTitle("Registration");
        registration_stage.setScene(new Scene(registration_form));

        registration_status_stage.initOwner(registration_stage);
        registration_status_stage.initModality(Modality.WINDOW_MODAL);
        registration_status_stage.setScene(new Scene(registration_status_form));

        main_stage.setTitle("Main");
        main_stage.setOnCloseRequest(e -> Client.request(new Query(Query.Type.SESSION_CLOSE, Client.session)));
        //main_stage.setMinWidth(800);
        //main_stage.setMaxWidth(800);
        main_stage.setScene(new Scene(main_form));

        main_status_stage.initOwner(main_stage);
        main_status_stage.initModality(Modality.WINDOW_MODAL);
        main_status_stage.setScene(new Scene(main_status_form));
    }

    public void parseAnswer(Query.Type type, Answer answer) {
        // TODO: 08.04.2018
        // frozen buttons if not enough permissions???
        Consumer<StackPane> setText = (status_form) -> ((Text) status_form.getChildren().get(0)).setText(answer.message);

        switch (type) {
            case REGISTRATION:
                if (answer.status == Answer.Status.OK)//nuzhno voobshe???
                    registration_status_stage.setOnCloseRequest(e -> registration_stage.close());
                setText.accept(registration_status_form);
                registration_status_stage.show();
                break;
            case LOGIN:
                if (answer.status == Answer.Status.OK) {
                    Client.session = answer.session;
                    Client.role = answer.role;
                    System.out.println(Client.role);//debug
                    //((Label) ((GridPane) main_form).getChildren().get(2)).setText(answer.role.toString());
                    login_stage.close();
                    main_stage.show();
                } else {
                    setText.accept(login_status_form);
                    login_status_stage.show();
                }
                break;
            case SESSION_CLOSE:
                //System.out.println(answer.message);
                break;
            case REQUEST_AUTHORSHIP:
                setText.accept(main_status_form);
                main_status_stage.show();
                break;
        }
    }
}
