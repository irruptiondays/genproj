package org.irruptiondays.genealogy.dao;

import org.irruptiondays.genealogy.EntityCreator;
import org.irruptiondays.genealogy.GenprojApplication;
import org.irruptiondays.genealogy.domain.Marriage;
import org.irruptiondays.genealogy.domain.Person;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import static org.junit.Assert.*;


/**
 * Created by TValentine on 5/6/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = GenprojApplication.class)
@WebAppConfiguration
@Transactional
public class PersonRepositoryTest {

    @Autowired
    private PersonRepository personRepository;

    /**
     * Test basic save to database.
     */
    @Test
    public void testCreateBasicPerson() {
        Person person = personRepository.save(EntityCreator.createPerson("Bobby"));
        assertEquals("Eastwood", person.getBirthplace());
        assertEquals("Smith", person.getLastName());
        assertEquals("Bobby", person.getFirstName());
    }

    /**
     * Test saving with father/mother (Person has-a Person)
     */
    @Test
    public void testCreatePersonWithParents() {
        Person person = personRepository.save(EntityCreator.createPerson("Bobby"));
        Person father = personRepository.save(EntityCreator.createPerson("Daddy"));
        Person mother = personRepository.save(EntityCreator.createPerson("Mommy"));
        person.setFather(father);
        person.setMother(mother);

        person = personRepository.save(person);

        assertNotNull(person.getFather());
        assertNotNull(person.getMother());
        assertEquals("Daddy", person.getFather().getFirstName());
        assertEquals("Mommy", person.getMother().getFirstName());
    }

    @Test
    public void testMarriage() {
        Person person = personRepository.save(EntityCreator.createPerson("Bobby"));
        Person wife = personRepository.save(EntityCreator.createPerson("Sally"));

        Marriage marriage = new Marriage(person, wife, new Date());
    }



}
