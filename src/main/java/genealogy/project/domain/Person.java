package genealogy.project.domain;

import genealogy.project.common.FamilyBranch;

import javax.persistence.*;
import java.util.*;

/**
 * A person, or a node in a family tree
 */
@Entity
public class Person {
    @Id
    @GeneratedValue
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


    @OneToOne(
            targetEntity = Person.class,
            cascade = { CascadeType.REFRESH }
    )
    private Person mother;

    @OneToOne(
            targetEntity = Person.class,
            cascade = { CascadeType.REFRESH }
    )
    private Person father;
    //private Person father;
    /*@OneToOne
    @JoinColumn(name="egoId")
    private FamilyOfOrigin familyOfOrigin;
    @OneToMany(cascade= CascadeType.ALL)
    @JoinColumn(name="personId")
    private Set<Marriage> marriages = new HashSet<>(0);*/
    /*private Map<String, String> miscData = new HashMap<>(0);*/

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

    public Person getMother() {
        return mother;
    }

    public void setMother(Person mother) {
        this.mother = mother;
    }

    public Person getFather() {
        return father;
    }

    public void setFather(Person father) {
        this.father = father;
    }
}
