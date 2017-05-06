package org.irruptiondays.genealogy.dao;

import org.irruptiondays.genealogy.domain.Marriage;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by TValentine on 5/6/17.
 */
@Repository
public interface MarriageRepository extends CrudRepository<Marriage, Long> {
}
