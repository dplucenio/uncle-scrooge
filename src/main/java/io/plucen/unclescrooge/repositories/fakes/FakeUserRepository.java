package io.plucen.unclescrooge.repositories.fakes;

import io.plucen.unclescrooge.entities.User;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public class FakeUserRepository extends FakeCrudRepository<User, UUID> {}
