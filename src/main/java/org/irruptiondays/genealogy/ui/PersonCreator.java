package org.irruptiondays.genealogy.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.irruptiondays.genealogy.controller.PersonController;
import org.irruptiondays.genealogy.domain.Person;

import java.util.List;

public class PersonCreator extends GenTab {

    private PersonController personController;
    private List<Person> persons;

    public PersonCreator() {
        super("Create New Person");
        personController = ApplicationContextUtils.getPersonController(); 
        persons = personController.getAllPersons();
        ObservableList<String> personNameList = FXCollections.observableArrayList();
        personNameList.addAll();
    }
}
