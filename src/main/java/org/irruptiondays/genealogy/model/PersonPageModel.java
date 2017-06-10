package org.irruptiondays.genealogy.model;

import lombok.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Models a person's entire family when generating HTML pages.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonPageModel {

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
    private Long fatherId;
    private Long motherId;
    private Long currentSpouseId;
    private Set<Long> siblingIds = new HashSet<>();
    private Set<Long> childrenIds = new HashSet<>();

    /**
     * Generate a filename for a person.
     * Remove spaces, periods (initials and suffixes), nulls, apostrophes
     *
     * @return String The filename to generate
     */
    public String getFileName() {
        return (String.valueOf(id) + "-" +
                lastName + "-" + firstName + "-" +
                middleNames + "-" + maidenName +
                "-" + suffix)
                .replace("  ", " ")
                .replace(" ", "-")
                .replace("null", "")
                .replace("'", "")
                .replace(".", "")
                .toLowerCase();
    }
}
