package org.irruptiondays.genealogy.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.irruptiondays.genealogy.common.FamilyBranch;

import javax.persistence.*;
import java.util.*;
import lombok.Data;

/**
 * A person, or a node in a family tree
 */
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
    private FamilyOfOrigin familyOfOrigin;*/

    /*private Map<String, String> miscData = new HashMap<>(0);*/
}
