package client;

import common.*;

import javafx.event.ActionEvent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class Controller {

    public TextField lflogin;
    public PasswordField lfpw;

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

    public void login(ActionEvent event) throws Exception {
        Query query = new Query(
                Query.Type.LOGIN,
                lflogin.getText(),
                lfpw.getText()
        );
        Client.request(query);
    }

    public void test(ActionEvent event) {
        //View.INSTANCE.registration_status_stage.initOwner(View.INSTANCE.login_stage);
        View.INSTANCE.registration_status_stage.show();
    }
}