package org.irruptiondays.genealogy.domain;

import org.irruptiondays.genealogy.EntityCreator;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test logic in Person class.
 */
public class PersonTest {

    @Test
    public void testPrintedName() {
        Person person = EntityCreator.createPerson("Bobby");
        assertTrue(person.getPrintedName().startsWith("Smith, Bobby John Jacob"));
    }
}
