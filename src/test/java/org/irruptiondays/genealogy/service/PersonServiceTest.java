package org.irruptiondays.genealogy.service;

import lombok.extern.slf4j.Slf4j;
import org.irruptiondays.genealogy.EntityCreator;
import org.irruptiondays.genealogy.GenprojApplication;
import org.irruptiondays.genealogy.dao.MarriageRepository;
import org.irruptiondays.genealogy.dao.MiscDataRepository;
import org.irruptiondays.genealogy.dao.PersonRepository;
import org.irruptiondays.genealogy.domain.Marriage;
import org.irruptiondays.genealogy.model.MarriageSummary;
import org.irruptiondays.genealogy.domain.MiscData;
import org.irruptiondays.genealogy.domain.Person;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by tvalentine on 5/8/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = GenprojApplication.class)
@WebAppConfiguration
@Transactional
@Slf4j
public class PersonServiceTest {

    @Autowired
    private PersonService personService;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private MarriageRepository marriageRepository;

    @Autowired
    private MiscDataRepository miscDataRepository;

    @Test
    public void testGetSiblingsByPerson() {
        Person father = personRepository.save(EntityCreator.createPerson("Daddy"));
        Person mother = personRepository.save(EntityCreator.createPerson("Mommy"));

        Person person = personRepository.save(EntityCreator.createPerson("Bobby"));
        person.setFather(father);
        person.setMother(mother);
        personRepository.save(person);

        Person person1 = personRepository.save(EntityCreator.createPerson("Dave"));
        person1.setFather(father);
        person1.setMother(mother);
        personRepository.save(person1);

        Person person2 = personRepository.save(EntityCreator.createPerson("Sally"));
        person2.setFather(father);
        person2.setMother(mother);
        personRepository.save(person2);

        // Not child of Daddy/Mommy, should not be counted.
        Person person3 = personRepository.save(EntityCreator.createPerson("John"));
        person3.setFather(person1);
        personRepository.save(person3);

        Set<Person> siblings = personService.getSiblingsByPerson(person);

        assertEquals(2, siblings.size());
    }

    @Test
    public void testSetParents() {
        Person father = personRepository.save(EntityCreator.createPerson("Daddy"));
        Person mother = personRepository.save(EntityCreator.createPerson("Mommy"));
        Person person = personRepository.save(EntityCreator.createPerson("Bobby"));

        try {
            person = personService.setParents(person.getId(), father.getId(), mother.getId());
            assertEquals("Daddy", person.getFather().getFirstName());
            assertEquals("Mommy", person.getMother().getFirstName());
        } catch (Exception e) {
            fail("Failed: "+ e);
        }
    }

    @Test
    public void testSetParentsNulls() {
        Person father = personRepository.save(EntityCreator.createPerson("Daddy"));
        Person mother = personRepository.save(EntityCreator.createPerson("Mommy"));
        Person person = personRepository.save(EntityCreator.createPerson("Bobby"));

        try {
            person = personService.setParents(person.getId(), father.getId(), mother.getId());
            assertEquals("Daddy", person.getFather().getFirstName());
            assertEquals("Mommy", person.getMother().getFirstName());

            person = personService.setParents(person.getId(), 0l, 0l);
            assertEquals(null, person.getFather());
            assertEquals(null, person.getMother());
        } catch (Exception e) {
            fail("Failed: " + e);
        }
    }

    @Test
    public void testDeletePerson() {
        Person person = personRepository.save(EntityCreator.createPerson("Bobby"));
        Person wife = personRepository.save(EntityCreator.createPerson("Sally"));
        Person wife2 = personRepository.save(EntityCreator.createPerson("Sally2"));
        Person wife3 = personRepository.save(EntityCreator.createPerson("Sally3"));

        Marriage marriage = marriageRepository.save(new Marriage(person, wife, new Date(), false));
        Marriage marriage2 = marriageRepository.save(new Marriage(person, wife2, new Date(), false));
        Marriage marriage3 = marriageRepository.save(new Marriage(person, wife3, new Date(), false));

        //Person person, String displayName, Date date, String text
        MiscData miscData = miscDataRepository.save(new MiscData(person, "misc!", new Date(), "Mc"));

        person = personRepository.findOne(person.getId());

        assertEquals(3, marriageRepository.getMarriagesByPerson(person).size());
        assertEquals(1, miscDataRepository.findByPerson(person).size());

        personService.deletePersonById(person.getId());
        assertEquals(0, marriageRepository.getMarriagesByPerson(person).size());
        assertEquals(0, miscDataRepository.findByPerson(person).size());

        assertNotNull(personRepository.findOne(wife.getId()));
        assertNotNull(personRepository.findOne(wife.getId()));
        assertNotNull(personRepository.findOne(wife.getId()));
        assertNull(personRepository.findOne(person.getId()));
        assertNull(marriageRepository.findOne(marriage.getId()));
        assertNull(marriageRepository.findOne(marriage2.getId()));
        assertNull(marriageRepository.findOne(marriage3.getId()));
        assertNull(miscDataRepository.findOne(miscData.getId()));
    }

    @Test
    public void testGetSpousesByPersonId() {
        Person person = personRepository.save(EntityCreator.createPerson("Bobby"));
        Person wife = personRepository.save(EntityCreator.createPerson("Sally"));
        Person wife2 = personRepository.save(EntityCreator.createPerson("Sally2"));
        Person wife3 = personRepository.save(EntityCreator.createPerson("Sally3"));
        Person husband2 = personRepository.save(EntityCreator.createPerson("Bobby2"));

        marriageRepository.save(new Marriage(person, wife, new Date(), false));
        marriageRepository.save(new Marriage(person, wife2, new Date(), false));
        marriageRepository.save(new Marriage(person, wife3, new Date(), false));
        marriageRepository.save(new Marriage(husband2, wife3, new Date(), false));

        List<MarriageSummary> marriages = new ArrayList<>();
        personService.getSpousesForPersonId(person.getId()).iterator().forEachRemaining(marriages::add);
        assertEquals("Should be 3, but was " + marriages.size(), 3, marriages.size());

        marriages = new ArrayList<>();
        personService.getSpousesForPersonId(wife.getId()).iterator().forEachRemaining(marriages::add);
        assertEquals("Should be 1, but was " + marriages.size(), 1, marriages.size());

        marriages = new ArrayList<>();
        personService.getSpousesForPersonId(wife2.getId()).iterator().forEachRemaining(marriages::add);
        assertEquals("Should be 1, but was " + marriages.size(), 1, marriages.size());

        marriages = new ArrayList<>();
        personService.getSpousesForPersonId(wife3.getId()).iterator().forEachRemaining(marriages::add);
        assertEquals("Should be 2, but was " + marriages.size(), 2, marriages.size());

        marriages = new ArrayList<>();
        personService.getSpousesForPersonId(husband2.getId()).iterator().forEachRemaining(marriages::add);
        assertEquals("Should be 1, but was " + marriages.size(), 1, marriages.size());

    }
}
