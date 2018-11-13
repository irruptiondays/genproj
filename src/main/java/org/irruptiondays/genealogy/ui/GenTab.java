package org.irruptiondays.genealogy.ui;

import javafx.scene.control.Tab;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.layout.BorderPane;

public class GenTab extends Tab {

    private BorderPane pane;
    private VBox vbox;

    public GenTab(String title) {
        setText(title);
        pane = new BorderPane();
        pane.setPrefSize(990, 790);
        vbox = new VBox();
        vbox.setMinSize(980, 780);

        Label header = new Label(" " + title);
        header.setStyle("-fx-font-size: 24px;");
        header.setMinWidth(200);

        vbox.getChildren().addAll(header);
        pane.getChildren().addAll(vbox);
    }


    public BorderPane getPane() {
        return pane;
    }

    public VBox getVbox() {
        return vbox;
    }

}