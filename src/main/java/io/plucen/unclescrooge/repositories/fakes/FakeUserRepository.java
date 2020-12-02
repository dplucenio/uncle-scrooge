package io.plucen.unclescrooge.repositories.fakes;

import io.plucen.unclescrooge.entities.User;
import io.plucen.unclescrooge.repositories.UserRepository;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public class FakeUserRepository extends FakeCrudRepository<User, UUID> implements UserRepository {

  @Override
  public Optional<User> findByEmail(String email) {
    return index().stream().filter(user -> user.getEmail().equals(email)).findAny();
  }
}
