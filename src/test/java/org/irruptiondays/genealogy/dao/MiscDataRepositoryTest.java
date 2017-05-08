package org.irruptiondays.genealogy.dao;

import lombok.extern.slf4j.Slf4j;
import org.irruptiondays.genealogy.EntityCreator;
import org.irruptiondays.genealogy.GenprojApplication;
import org.irruptiondays.genealogy.domain.MiscData;
import org.irruptiondays.genealogy.domain.Person;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Set;

/**
 * Created by TValentine on 5/7/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = GenprojApplication.class)
@WebAppConfiguration
@Transactional
@Slf4j
public class MiscDataRepositoryTest {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private MiscDataRepository miscDataRepository;

    private Person person;

    @Before
    public void setup() {
        person = personRepository.save(EntityCreator.createPerson("Bobby"));
    }

    @Test
    public void testFindByPerson() {
        miscDataRepository.save(new MiscData(person, "Did Stuff", new Date(), "Stuff"));
        Set<MiscData> miscDataObjects = miscDataRepository.findByPerson(person);
    }

    @Test
    public void testLongDataString() {
        miscDataRepository.save(new MiscData(person, "Did Stuff", new Date(), EntityCreator.reallyLongStringGenerator()));
    }


}
