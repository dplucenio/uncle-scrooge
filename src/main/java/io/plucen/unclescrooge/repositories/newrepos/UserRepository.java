package io.plucen.unclescrooge.repositories.newrepos;

import io.plucen.unclescrooge.entities.User;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, UUID>, WithInsert<User> {

  @Query("SELECT * FROM \"users\" WHERE email = :email")
  Optional<User> findByEmail(@Param("email") String email);
}
