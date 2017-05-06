package org.irruptiondays.genealogy;

import org.irruptiondays.genealogy.domain.Person;

import java.util.Date;

/**
 * Created by TValentine on 5/6/17.
 */
public class EntityCreator {

    public static Person createPerson(String firstName) {
        return Person.builder().birthdate(new Date())
                .birthplace("Eastwood")
                .lastName("Smith")
                .firstName(firstName)
                .build();
    }
}
