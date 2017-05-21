package org.irruptiondays.genealogy.service;

import org.irruptiondays.genealogy.dao.PersonRepository;
import org.irruptiondays.genealogy.domain.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by tvalentine on 5/8/2017.
 */
@Service
public class PersonService {

    private PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public Set<Person> getSiblingsByPerson(Person person) {
        Person personFromDb = personRepository.findOne(person.getId());
        Set<Person> siblings = new HashSet<>();

        if (personFromDb.getFather() != null) {
            siblings.addAll(personRepository.getChildrenOfPerson(personFromDb.getFather()));
        }

        if (personFromDb.getMother() != null) {
            siblings.addAll(personRepository.getChildrenOfPerson(personFromDb.getMother()));
        }

        // Should always be true, but checking anyway
        if (siblings.contains(person)) {
            siblings.remove(person);
        }

        return siblings;
    }

    public Map<String, List<Person>> getAllPersonsGroupedByLastname() {
        // TODO implement this
        return null;
    }

    public Person setParents(Long originId, Long fatherId, Long motherId) throws Exception {
        if (originId == 0) {
            // TODO make exceptions for stuff like this
            throw new Exception();
        }
        Person origin = personRepository.findOne(originId);

        if (fatherId != 0) {
            Person father = personRepository.findOne(fatherId);
            origin.setFather(father);
        } else {
            origin.setFather(null);
        }

        if (motherId != 0) {
            Person mother = personRepository.findOne(motherId);
            origin.setMother(mother);
        } else {
            origin.setMother(null);
        }

        return personRepository.save(origin);
    }
}
