package org.irruptiondays.genealogy.dao;

import org.irruptiondays.genealogy.domain.MiscData;
import org.irruptiondays.genealogy.domain.Person;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * Created by TValentine on 5/6/17.
 */
@Repository
public interface MiscDataRepository extends CrudRepository<MiscData, Long> {

    Set<MiscData> findByPerson(Person person);
}
