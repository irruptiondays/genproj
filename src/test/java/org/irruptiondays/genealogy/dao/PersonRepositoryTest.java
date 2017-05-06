package org.irruptiondays.genealogy.dao;

import org.irruptiondays.genealogy.GenprojApplication;
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
        Person person = personRepository.save(createPerson("Bobby"));
        assertEquals("Eastwood", person.getBirthplace());
        assertEquals("Smith", person.getLastName());
        assertEquals("Bobby", person.getFirstName());
    }

    /**
     * Test saving with father/mother (Person has-a Person)
     */
    @Test
    public void testCreatePersonWithParents() {
        Person person = personRepository.save(createPerson("Bobby"));
        Person father = personRepository.save(createPerson("Daddy"));
        Person mother = personRepository.save(createPerson("Mommy"));
        person.setFather(father);
        person.setMother(mother);

        System.out.println("\n\n\n\n\n The mother is " + mother);

        person = personRepository.save(person);
        System.out.println("\n\n\n\n\n The person is now " + person);

        assertNotNull(person.getFather());
        assertNotNull(person.getMother());
        assertEquals("Daddy", person.getFather().getFirstName());
        assertEquals("Mommy", person.getMother().getFirstName());
    }

//    @Test
//    public void testMarriage() {
//
//    }

    private Person createPerson(String firstName) {
        return Person.builder().birthdate(new Date())
                .birthplace("Eastwood")
                .lastName("Smith")
                .firstName(firstName)
                .build();
    }

}
