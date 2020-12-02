package io.plucen.unclescrooge.repositories;

import io.plucen.unclescrooge.entities.Account;
import java.util.UUID;

public interface AccountRepository extends CrudRepository<Account, UUID> {}
