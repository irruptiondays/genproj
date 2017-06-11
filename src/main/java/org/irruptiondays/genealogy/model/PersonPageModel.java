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
    private Date marriageAnniversary;
    private Set<Long> siblingIds = new HashSet<>();
    private Set<Long> childrenIds = new HashSet<>();

    /**
     * Generate a filename for a person.
     * Remove spaces, periods (initials and suffixes), nulls, apostrophes
     *
     * @return String The filename to generate
     */
    public String getFileName() {
        return (lastName + "-" + firstName + "-" +
                middleNames + "-" + maidenName +
                "-" + suffix + "-" +
                String.valueOf(id))
                .replace("  ", " ")
                .replace(" ", "-")
                .replace("---", "-")
                .replace("--", "-")
                .replace("null", "")
                .replace("'", "")
                .replace(".", "")
                .toLowerCase();
    }

    public String getPrintedNameTitleFormat() {
        StringBuffer sb = new StringBuffer();
        if (firstName != null) {
            sb.append(firstName).append(" ");
        }
        if (middleNames != null) {
            sb.append(middleNames).append(" ");
        }
        if (maidenName != null) {
            sb.append(maidenName).append(" ");
        }
        if (lastName != null) {
            sb.append(lastName).append(" ");
        }
        if (suffix != null) {
            sb.append(", ").append(suffix);
        }

        return sb.toString();
    }

    public boolean isFatherKnown() {
        return fatherId != null && fatherId != 0;
    }

    public boolean isMotherKnown() {
        return motherId != null && motherId != 0;
    }

    public boolean areParentsKnown() {
        return isFatherKnown() || isMotherKnown();
    }

    public boolean hasChildren() {
        return childrenIds != null && childrenIds.size() > 0;
    }

    public boolean hasSpouse() {
        return currentSpouseId != null && currentSpouseId != 0;
    }

    public boolean isDead() {
        return deathdate != null;
    }
}
