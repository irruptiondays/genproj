package org.irruptiondays.genealogy.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * Object to store various events or facts about a person.
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MiscData {
    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    private Person person;
    private String displayName;
    private Date date;

    @Column(length=32000)
    private String text;


    public MiscData(Person person, String displayName, Date date, String text) {
        this.person = person;
        this.date = date;
        this.text = text;
    }
}
