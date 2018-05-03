package controllers;

import client.Client;
import client.View;
import common.Query;
import javafx.event.ActionEvent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

    public TextField login;
    public PasswordField pw;

    public void login(ActionEvent event) {
        Query query = new Query(
                Query.Type.LOGIN,
                login.getText(),
                pw.getText()
        );
        Client.request(query);
    }

    public void openRegisterWindow(ActionEvent event) {
        View.INSTANCE.registration_stage.show();
    }
}
