package client;

import common.*;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class Controller {

    public TextField lflogin;
    public PasswordField lfpw;

    public TextField rflogin;
    public TextField rfpw;
    public TextField rfname;
    public TextField rfsurname;

    public Label lrole;
    public HBox hconds;

    // TODO: 10.04.2018
    //spacing
    //status window in fxml???
    //role/login in main???
    //1 fxml - 1 controller???
    //tables many to many???

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

    public void requestAuthorship(ActionEvent event) {
        Client.request(new Query(Query.Type.REQUEST_AUTHORSHIP, Client.session));
    }

    public void addOrCondition(ActionEvent event) {
        VBox vBox = new VBox();

        Button bAND = new Button("AND");
        bAND.setOnAction(e1 -> {
            HBox hBox = new HBox();

            ComboBox cbtable = new ComboBox();
            cbtable.getItems().addAll(
                    "author",
                    "author_organization",
                    "journal",
                    "keyword",
                    "organization",
                    "publication",
                    "publication_author",
                    "publication_keyword",
                    "publication_redactor",
                    "user",
                    "request_authorship"
            );

            ComboBox cbfield = new ComboBox();

            ComboBox cbrelation = new ComboBox();
            cbrelation.getItems().addAll("<", ">", "<=", ">=", "=", "!=");

            ComboBox cbvalue = new ComboBox();
            cbvalue.setEditable(true);

            Button bdelcond = new Button("-");
            bdelcond.setOnAction(e2 -> vBox.getChildren().remove(hBox));

            hBox.getChildren().addAll(cbtable, cbfield, cbrelation, cbvalue, bdelcond);

            vBox.getChildren().add(hBox);
        });

        Button bX = new Button("X");
        bX.setOnAction(e -> hconds.getChildren().remove(vBox));

        vBox.getChildren().add(
                new HBox(
                        new Label("Table"),
                        new Label("Field"),
                        new Label("Relation"),
                        new Label("Value"),
                        bAND,
                        bX
                )
        );

        hconds.getChildren().add(vBox);
    }

    public void test(ActionEvent event) {
        System.out.println(View.INSTANCE.main_stage.getWidth());
    }
}
/*
<Label text="Table" GridPane.columnIndex="1" GridPane.rowIndex="4"/>
    <ComboBox fx:id="cbtable" GridPane.columnIndex="2" GridPane.rowIndex="4">
        <items>
            <FXCollections fx:factory="observableArrayList">
                <String fx:value="author"/>
                <String fx:value="author_organization"/>
                <String fx:value="journal"/>
                <String fx:value="keyword"/>
                <String fx:value="organization"/>
                <String fx:value="publication"/>
                <String fx:value="publication_author"/>
                <String fx:value="publication_keyword"/>
                <String fx:value="publication_redactor"/>
                <String fx:value="user"/>
                <String fx:value="request_authorship"/>
            </FXCollections>
        </items>
    </ComboBox>
 */
/*
<ScrollPane hbarPolicy="ALWAYS" vbarPolicy="ALWAYS" GridPane.columnIndex="1" GridPane.columnSpan="4"
                GridPane.rowIndex="5">
        <content>
            <HBox fx:id="hconds">
                <VBox>
                    <HBox>
                        <Label text="Field"/>
                        <Label text="Relation"/>
                        <Label text="Value"/>
                        <Button text="AND"/>
                        <Button text="X"/>
                    </HBox>
                    <HBox>
                        <ComboBox/>
                        <ComboBox>
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
                        <ComboBox editable="true" prefWidth="70"/>
                        <Button onAction="#deleteCondition" text="-"/>
                    </HBox>
                </VBox>
            </HBox>
        </content>
    </ScrollPane>
 */
/*
<HBox spacing="5" GridPane.columnIndex="1" GridPane.rowIndex="6" GridPane.columnSpan="4">
        <VBox fx:id="vconds1">
            <HBox spacing="20">
                <Label text="Field"/>
                <Label text="Relation"/>
                <Label text="Value"/>
                <Button fx:id="baddcond" text="+" onAction="#addCondition"/>
            </HBox>
        </VBox>

        <VBox fx:id="vconds2">
            <HBox spacing="20">
                <Label text="Field"/>
                <Label text="Relation"/>
                <Label text="Value"/>
                <Button fx:id="baddcond" text="+" onAction="#addCondition"/>
            </HBox>
        </VBox>
    </HBox>
 */
/*
<Label text="Field" GridPane.columnIndex="1" GridPane.rowIndex="5"/>
    <Label text="Relation" GridPane.columnIndex="2" GridPane.rowIndex="5"/>
    <Label text="Value" GridPane.columnIndex="3" GridPane.rowIndex="5"/>
    <Button fx:id="baddcond" text="+" GridPane.columnIndex="4" GridPane.rowIndex="5" onAction="#addCondition"/>

    <VBox fx:id="vconds" GridPane.columnIndex="1" GridPane.rowIndex="6" GridPane.columnSpan="4">
        <HBox>
            <ComboBox/>
            <ComboBox>
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
            <ComboBox editable="true" prefWidth="70"/>
            <Button onAction="#deleteCondition" text="-"/>
        </HBox>
    </VBox>
 */
/*
 <FlowPane fx:id="fpconds" hgap="5" vgap="5" GridPane.columnIndex="1" GridPane.rowIndex="6" GridPane.columnSpan="3">
        <Label text="Field"/>
        <Label text="Relation"/>
        <Label text="Value"/>
        <Button fx:id="baddcond" text="+"/>

        <ComboBox/>
        <ComboBox>
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
        <ComboBox editable="true" prefWidth="70"/>
        <Button onAction="#deleteCondition" text="-"/>
    </FlowPane>
 */
/*
<GridPane fx:id="gpconds" hgap="5" vgap="5" GridPane.columnIndex="1" GridPane.rowIndex="6" GridPane.columnSpan="3">
        <Label text="Field" GridPane.columnIndex="0" GridPane.rowIndex="0"/>
        <Label text="Relation" GridPane.columnIndex="1" GridPane.rowIndex="0"/>
        <Label text="Value" GridPane.columnIndex="2" GridPane.rowIndex="0"/>
        <Button fx:id="baddcond" text="+" GridPane.columnIndex="3" GridPane.rowIndex="0" onAction="#addCondition"/>

        <ComboBox GridPane.columnIndex="0" GridPane.rowIndex="1"/>
        <ComboBox GridPane.columnIndex="1" GridPane.rowIndex="1">
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
        <ComboBox editable="true" GridPane.columnIndex="2" GridPane.rowIndex="1"/>
        <Button onAction="#deleteCondition" text="-" GridPane.columnIndex="3" GridPane.rowIndex="1"/>
    </GridPane>
 */
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