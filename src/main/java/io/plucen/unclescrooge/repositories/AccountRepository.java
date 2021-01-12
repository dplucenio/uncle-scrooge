package io.plucen.unclescrooge.repositories;

import io.plucen.unclescrooge.entities.Account;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends CrudRepository<Account, UUID>, WithInsert<Account> {}
