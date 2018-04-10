package client;

import common.*;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;

public class Controller {

    public TextField lflogin;
    public PasswordField lfpw;

    public TextField rflogin;
    public TextField rfpw;
    public TextField rfname;
    public TextField rfsurname;

    // TODO: 10.04.2018
    //combobox items???
    //relation strings???
    //status window in fxml???
    //role/login in main???

    public void openRegisterWindow(ActionEvent event) {
        View.INSTANCE.registration_stage.show();
    }

    public void register(ActionEvent event) {
        Query query = new Query(
                Query.Type.REGISTRATION,
                rflogin.getText(),
                rfpw.getText(),
                rfname.getText(),
                rfsurname.getText()
        );
        Client.request(query);
    }

    public void login(ActionEvent event) {
        Query query = new Query(
                Query.Type.LOGIN,
                lflogin.getText(),
                lfpw.getText()
        );
        Client.request(query);
    }

    public void exit(ActionEvent event) {
        View.INSTANCE.main_stage.close();
        Client.request(new Query(Query.Type.SESSION_CLOSE, Client.session));
        View.INSTANCE.login_stage.show();
    }

    public void request_authorship(ActionEvent event) {
        Client.request(new Query(Query.Type.REQUEST_AUTHORSHIP, Client.session));
    }

    public void test(ActionEvent event) {

    }
}
/*
<String fx:value=">h"/>
                <String fx:value="<h"/>
                <String fx:value=">=h"/>
                <String fx:value="<=h"/>
                <String fx:value="=h"/>
                <String fx:value="!=h"/>
 */

/*
<Button GridPane.columnIndex="3" GridPane.rowIndex="2"
                fx:id="binsert" text="+"/>

        <Button GridPane.columnIndex="4" GridPane.rowIndex="2"
                fx:id="bdelete" text="-"/>

        <Button GridPane.columnIndex="5" GridPane.rowIndex="2"
                fx:id="bupdate" text="change"/>

        <Button GridPane.columnIndex="6" GridPane.rowIndex="2"
                fx:id="bselect" text="find"/>
 */
/*
<Button fx:id="bdelcond" text="-" GridPane.columnIndex="3" GridPane.rowIndex="5"/>
 */

/*
<ComboBox fx:id="cbfield" GridPane.columnIndex="0" GridPane.rowIndex="5"></ComboBox>
        <ComboBox fx:id="cbrelation" GridPane.columnIndex="1" GridPane.rowIndex="5">
            <items>
                <FXCollections fx:factory="observableArrayList">
                    <String fx:value="lt"/>
                    <String fx:value="gt"/>
                    <String fx:value="le"/>
                    <String fx:value="ge"/>
                    <String fx:value="eq"/>
                    <String fx:value="ne"/>
                </FXCollections>
            </items>
        </ComboBox>
        <ComboBox fx:id="cbvalue" editable="true" GridPane.columnIndex="2" GridPane.rowIndex="5"></ComboBox>
        <Button fx:id="bdelcond" text="-" GridPane.columnIndex="3" GridPane.rowIndex="5"/>
 */