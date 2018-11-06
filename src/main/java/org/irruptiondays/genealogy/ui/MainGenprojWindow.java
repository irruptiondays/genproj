package org.irruptiondays.genealogy.ui;

import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

/**
 * Created by tvalentine on 11/5/2018.
 */
public class MainGenprojWindow extends TabPane {

    private static final String PERSON_LIST_HEADER = "Person List Header";

    public MainGenprojWindow() {
        setPrefSize(1000, 800);
        setMinSize(TabPane.USE_PREF_SIZE, TabPane.USE_PREF_SIZE);
        setMaxSize(TabPane.USE_PREF_SIZE, TabPane.USE_PREF_SIZE);
        tabSetup();
    }

    public void tabSetup() {
        Tab personList = new Tab();
        personList.setText(PERSON_LIST_HEADER);
        BorderPane personListPane = new BorderPane();
        personListPane.setPrefSize(990, 790);
        VBox vbox = new VBox();
        vbox.setPrefSize(980, 780);
        Label personListHeader = new Label(PERSON_LIST_HEADER);
        personListHeader.setMinWidth(200);
        vbox.getChildren().addAll(personListHeader);
        personListPane.getChildren().addAll(vbox);
        personList.setContent(personListPane);
        this.getTabs().addAll(personList);
    }


}
