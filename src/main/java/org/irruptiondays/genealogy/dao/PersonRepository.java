package org.irruptiondays.genealogy.dao;

import org.irruptiondays.genealogy.domain.Person;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

/**
 * Created by TValentine on 4/30/16.
 */
//@RestResource(path = "persons", rel="persons")
@Repository
public interface PersonRepository extends CrudRepository<Person, Long> {

    @Query("SELECT p FROM Person p WHERE p.father = :person OR p.mother = :person")
    Set<Person> getChildrenOfPerson(@Param("person") Person person);
}
