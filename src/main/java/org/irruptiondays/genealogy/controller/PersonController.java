package org.irruptiondays.genealogy.controller;

import org.irruptiondays.genealogy.common.FamilyBranch;
import org.irruptiondays.genealogy.dao.PersonRepository;
import org.irruptiondays.genealogy.domain.Person;
import org.irruptiondays.genealogy.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Rest endpoints for CRUD transactions (persons).
 */
@Controller
@RequestMapping(value = {"/person", "/person/" }, method = RequestMethod.GET)
public class PersonController {

    private PersonRepository personRepository;
    private PersonService personService;

    @Autowired
    public PersonController(PersonRepository personRepository, PersonService personService) {
        this.personRepository = personRepository;
        this.personService = personService;
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

}
