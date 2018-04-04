package client;

import common.Query;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import client.Model;

public class Controller {

    public Button lblogin;
    public Button lbreg;
    public Button rbreg;

    public TextField rflogin;
    public TextField rfpw;
    public TextField rfname;
    public TextField rfsurname;

    public void openRegisterWindow(ActionEvent event) throws Exception {
        Stage rstage = new Stage();
        rstage.initOwner(Model.INSTANCE.stage);
        rstage.initModality(Modality.WINDOW_MODAL);
        rstage.setTitle("Registration");
        rstage.setScene(new Scene(Model.INSTANCE.child, 300, 275));
        rstage.show();
    }

    public void register(ActionEvent event) {
        Query query = new Query(
                Query.Type.REGISTRATION,
                rflogin.getText(),
                rfpw.getText(),
                rfname.getText(),
                rfsurname.getText()
        );
    }
}