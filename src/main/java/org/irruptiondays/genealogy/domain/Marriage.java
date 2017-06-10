package org.irruptiondays.genealogy.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * Table of marriages of persons
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Marriage {

    public Marriage(Person spouse1, Person spouse2, Date date, boolean mostRecent) {
        this.spouse1 = spouse1;
        this.spouse2 = spouse2;
        this.date = date;
        this.mostRecent = mostRecent;
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
    private boolean mostRecent;

}
