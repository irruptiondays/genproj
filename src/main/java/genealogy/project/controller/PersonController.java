package genealogy.project.controller;

import genealogy.project.dao.PersonRepository;
import genealogy.project.domain.Person;
import genealogy.project.dto.PersonDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
    public List<PersonDTO> getAllPersons() {
        List<PersonDTO> persons = new ArrayList<>();
        personRepository.findAll().forEach(item-> persons.add(new PersonDTO(item)));
        return persons;
    }

    @RequestMapping(value = "/person", method = RequestMethod.POST)
    public void createPerson() {
        Person person = new Person();
        person.setBirthdate(new Date());
        person.setBirthplace("Philly");
        person.setCurrentOrLateHome("Btown");
        person.setFirstName("Fred");
        person.setMiddleNames("Bob Joe");
        person.setLastName("Smith");
        person.setSuffix("III");
        personRepository.save(person);
    }
}
