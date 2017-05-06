package org.irruptiondays.genealogy.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by TValentine on 12/8/15.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Marriage {

    public Marriage(Person spouse1, Person spouse2, Date date) {
        this.spouse1 = spouse1;
        this.spouse2 = spouse2;
        this.date = date;
    }

    @Id
    @GeneratedValue
    private long id;
    @ManyToOne
    @JoinColumn(name = "spouse1")
    private Person spouse1;
    @ManyToOne
    @JoinColumn(name = "spouse2")
    private Person spouse2;
    private Date date;

}
