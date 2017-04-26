package org.irruptiondays.genealogy.controller;

import org.irruptiondays.genealogy.dao.PersonRepository;
import org.irruptiondays.genealogy.domain.Person;
import org.irruptiondays.genealogy.util.GenealogyTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TValentine on 5/6/16.
 */
@Controller
public class PersonController {

    private PersonRepository personRepository;

    @Autowired
    public PersonController(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @RequestMapping(value = "/person", method = RequestMethod.GET)
    public @ResponseBody List<Person> getAllPersons() {
        List<Person> persons = new ArrayList<>();
        personRepository.findAll().forEach(item-> persons.add(item));
        return persons;
    }

    @RequestMapping(value = "/person/{personId}", method = RequestMethod.GET)
    public @ResponseBody Person getPersonById(@PathVariable Long personId) {
        return personRepository.findOne(personId);
    }

    @RequestMapping(value = "/person", method = {RequestMethod.POST, RequestMethod.PUT})
    public @ResponseBody Person createPerson(@RequestBody Person person) {
        return personRepository.save(person);
    }

    @RequestMapping(value = "/person/parent/{parentId}/child/{childId}/type/{parentType}", method = RequestMethod.PUT)
    public @ResponseBody Person setParent(@PathVariable Long parentId,
                                             @PathVariable Long childId, @PathVariable String parentType) {
        if (GenealogyTools.invalidId(parentId) || GenealogyTools.invalidId(childId)) {
            // throw exception. make one and throw it here.
        }

        Person parent = personRepository.findOne(parentId);
        Person child = personRepository.findOne(childId);

        if (parent == null || child == null) {
            // throw an exception here.
        }

        if ("FATHER".equals(parentType)) {
            child.setFather(parent);
        } else if ("MOTHER".equals(parentType)) {
            child.setMother(parent);
        } else {
            // throw exception
        }

        child.setFather(parent);
        return personRepository.save(child);
    }

}
