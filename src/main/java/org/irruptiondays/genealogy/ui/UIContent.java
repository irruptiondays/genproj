package org.irruptiondays.genealogy.ui;

import javafx.scene.Parent;
import javafx.scene.control.TabPane;

/**
 * Created by tvalentine on 11/5/2018.
 */
public class UIContent {

    public static Parent createContent() {
        MainGenprojWindow tabPane = new MainGenprojWindow();
        return tabPane;
    }
}
