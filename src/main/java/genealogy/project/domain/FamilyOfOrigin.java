package genealogy.project.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by TValentine on 12/8/15.
 */
@Entity
public class FamilyOfOrigin {
    @Id
    @GeneratedValue
    private long id;
    private Person ego;
    private Person mother;
    private Person father;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Person getEgo() {
        return ego;
    }

    public void setEgo(Person ego) {
        this.ego = ego;
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
