package org.irruptiondays.genealogy.service;

import org.irruptiondays.genealogy.dao.MarriageRepository;
import org.irruptiondays.genealogy.dao.MiscDataRepository;
import org.irruptiondays.genealogy.dao.PersonRepository;
import org.irruptiondays.genealogy.domain.Marriage;
import org.irruptiondays.genealogy.model.MarriageSummary;
import org.irruptiondays.genealogy.domain.MiscData;
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
    private MarriageRepository marriageRepository;
    private MiscDataRepository miscDataRepository;


    @Autowired
    public PersonService(PersonRepository personRepository, MarriageRepository marriageRepository, MiscDataRepository miscDataRepository) {
        this.personRepository = personRepository;
        this.marriageRepository = marriageRepository;
        this.miscDataRepository = miscDataRepository;
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

    /**
     * Sets parents for a person with only IDs.
     * If id == 0, no parent is set and any previous parent is removed.
     * @param originId The is of the person who is having his parents set
     * @param fatherId The id of the father
     * @param motherId The id of the mother
     * @return Person The person who now has parents sets
     * @throws Exception
     */
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

    public void deletePersonById(Long personId) {
        Person person = personRepository.findOne(personId);
        deleteMarriagesByPerson(person);
        deleteMiscDataByPerson(person);
        personRepository.delete(person);
    }

    public void deleteMiscDataByPerson(Person person) {
        Set<MiscData> miscData = miscDataRepository.findByPerson(person);
        if (miscData != null && miscData.size() > 0) {
            miscData.forEach(m -> miscDataRepository.delete(m));
        }
    }

    public void deleteMarriagesByPerson(Person person) {
        Set<Marriage> marriages = marriageRepository.getMarriagesByPerson(person);
        if (marriages != null && marriages.size() > 0) {
            marriages.forEach(m -> marriageRepository.delete(m));
        }
    }

    public Set<MarriageSummary> getSpousesForPersonId(long id) {
        Set<Marriage> marriages = marriageRepository.getMarriagesByPersonId(id);
        Set<MarriageSummary> marriageSummaries = new HashSet<>();
        if (marriages != null) {
            marriages.stream().forEach(m -> {
                Person spouse = getSpouseByPersonId(id, m);
                marriageSummaries.add(new MarriageSummary(m.getId(), spouse, m.getDate()));
            });
        }
        return marriageSummaries;
    }

    private Person getSpouseByPersonId(long id, Marriage marriage) {
        return id != marriage.getSpouse1().getId()
                ? marriage.getSpouse1()
                : marriage.getSpouse2();
    }
}
