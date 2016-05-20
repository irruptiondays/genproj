package genealogy.project.controller;

import genealogy.project.dao.PersonRepository;
import genealogy.project.domain.Person;
import genealogy.project.dto.PersonDTO;
import genealogy.project.util.GenealogyTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by TValentine on 5/6/16.
 */
@Controller
public class PersonController {

    @Autowired
    public PersonRepository personRepository;

    @RequestMapping(value = "/person", method = RequestMethod.GET)
    public @ResponseBody List<PersonDTO> getAllPersons() {
        List<PersonDTO> persons = new ArrayList<>();
        personRepository.findAll().forEach(item-> persons.add(new PersonDTO(item)));
        return persons;
    }

    @RequestMapping(value = "/person", method = RequestMethod.GET)
    public @ResponseBody PersonDTO getPersonById(@PathVariable Long personId) {
        Person person = personRepository.findOne(personId);
        return new PersonDTO(person);
    }

    @RequestMapping(value = "/person", method = {RequestMethod.POST, RequestMethod.PUT})
    public @ResponseBody PersonDTO createPerson(@RequestBody Person person) {
        return new PersonDTO(personRepository.save(person));
    }

    @RequestMapping(value = "/person/parent/{parentId}/child/{childId}/type/{parentType}", method = RequestMethod.PUT)
    public @ResponseBody PersonDTO setParent(@PathVariable Long parentId,
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
        return new PersonDTO(personRepository.save(child));
    }

}
