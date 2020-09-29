package io.plucen.unclescrooge.repositories;

import io.plucen.unclescrooge.entities.Account;
import io.plucen.unclescrooge.entities.UserAccountConnection;
import io.plucen.unclescrooge.utils.Pair;
import java.util.List;
import java.util.UUID;

public interface UserAccountRepository
    extends CrudRepository<UserAccountConnection, Pair<UUID, UUID>> {

  public List<Account> getUserConnectedAccounts(UUID userId);
}
