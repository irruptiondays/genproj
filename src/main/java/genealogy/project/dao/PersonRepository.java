package genealogy.project.dao;

import genealogy.project.domain.Person;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;

/**
 * Created by TValentine on 4/30/16.
 */
@RestResource(path = "persons", rel="persons")
public interface PersonRepository extends CrudRepository<Person, Long> {
}
