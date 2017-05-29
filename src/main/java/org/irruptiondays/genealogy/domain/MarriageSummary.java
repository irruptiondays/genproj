package org.irruptiondays.genealogy.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Created by TValentine on 5/28/17.
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
