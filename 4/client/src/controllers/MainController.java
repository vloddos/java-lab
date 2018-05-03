package controllers;

import client.Client;
import common.TableInfo;
import client.View;
import common.Answer;
import common.Query;
import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MainController {

    public TableView result_table;
    public HBox conditions;
    public VBox values;
    public ToggleGroup request_group;
    public ComboBox cbtable;

    public void exit(ActionEvent event) {
        View.INSTANCE.main_stage.close();
        Client.request(new Query(Query.Type.SESSION_CLOSE, Client.session));
        View.INSTANCE.login_stage.show();
    }

    public void requestAuthorship(ActionEvent event) {
        Client.request(new Query(Query.Type.REQUEST_AUTHORSHIP, Client.session));
    }

    public void addOrCondition(ActionEvent event) {//field.setOnAction();
        VBox vBox = new VBox();

        Button bAND = new Button("AND");
        bAND.setOnAction(e1 -> {
            GridPane gridPane = new GridPane();
            IntStream.range(0, 4).forEach(i -> gridPane.getColumnConstraints().add(new ColumnConstraints(100)));

            ComboBox field = new ComboBox();
            if (cbtable.getValue() != null)
                field.getItems().addAll(TableInfo.table_fields.get(cbtable.getValue().toString()));

            ComboBox relation = new ComboBox();
            relation.getItems().addAll("<", ">", "<=", ">=", "=", "!=");

            ComboBox value = new ComboBox();
            value.setEditable(true);

            Button delcond = new Button("-");
            delcond.setOnAction(e2 -> vBox.getChildren().remove(gridPane));

            gridPane.addRow(0, field, relation, value, delcond);

            vBox.getChildren().add(gridPane);
        });

        Button bX = new Button("X");
        bX.setOnAction(e -> conditions.getChildren().remove(vBox));

        GridPane columns = new GridPane();
        IntStream.range(0, 5).forEach(i -> columns.getColumnConstraints().add(new ColumnConstraints(100)));
        columns.addRow(
                0,
                new Label("Field"),
                new Label("Relation"),
                new Label("Value"),
                bAND,
                bX
        );
        vBox.getChildren().add(columns);

        conditions.getChildren().add(vBox);
    }

    public void updateFields(ActionEvent event) {
        Consumer<Node> f = g -> {
            ObservableList list = ((ComboBox) ((GridPane) g).getChildren().get(0)).getItems();
            list.clear();
            list.addAll(TableInfo.table_fields.get(cbtable.getValue().toString()));
        };

        conditions.getChildren()
                .forEach(
                        v -> ((VBox) v).getChildren()
                                .stream()
                                .skip(1)
                                .forEach(f)
                );

        values.getChildren()
                .stream()
                .skip(1)
                .forEach(f);
    }

    public void addValues(ActionEvent event) {
        GridPane gridPane = new GridPane();
        IntStream.range(0, 3).forEach(i -> gridPane.getColumnConstraints().add(new ColumnConstraints(100)));

        ComboBox field = new ComboBox();
        if (cbtable.getValue() != null)
            field.getItems().addAll(TableInfo.table_fields.get(cbtable.getValue().toString()));

        TextField value = new TextField();

        Button delvalue = new Button("-");
        delvalue.setOnAction(e -> values.getChildren().remove(gridPane));

        gridPane.addRow(0, field, value, delvalue);

        values.getChildren().add(gridPane);
    }

    public void updateResultTable(String table, String where) {
        Answer answer = Client.request(new Query(Query.Type.SELECT, Client.session, table, where));

        ObservableList<ArrayList<String>> ol = FXCollections.observableArrayList();
        answer.al.forEach(r -> ol.add(r));

        result_table.setItems(ol);
    }

    public void request(ActionEvent event) {
        Consumer<String> setText = message -> ((Text) View.INSTANCE.main_status_form.getChildren().get(0)).setText(message);

        String[] table = new String[1];

        if (cbtable.getValue() != null)
            table[0] = cbtable.getValue().toString();
        else {
            setText.accept("No table is chosen");
            View.INSTANCE.main_status_stage.show();
            return;
        }

        String where = getWhere();
        String set = getSet();
        String query = ((RadioButton) request_group.getSelectedToggle()).getText();

        if (query.equals("select")) {
            ObservableList columns = result_table.getColumns();
            columns.clear();
            IntStream.range(0, TableInfo.table_fields.get(table[0]).length)
                    .forEach(i -> columns.add(new TableColumn<ArrayList<String>, String>(TableInfo.table_fields.get(table[0])[i])));
            IntStream.range(0, columns.size())
                    .forEach(i -> {
                                TableColumn column = ((TableColumn) columns.get(i));
                                column.setCellValueFactory(param ->
                                        new ObservableValue<String>() {
                                            @Override
                                            public void addListener(ChangeListener<? super String> listener) {

                                            }

                                            @Override
                                            public void removeListener(ChangeListener<? super String> listener) {

                                            }

                                            @Override
                                            public String getValue() {
                                                return ((TableColumn.CellDataFeatures<ArrayList<String>, String>) param).getValue().get(i);
                                            }

                                            @Override
                                            public void addListener(InvalidationListener listener) {

                                            }

                                            @Override
                                            public void removeListener(InvalidationListener listener) {

                                            }
                                        }
                                );
                            }
                    );
        } else if (query.equals("delete")) {
            if (where.equals("")) {
                setText.accept("No conditions are specified");
                View.INSTANCE.main_status_stage.show();
                return;
            }
            Client.request(new Query(Query.Type.DELETE, Client.session, table[0], where));
        } else if (query.equals("update")) {
            if (set.equals("")) {
                setText.accept("No values for setting are specified");
                View.INSTANCE.main_status_stage.show();
                return;
            }
            Client.request(new Query(Query.Type.UPDATE, Client.session, table[0], set, where));
        }

        updateResultTable(table[0], where);
    }

    public String getWhere() {
        List orlist = new ArrayList();
        for (Node v : conditions.getChildren()) {
            List andlist = new ArrayList();
            ObservableList vc = ((VBox) v).getChildren();
            for (Object g : vc.subList(1, vc.size())) {
                ObservableList gc = ((GridPane) g).getChildren();
                Object s0 = ((ComboBox) gc.get(0)).getValue();
                Object s1 = ((ComboBox) gc.get(1)).getValue();
                Object s2 = ((ComboBox) gc.get(2)).getValue();
                if (s0 != null && s1 != null && s2 != null)
                    andlist.add(String.format("%s%s'%s'", s0, s1, s2));
            }
            if (andlist.size() > 0)
                orlist.add(andlist.stream().collect(Collectors.joining(" and ")).toString());
        }
        return orlist.stream().collect(Collectors.joining(" or ")).toString();
    }

    public String getSet() {
        List setlist = new ArrayList();
        ObservableList vc = values.getChildren();
        for (Object g : vc.subList(1, vc.size())) {
            ObservableList gc = ((GridPane) g).getChildren();
            Object s0 = ((ComboBox) gc.get(0)).getValue();
            Object s1 = ((TextField) gc.get(1)).getText();
            if (s0 != null && s1 != null)
                setlist.add(String.format("%s='%s'", s0, s1));
        }
        return setlist.stream().collect(Collectors.joining(",")).toString();
    }

    public void test(ActionEvent event) {

    }
}