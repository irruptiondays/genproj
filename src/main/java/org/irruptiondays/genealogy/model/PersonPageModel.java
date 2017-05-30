package org.irruptiondays.genealogy.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.irruptiondays.genealogy.domain.MiscData;
import org.irruptiondays.genealogy.domain.Person;

import java.util.HashSet;
import java.util.Set;

/**
 * Models a person's entire family when generating pages.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonPageModel {

    private Person person;
    private Set<Person> siblings = new HashSet<>(0);
    private Set<Person> children = new HashSet<>(0);
    private Set<MiscData> miscData = new HashSet<>(0);
}
