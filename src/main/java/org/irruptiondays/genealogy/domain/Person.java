package org.irruptiondays.genealogy.domain;

import lombok.*;
import org.irruptiondays.genealogy.common.FamilyBranch;

import javax.persistence.*;
import java.util.*;

import org.irruptiondays.genealogy.common.Gender;

/**
 * A person, or a node in a family tree
 */
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
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

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
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

    @Transient
    public String getPrintedName() {
        StringBuffer sb = new StringBuffer();
        if (lastName != null) {
            sb.append(lastName).append(", ");
        }
        if (firstName != null) {
            sb.append(firstName).append(" ");
        }
        if (middleNames != null) {
            sb.append(middleNames).append(" ");
        }
        if (maidenName != null) {
            sb.append(maidenName).append(" ");
        }
        if (suffix != null) {
            sb.append(suffix);
        }

        return sb.toString();
    }

    @Transient
    public Long getFatherId() {
        return father != null ? father.getId() : 0;
    }

    @Transient
    public Long getMotherId() {
        return mother != null ? mother.getId() : 0;
    }
}
