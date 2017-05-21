package org.irruptiondays.genealogy.service;

import lombok.extern.slf4j.Slf4j;
import org.irruptiondays.genealogy.EntityCreator;
import org.irruptiondays.genealogy.GenprojApplication;
import org.irruptiondays.genealogy.dao.PersonRepository;
import org.irruptiondays.genealogy.domain.Person;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

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
}
