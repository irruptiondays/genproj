package org.irruptiondays.genealogy.dao;

import org.irruptiondays.genealogy.domain.Person;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

/**
 * Created by TValentine on 4/30/16.
 */
//@RestResource(path = "persons", rel="persons")
@Repository
public interface PersonRepository extends CrudRepository<Person, Long> {
}