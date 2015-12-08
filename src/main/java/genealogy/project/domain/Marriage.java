package genealogy.project.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by TValentine on 12/8/15.
 */
@Entity
public class Marriage {
    @Id
    @GeneratedValue
    private long id;
    private Person spouse1;
    private Person spouse2;
    private GenDate date;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Person getSpouse1() {
        return spouse1;
    }

    public void setSpouse1(Person spouse1) {
        this.spouse1 = spouse1;
    }

    public Person getSpouse2() {
        return spouse2;
    }

    public void setSpouse2(Person spouse2) {
        this.spouse2 = spouse2;
    }

    public GenDate getDate() {
        return date;
    }

    public void setDate(GenDate date) {
        this.date = date;
    }
}
