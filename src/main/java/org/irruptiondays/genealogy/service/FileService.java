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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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

    @Autowired
    public FileService(PersonService personService, PersonRepository personRepository, MarriageRepository marriageRepository) {
        this.personRepository = personRepository;
        this.personService = personService;
        this.marriageRepository = marriageRepository;
    }

    public void generateProject() throws IOException {

        Map<Long, PersonPageModel> personMap = getMap(getAllPersonsAsPersonPageModels());

        String directoryPath = "target/projectOutput-" + new Date().getTime() + "/";

        File directory = new File(directoryPath + new Date().getTime() + "/");

        if (!directory.exists()) {
            if (!directory.mkdirs()) {
                log.error("Could not create directory!");
                return;
            }
        }


        // TODO siblings are missing
        for (PersonPageModel p : personMap.values()) {

            File file = new File(directoryPath + p.getFileName() +  ".html");
            file.createNewFile();

            FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
            BufferedWriter bw = new BufferedWriter(fw);

            String properlyFormattedName = p.getPrintedNameTitleFormat();


            bw.write("<html>");
            bw.newLine();
            bw.write("  <head>");

            bw.newLine();

            bw.write("    <title>" + properlyFormattedName + " | Genealogy Project | irruptiondays</title>");

            bw.write("    <link href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css\" rel=\"stylesheet\" integrity=\"sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u\" crossorigin=\"anonymous\" />");

            bw.write("    <script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js\" integrity=\"sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa\" crossorigin=\"anonymous\"></script>");



            bw.newLine();

            bw.write("  </head>");

            bw.newLine();

            bw.write("  <body>");

            bw.newLine();

            bw.write("    <div class=\"container\">");

            bw.newLine();

            bw.write("      <h1>" + properlyFormattedName + "</h1>");

            bw.newLine();

            bw.write("      <div>\n" +
                    "              <span><strong>Date of Birth:</strong> " + Tools.dateString(p.getBirthdate()) + "</span><br/>\n" +
                    "              <span><strong>Birthplace:</strong> " + p.getBirthplace() + "</span><br/>\n" +
                    "          </div>");


            if (p.areParentsKnown()) {
                bw.write("          <div>\n");
                if (p.isFatherKnown()) {
                    bw.write("            <span><strong>Father: </strong>" + createPersonLink(personMap.get(p.getFatherId())) + "</span><br/>\n");
                }
                if (p.isMotherKnown()) {
                    bw.write("            <span><strong>Mother: </strong>" + createPersonLink(personMap.get(p.getMotherId())) + "</span><br/>\n");
                }
                bw.write("          </div>");
            }

            bw.newLine();

            if (p.hasSiblings()) {

                bw.write("          <div>\n" +
                        "                  <span><strong>Siblings: </strong></span>\n" +
                        "                  <ul style=\"list-style: none;\">\n");

                for (Long id : p.getSiblingIds()) {
                    bw.write("              <li>" + createPersonLink(personMap.get(id)) + "</li>");
                }

                bw.write("                  </ul>\n" +
                        "              </div>");

            }

            bw.newLine();

            if (p.hasChildren()) {

                bw.write("          <div>\n" +
                        "            <span><strong>Children: </strong></span>\n" +
                        "            <ul style=\"list-style: none;\">\n");

                for (Long id : p.getChildrenIds()) {
                    bw.write("              <li>" + createPersonLink(personMap.get(id)) + "</li>");
                }

                bw.write("            </ul>\n" +
                        "              </div>");

            }

            if (p.hasSpouse()) {
                bw.write("              <span><strong>Married: </strong>" +
                        createPersonLink(personMap.get(p.getCurrentSpouseId())) +
                        " (" + Tools.dateString(p.getMarriageAnniversary()) +
                        ")</span><br/>\n");
            }

            if (p.isDead()) {
                bw.write("          <div>\n" +
                        "                  <span><strong>Date of Death:</strong> " + Tools.dateString(p.getDeathdate()) + "</span><br/>\n" +
                        "              </div>");
            }

            if (p.getCurrentOrLateHome() != null && !p.getCurrentOrLateHome().isEmpty()) {
                bw.write("          <div>\n" +
                        "                  <span><strong>Residence:</strong> " + p.getCurrentOrLateHome() + "</span><br/>\n" +
                        "              </div>");
            }



            bw.write("  </div>");

            bw.write("  </body>");

            bw.newLine();

            bw.write("</html>");
            bw.newLine();


            bw.close();
            fw.close();
        }
    }

    private String createPersonLink(PersonPageModel person) {
        if (person == null) {
            return "";
        }
        return "<a href=\"" + person.getFileName() + ".html\">" + person.getPrintedNameTitleFormat() + "</a>";
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
