package org.irruptiondays.genealogy.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.irruptiondays.genealogy.domain.Person;

import java.util.Date;

/**
 * Models a spouse for the front end.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MarriageSummary {

    private long id;
    private Person spouse;
    private Date date;

}
