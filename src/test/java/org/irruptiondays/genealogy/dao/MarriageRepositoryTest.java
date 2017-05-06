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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by TValentine on 5/6/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = GenprojApplication.class)
@WebAppConfiguration
@Transactional
public class MarriageRepositoryTest {

    @Autowired
    PersonRepository personRepository;

    @Autowired
    MarriageRepository marriageRepository;

    @Test
    public void testMarriage() {
        Person person = personRepository.save(EntityCreator.createPerson("Bobby"));
        Person wife = personRepository.save(EntityCreator.createPerson("Sally"));

        Marriage marriage = marriageRepository.save(new Marriage(person, wife, new Date()));
        assertNotNull(marriage);
        assertEquals("Bobby", marriage.getSpouse1().getFirstName());
        assertEquals("Sally", marriage.getSpouse2().getFirstName());
        assertTrue(marriage.getId() > 0);
    }

    @Test
    public void testMarriages() {
        Person person = personRepository.save(EntityCreator.createPerson("Bobby"));
        Person wife = personRepository.save(EntityCreator.createPerson("Sally"));
        Person wife2 = personRepository.save(EntityCreator.createPerson("Sally2"));
        Person wife3 = personRepository.save(EntityCreator.createPerson("Sally3"));

        marriageRepository.save(new Marriage(person, wife, new Date()));
        marriageRepository.save(new Marriage(person, wife2, new Date()));
        marriageRepository.save(new Marriage(person, wife3, new Date()));

        List<Marriage> marriages = new ArrayList<>();
        marriageRepository.findAll().iterator().forEachRemaining(marriages::add);
        assertEquals("Should be 3, but was " + marriages.size(), 3, marriages.size());
    }
}
