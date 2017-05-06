package org.irruptiondays.genealogy.domain;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by TValentine on 12/8/15.
 */
//@Entity
@Data
public class Marriage {
    @Id
    @GeneratedValue
    private long id;
    @OneToOne
    @JoinColumn(name="marriageId")
    private Person spouse1;
    @OneToOne
    @JoinColumn(name="spouse1Id")
    private Person spouse2;
    @OneToOne
    @JoinColumn(name="spouse2Id")
    private GenDate date;

}
