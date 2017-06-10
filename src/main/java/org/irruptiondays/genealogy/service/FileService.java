package org.irruptiondays.genealogy.service;

import org.irruptiondays.genealogy.dao.PersonRepository;
import org.irruptiondays.genealogy.domain.Person;
import org.irruptiondays.genealogy.model.PersonPageModel;
import org.irruptiondays.genealogy.util.Tools;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Service class to generate the HTML files.
 */
@Service
public class FileService {

    private PersonService personService;
    private PersonRepository personRepository;

    private Map<Long, PersonPageModel> personMap = new HashMap<>();

    @Autowired
    public FileService(PersonService personService, PersonRepository personRepository) {
        this.personRepository = personRepository;
        this.personService = personService;
    }

    public Set<PersonPageModel> getAllPersonsAsPersonPageModels() {
        Set<Person> personSet = Tools.iterableToSet(personRepository.findAll());
        Set<PersonPageModel> personPageModelSet = new HashSet<>();
        personSet.stream().forEach(p -> personPageModelSet.add(getPersonPageModelForPerson(p)));
        return personPageModelSet;
    }

    private PersonPageModel getPersonPageModelForPerson(Person person) {
        PersonPageModel personPageModel = new PersonPageModel();
        BeanUtils.copyProperties(person, personPageModel);

        Set<Person> children = personRepository.getChildrenOfPerson(person);
        personPageModel.setChildrenIds(getIdsForSet(children));

        Set<Person> siblings = personService.getSiblingsByPerson(person);
        personPageModel.setSiblingIds(getIdsForSet(siblings));

        return personPageModel;
    }

    private Set<Long> getIdsForSet(Set<Person> personSet) {
        if (personSet == null) {
            return new HashSet<>(0);
        }
        return personSet.stream().map(p -> p.getId()).collect(Collectors.toSet());
    }

    public Map<Long, PersonPageModel> getMap(Set<PersonPageModel> personPageModelSet) {
        Map<Long, PersonPageModel> personPageModelMap = new HashMap<>();
        personPageModelSet.forEach(p -> personPageModelMap.put(p.getId(), p));
        return personPageModelMap;
    }

}
