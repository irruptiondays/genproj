package org.irruptiondays.genealogy.service;

import lombok.extern.slf4j.Slf4j;
import org.irruptiondays.genealogy.dao.MarriageRepository;
import org.irruptiondays.genealogy.dao.PersonRepository;
import org.irruptiondays.genealogy.domain.Marriage;
import org.irruptiondays.genealogy.domain.Person;
import org.irruptiondays.genealogy.model.MarriageSummary;
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
@Slf4j
public class FileService {

    private PersonService personService;
    private PersonRepository personRepository;
    private MarriageRepository marriageRepository;

    private Map<Long, PersonPageModel> personMap = new HashMap<>();

    @Autowired
    public FileService(PersonService personService, PersonRepository personRepository, MarriageRepository marriageRepository) {
        this.personRepository = personRepository;
        this.personService = personService;
        this.marriageRepository = marriageRepository;
    }

    public Set<PersonPageModel> getAllPersonsAsPersonPageModels() {
        Set<Person> personSet = Tools.iterableToSet(personRepository.findAll());
        Set<PersonPageModel> personPageModelSet = new HashSet<>();
        personSet.forEach(p -> personPageModelSet.add(getPersonPageModelForPerson(p)));
        return personPageModelSet;
    }

    private PersonPageModel getPersonPageModelForPerson(Person person) {
        PersonPageModel personPageModel = new PersonPageModel();
        BeanUtils.copyProperties(person, personPageModel);

        Set<Person> children = personRepository.getChildrenOfPerson(person);
        personPageModel.setChildrenIds(getIdsForSet(children));

        Set<Person> siblings = personService.getSiblingsByPerson(person);
        personPageModel.setSiblingIds(getIdsForSet(siblings));

        setCurrentSpouseData(personPageModel);

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

    private void setCurrentSpouseData(PersonPageModel personPageModel) {
        MarriageSummary summary = getCurrentMarriageForPerson(personPageModel.getId());
        personPageModel.setCurrentSpouseId(summary != null ? summary.getSpouse().getId() : 0);
        personPageModel.setMarriageAnniversary(summary != null ? summary.getDate() : null);
    }

    private MarriageSummary getCurrentMarriageForPerson(long id) {
        Set<Marriage> marriages = marriageRepository.getMarriagesByPersonId(id);
        if (marriages != null) {
            for (Marriage m : marriages) {
                if (m.isMostRecent()) {
                    Person p = personService.getSpouseByPersonId(id, m);
                    if (p == null) {
                        log.error("No person when search for marriage!");
                        return null;
                    }
                    return new MarriageSummary(m.getId(), p, m.getDate(), m.isMostRecent());
                }
            }
        }
        return null;
    }

}
