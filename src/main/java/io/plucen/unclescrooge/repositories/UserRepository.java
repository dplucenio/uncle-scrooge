package io.plucen.unclescrooge.repositories;

import io.plucen.unclescrooge.entities.User;
import io.plucen.unclescrooge.repositories.withinsert.WithInsert;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, UUID>, WithInsert<User> {

  @Query("select * from application_user where email = :email")
  Optional<User> findByEmail(@Param("email") String email);

  @Query(
      "select u.id, u.email from application_user u "
          + "join application_user_account ua on u.id = ua.application_user "
          + "join account a on a.id = ua.account where a.id = :accountId")
  Iterable<User> findAllByAccountId(@Param("accountId") UUID accountId);
}
