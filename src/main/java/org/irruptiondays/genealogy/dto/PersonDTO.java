package org.irruptiondays.genealogy.dto;

import org.irruptiondays.genealogy.common.FamilyBranch;
import org.irruptiondays.genealogy.domain.Person;

import java.util.Date;

/**
 * Created by TValentine on 5/6/16.
 */
public class PersonDTO {
    private long id;
    private String firstName;
    private String middleNames;
    private String lastName;
    private String suffix;
    private String maidenName;
    private Date birthdate;
    private Date deathdate;
    private String birthplace;
    private String currentOrLateHome;
    private FamilyBranch familyBranch;

    private long motherId;
    private long fatherId;

    public PersonDTO(Person person) {
        if (person == null) {
            // throw exception
        }
        this.id = person.getId();
        this.firstName = person.getFirstName();
        this.middleNames = person.getMiddleNames();
        this.lastName = person.getLastName();
        this.suffix = person.getSuffix();
        this.maidenName = person.getMaidenName();
        this.birthdate = person.getBirthdate();
        this.deathdate = person.getDeathdate();
        this.birthplace = person.getBirthplace();
        this.currentOrLateHome = person.getCurrentOrLateHome();
        this.familyBranch = person.getFamilyBranch();
        if (person.getMother() != null) {
            this.motherId = person.getMother().getId();
        }
        if (person.getFather() != null) {
            this.fatherId = person.getFather().getId();
        }
    }

    public PersonDTO() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleNames() {
        return middleNames;
    }

    public void setMiddleNames(String middleNames) {
        this.middleNames = middleNames;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getMaidenName() {
        return maidenName;
    }

    public void setMaidenName(String maidenName) {
        this.maidenName = maidenName;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public Date getDeathdate() {
        return deathdate;
    }

    public void setDeathdate(Date deathdate) {
        this.deathdate = deathdate;
    }

    public String getBirthplace() {
        return birthplace;
    }

    public void setBirthplace(String birthplace) {
        this.birthplace = birthplace;
    }

    public String getCurrentOrLateHome() {
        return currentOrLateHome;
    }

    public void setCurrentOrLateHome(String currentOrLateHome) {
        this.currentOrLateHome = currentOrLateHome;
    }

    public FamilyBranch getFamilyBranch() {
        return familyBranch;
    }

    public void setFamilyBranch(FamilyBranch familyBranch) {
        this.familyBranch = familyBranch;
    }

    public long getMotherId() {
        return motherId;
    }

    public void setMotherId(long motherId) {
        this.motherId = motherId;
    }

    public long getFatherId() {
        return fatherId;
    }

    public void setFatherId(long fatherId) {
        this.fatherId = fatherId;
    }

    public Person toDomain() {
        Person person = new Person();
        person.setId(id);
        person.setFirstName(firstName);
        person.setMiddleNames(middleNames);
        person.setLastName(lastName);
        person.setSuffix(suffix);
        person.setMaidenName(maidenName);
        person.setBirthdate(birthdate);
        person.setDeathdate(deathdate);
        person.setBirthplace(birthplace);
        person.setCurrentOrLateHome(currentOrLateHome);
        person.setFamilyBranch(familyBranch);
        return person;
    }
}
