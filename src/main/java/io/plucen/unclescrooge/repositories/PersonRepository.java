package io.plucen.unclescrooge.repositories;

import io.plucen.unclescrooge.entities.Person;
import io.plucen.unclescrooge.repositories.withinsert.WithInsert;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends CrudRepository<Person, UUID>, WithInsert<Person> {

  @Query("select * from person where email = :email")
  Optional<Person> findByEmail(@Param("email") String email);
}
