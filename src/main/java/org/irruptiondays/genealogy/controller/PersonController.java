package org.irruptiondays.genealogy.controller;

import lombok.extern.slf4j.Slf4j;
import org.irruptiondays.genealogy.common.FamilyBranch;
import org.irruptiondays.genealogy.dao.MarriageRepository;
import org.irruptiondays.genealogy.dao.PersonRepository;
import org.irruptiondays.genealogy.domain.Marriage;
import org.irruptiondays.genealogy.domain.MarriageSummary;
import org.irruptiondays.genealogy.domain.Person;
import org.irruptiondays.genealogy.service.PersonService;
import org.irruptiondays.genealogy.util.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Rest endpoints for CRUD transactions (persons).
 */
@Controller
@RequestMapping(value = {"/person", "/person/" })
@Slf4j
public class PersonController {

    private PersonRepository personRepository;
    private PersonService personService;
    private MarriageRepository marriageRepository;

    @Autowired
    public PersonController(PersonRepository personRepository, PersonService personService, MarriageRepository marriageRepository) {
        this.personRepository = personRepository;
        this.personService = personService;
        this.marriageRepository = marriageRepository;
    }

    /**
     * Get all persons regardless of family branch
     * @return List of persons
     */
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ResponseBody
    public List<Person> getAllPersons() {
        List<Person> persons = new ArrayList<>();
        personRepository.getAllPersonsOrderByLastNameAscFirstNameAsc().forEach(persons::add);
        return persons;
    }

    /**
     * Get all persons by family branch (paternal, maternal, common)
     * Paternal or maternal will always include common
     * @param familyBranch
     * @return List of persons
     */
    @RequestMapping(value = "/all/{familyBranch}", method = RequestMethod.GET)
    @ResponseBody
    public List<Person> getAllPersonsByFamilyBranch(@PathVariable FamilyBranch familyBranch) {
        List<Person> persons = new ArrayList<>();
        personRepository.getPersonByFamilyBranch(familyBranch).forEach(persons::add);
        personRepository.getPersonByFamilyBranch(FamilyBranch.COMMON).forEach(persons::add);
        return persons;
    }

    /**
     * Get a unique person by id
     * @param personId
     * @return Person
     */
    @RequestMapping(value = "/{personId}", method = RequestMethod.GET)
    @ResponseBody
    public Person getPersonById(@PathVariable Long personId) {
        return personRepository.findOne(personId);
    }

    /**
     * Create a new person
     * @param person
     * @return
     */
    @RequestMapping(value = "", method = {RequestMethod.POST, RequestMethod.PUT})
    @ResponseBody
    public Person createPerson(@RequestBody Person person) {
        return personRepository.save(person);
    }

    /**
     * Delete a person from the project
     * @param personId
     */
    @RequestMapping(value = "/{personId}", method = RequestMethod.DELETE)
    @ResponseBody
    public void deletePerson(@PathVariable Long personId) {
        personService.deletePersonById(personId);
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
    @RequestMapping(value = "/origin/{originId}/{fatherId}/{motherId}", method = {RequestMethod.POST, RequestMethod.PUT})
    @ResponseBody
    public Person setParents(@PathVariable Long originId,
                           @PathVariable Long fatherId,
                           @PathVariable Long motherId) throws Exception {
        return personService.setParents(originId, fatherId, motherId);
    }

    @RequestMapping(value = "/marriage", method = {RequestMethod.POST, RequestMethod.PUT})
    @ResponseBody
    public Marriage addMarriage(@RequestBody Marriage marriage) {
        return marriageRepository.save(marriage);
    }


    @RequestMapping(value = "/marriage/{id}", method = {RequestMethod.GET})
    @ResponseBody
    public Set<MarriageSummary> getSpousesByPersonId(@PathVariable long id) {
        return personService.getSpousesForPersonId(id);
    }

    @RequestMapping(value = "/marriage", method = {RequestMethod.GET})
    @ResponseBody
    public Set<Marriage> getMarriagesByPerson(@RequestBody Person person) {
        return marriageRepository.getMarriagesByPerson(person);
    }

    /**
     * Delete a marriage by the marriage id
     * @param marriageId The id of the marriage
     */
    @RequestMapping(value = "/marriage/{marriageId}", method = {RequestMethod.DELETE})
    @ResponseBody
    public void deleteMarriageById(@PathVariable long marriageId) {
        marriageRepository.delete(marriageId);
    }

    @RequestMapping(value = "/marriage/all", method = {RequestMethod.GET})
    @ResponseBody
    public Set<Marriage> getMarriagesByPerson() {
        return Tools.iterableToSet(marriageRepository.findAll());
    }

}
