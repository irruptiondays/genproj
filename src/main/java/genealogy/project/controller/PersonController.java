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

    @RequestMapping(value = "/person", method = RequestMethod.POST)
    public @ResponseBody PersonDTO createPerson(@RequestBody Person person) {
        /*Person person = new Person();
        person.setBirthdate(new Date());
        person.setBirthplace("Philly");
        person.setCurrentOrLateHome("Btown");
        person.setFirstName("Fred");
        person.setMiddleNames("Bob Joe");
        person.setLastName("Smith");
        person.setSuffix("III");*/
        return new PersonDTO(personRepository.save(person));
    }

    @RequestMapping(value = "/person/parent/{parentId}/child/{childId}", method = RequestMethod.PUT)
    public @ResponseBody PersonDTO setParent(@PathVariable Long parentId, @PathVariable Long childId) {
        if (GenealogyTools.invalidId(parentId) || GenealogyTools.invalidId(childId)) {
            // throw exception. make one and throw it here.
        }

        Person father = personRepository.findOne(parentId);
        Person child = personRepository.findOne(childId);

        if (father == null || child == null) {
            // throw an exception here.
        }

        child.setFather(father);
        return new PersonDTO(personRepository.save(child));

    }

}
