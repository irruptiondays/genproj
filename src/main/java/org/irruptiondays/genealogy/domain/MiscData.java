package org.irruptiondays.genealogy.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by TValentine on 5/6/17.
 */
@Entity
@Data
public class MiscData {
    @Id
    @GeneratedValue
    private long id;
    @OneToOne
    private Person person;
    private Date date;
    @Column(length=1000)
    @Lob
    private String text;
}
