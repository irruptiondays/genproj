package org.irruptiondays.genealogy;

import org.irruptiondays.genealogy.common.FamilyBranch;
import org.irruptiondays.genealogy.domain.Person;

import java.util.Date;

/**
 * Creates simple pojos and other things. Makes unit test code easier to read.
 */
public class EntityCreator {

    public static Person createPerson(String firstName) {
        return Person.builder().birthdate(new Date())
                .birthplace("Eastwood")
                .lastName("Smith")
                .firstName(firstName)
                .middleNames("John Jacob")
                .familyBranch(FamilyBranch.PATERNAL)
                .currentOrLateHome("Funnyville")
                .build();
    }

    public static String reallyLongStringGenerator() {
        return "Really long string Really long string Really long string " +
                "Really long string Really long string Really long string " +
                "Really long string Really long string Really long string " +
                "Really long string Really long string Really long string " +
                "Really long string Really long string Really long string " +
                "Really long string Really long string Really long string " +
                "Really long string Really long string Really long string " +
                "Really long string Really long string Really long string " +
                "Really long string Really long string Really long string " +
                "Really long string Really long string Really long string " +
                "Really long string Really long string Really long string " +
                "Really long string Really long string Really long string " +
                "Really long string Really long string Really long string " +
                "Really long string Really long string Really long string " +
                "Really long string Really long string Really long string " +
                "Really long string Really long string Really long string " +
                "Really long string Really long string Really long string " +
                "Really long string Really long string Really long string " +
                "Really long string Really long string Really long string " +
                "Really long string Really long string Really long string ";
    }
}
