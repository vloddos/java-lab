package client;

import common.*;

import javafx.event.ActionEvent;
import javafx.scene.control.TextField;

public class Controller {

    public TextField rflogin;
    public TextField rfpw;
    public TextField rfname;
    public TextField rfsurname;

    public void openRegisterWindow(ActionEvent event) {
        View.INSTANCE.registration_stage.show();
    }

    public void register(ActionEvent event) throws Exception {
        Query query = new Query(
                Query.Type.REGISTRATION,
                rflogin.getText(),
                rfpw.getText(),
                rfname.getText(),
                rfsurname.getText()
        );
        Client.request(query);
    }
}