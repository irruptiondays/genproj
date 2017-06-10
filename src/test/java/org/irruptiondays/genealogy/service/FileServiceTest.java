package org.irruptiondays.genealogy.service;

import org.irruptiondays.genealogy.EntityCreator;
import org.irruptiondays.genealogy.GenprojApplication;
import org.irruptiondays.genealogy.dao.MarriageRepository;
import org.irruptiondays.genealogy.dao.PersonRepository;
import org.irruptiondays.genealogy.domain.Marriage;
import org.irruptiondays.genealogy.domain.Person;
import org.irruptiondays.genealogy.model.PersonPageModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Set;

import static org.junit.Assert.assertEquals;

/**
 * Tests the FileService
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = GenprojApplication.class)
@WebAppConfiguration
@Transactional
public class FileServiceTest {

    @Autowired
    private FileService fileService;
    @Autowired
    private PersonService personService;
    @Autowired
    private MarriageRepository marriageRepository;
    @Autowired
    private PersonRepository personRepository;

    @Test
    public void testGetAllPersonsAsPersonPageModels() throws Exception {
        Person dad = personRepository.save(EntityCreator.createPerson("Dad"));
        Person mom = personRepository.save(EntityCreator.createPerson("Mom"));
        Person child1 = personRepository.save(EntityCreator.createPerson("Child1"));
        Person child2 = personRepository.save(EntityCreator.createPerson("Child2"));
        Person spouse1a = personRepository.save(EntityCreator.createPerson("Spouse1a"));
        Person spouse1b = personRepository.save(EntityCreator.createPerson("Spouse1b"));
        Person spouse2 = personRepository.save(EntityCreator.createPerson("Spouse2"));
        Person grandchild1a = personRepository.save(EntityCreator.createPerson("Grandchild1a"));
        Person grandchild1b = personRepository.save(EntityCreator.createPerson("Grandchild1b"));
        Person grandchild2 = personRepository.save(EntityCreator.createPerson("Grandchild2"));
        Person grandchild21 = personRepository.save(EntityCreator.createPerson("Grandchild2-1"));

        marriageRepository.save(new Marriage(dad, mom, new Date(-1111111)));
        marriageRepository.save(new Marriage(child1, spouse1a, new Date(111)));
        marriageRepository.save(new Marriage(child1, spouse1b, new Date(111222)));
        marriageRepository.save(new Marriage(child2, spouse2, new Date(11122)));

        personService.setParents(child1.getId(), dad.getId(), mom.getId());
        personService.setParents(child2.getId(), dad.getId(), mom.getId());

        personService.setParents(grandchild1a.getId(), child1.getId(), spouse1a.getId());
        personService.setParents(grandchild1b.getId(), child1.getId(), spouse1b.getId());

        personService.setParents(grandchild2.getId(), child2.getId(), spouse2.getId());
        personService.setParents(grandchild21.getId(), child2.getId(), spouse2.getId());

        Set<PersonPageModel> personPageModelSet = fileService.getAllPersonsAsPersonPageModels();
        assertEquals(11, personPageModelSet.size());
    }
}
