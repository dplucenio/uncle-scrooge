package io.plucen.unclescrooge.repositories.fakes;

import io.plucen.unclescrooge.entities.Account;
import io.plucen.unclescrooge.repositories.AccountRepository;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public class FakeAccountRepository extends FakeCrudRepository<Account, UUID>
    implements AccountRepository {}
