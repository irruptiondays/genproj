package genealogy.project.domain;

import genealogy.project.common.FamilyBranch;

import javax.persistence.*;
import java.util.*;

/**
 * Created by TValentine on 12/8/15.
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
    private GenDate birthdate;
    private GenDate deathdate;
    private GenLocale birthplace;
    private GenLocale currentOrLateHome;
    private FamilyBranch familyBranch;
    @OneToOne
    @JoinColumn(name="personId")
    private FamilyOfOrigin familyOfOrigin;
    @OneToMany(cascade= CascadeType.ALL)
    @JoinColumn(name="personId")
    private Set<Marriage> marriages = new HashSet<>(0);
    private Map<String, String> miscData = new HashMap<>(0);

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

    public GenDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(GenDate birthdate) {
        this.birthdate = birthdate;
    }

    public GenDate getDeathdate() {
        return deathdate;
    }

    public void setDeathdate(GenDate deathdate) {
        this.deathdate = deathdate;
    }

    public GenLocale getBirthplace() {
        return birthplace;
    }

    public void setBirthplace(GenLocale birthplace) {
        this.birthplace = birthplace;
    }

    public GenLocale getCurrentOrLateHome() {
        return currentOrLateHome;
    }

    public void setCurrentOrLateHome(GenLocale currentOrLateHome) {
        this.currentOrLateHome = currentOrLateHome;
    }

    public FamilyBranch getFamilyBranch() {
        return familyBranch;
    }

    public void setFamilyBranch(FamilyBranch familyBranch) {
        this.familyBranch = familyBranch;
    }

    public FamilyOfOrigin getFamilyOfOrigin() {
        return familyOfOrigin;
    }

    public void setFamilyOfOrigin(FamilyOfOrigin familyOfOrigin) {
        this.familyOfOrigin = familyOfOrigin;
    }

    public Set<Marriage> getMarriages() {
        return marriages;
    }

    public void setMarriages(Set<Marriage> marriages) {
        this.marriages = marriages;
    }

    public Map<String, String> getMiscData() {
        return miscData;
    }

    public void setMiscData(Map<String, String> miscData) {
        this.miscData = miscData;
    }
}
