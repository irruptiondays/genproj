package org.irruptiondays.genealogy.dao;

import lombok.extern.slf4j.Slf4j;
import org.irruptiondays.genealogy.EntityCreator;
import org.irruptiondays.genealogy.GenprojApplication;
import org.irruptiondays.genealogy.common.FamilyBranch;
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
import java.util.Set;

import static org.junit.Assert.*;


/**
 * Created by TValentine on 5/6/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = GenprojApplication.class)
@WebAppConfiguration
@Transactional
@Slf4j
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

    @Test
    public void testGetChildrenOfPerson() {
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

        Set<Person> children = personRepository.getChildrenOfPerson(father);

        assertEquals(3, children.size());
    }

    @Test
    public void testGetPersonByFamilyBranch() {
        personRepository.save(EntityCreator.createPerson("Bobby"));
        personRepository.save(EntityCreator.createPerson("Suzy"));
        personRepository.save(EntityCreator.createPerson("Charlie"));
        Set<Person> paternalBranchMembers = personRepository.getPersonByFamilyBranch(FamilyBranch.PATERNAL);
        Set<Person> maternalBranchMembers = personRepository.getPersonByFamilyBranch(FamilyBranch.MATERNAL);

        assertEquals(3, paternalBranchMembers.size());
        assertEquals(0, maternalBranchMembers.size());
    }

}
