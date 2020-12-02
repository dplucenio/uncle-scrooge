package io.plucen.unclescrooge.repositories;

import io.plucen.unclescrooge.entities.User;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends CrudRepository<User, UUID> {
  public Optional<User> findByEmail(String email);
}
