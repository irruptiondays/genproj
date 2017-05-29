package org.irruptiondays.genealogy.dao;

import org.irruptiondays.genealogy.domain.Marriage;
import org.irruptiondays.genealogy.domain.Person;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * Created by TValentine on 5/6/17.
 */
@Repository
public interface MarriageRepository extends CrudRepository<Marriage, Long> {

    @Query("SELECT m FROM Marriage m WHERE m.spouse1 = :person OR m.spouse2 = :person")
    Set<Marriage> getMarriagesByPerson(@Param("person") Person person);

    @Query("SELECT m FROM Marriage m WHERE m.spouse1.id = :personId OR m.spouse2.id = :personId")
    Set<Marriage> getMarriagesByPersonId(@Param("personId") long personId);
}
