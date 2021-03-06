package org.irruptiondays.genealogy.ui;

import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.irruptiondays.genealogy.domain.Person;

import java.util.List;

/**
 * Created by tvalentine on 11/5/2018.
 */
public class MainGenprojWindow extends TabPane {

    private static final String PERSON_LIST_HEADER = "Person List Header";

    public MainGenprojWindow() {
        setPrefSize(1000, 800);
        setMinSize(TabPane.USE_PREF_SIZE, TabPane.USE_PREF_SIZE);
        setMaxSize(TabPane.USE_PREF_SIZE, TabPane.USE_PREF_SIZE);
        setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
        tabSetup();
    }

    public void tabSetup() {
        Tab personList = new Tab();
        personList.setText(PERSON_LIST_HEADER);
        BorderPane personListPane = new BorderPane();
        personListPane.setPrefSize(990, 790);
        VBox vbox = new VBox();
        vbox.setMinSize(980, 780);
        //personListPane.setStyle("-fx-border-color: red;");
        Label personListHeader = new Label(" " + PERSON_LIST_HEADER);
        personListHeader.setMinWidth(200);


        StringBuffer sb = new StringBuffer("");

        List<Person> persons = ApplicationContextUtils.getPersonController().getAllPersons();

        if (persons != null) {
            for (Person p : persons) {
                //sb.append("" + i + " This is text.\n This is some more. \n A third line.\n\n");
                sb.append(p.getPrintedName()).append("\n");
            }
        }
        Text text = new Text(sb.toString());
        ScrollPane scrollPane = new ScrollPane(text);
        scrollPane.setMinSize(970, 770);

        vbox.getChildren().addAll(personListHeader, scrollPane);
        personListPane.getChildren().addAll(vbox);
        personList.setContent(personListPane);
        this.getTabs().addAll(personList);
    }



}
