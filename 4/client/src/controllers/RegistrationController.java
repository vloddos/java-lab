package controllers;

import client.Client;
import common.Query;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;

public class RegistrationController {

    public TextField login;
    public TextField pw;
    public TextField name;
    public TextField surname;

    public void register(ActionEvent event) {
        Query query = new Query(
                Query.Type.REGISTRATION,
                login.getText(),
                pw.getText(),
                name.getText(),
                surname.getText()
        );
        Client.request(query);
    }
}
