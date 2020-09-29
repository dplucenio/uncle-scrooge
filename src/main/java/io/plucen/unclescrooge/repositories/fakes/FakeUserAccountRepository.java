package io.plucen.unclescrooge.repositories.fakes;

import static java.util.stream.Collectors.toList;

import io.plucen.unclescrooge.entities.Account;
import io.plucen.unclescrooge.entities.UserAccountConnection;
import io.plucen.unclescrooge.repositories.CrudRepository;
import io.plucen.unclescrooge.repositories.UserAccountRepository;
import io.plucen.unclescrooge.utils.Pair;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class FakeUserAccountRepository
    extends FakeCrudRepository<UserAccountConnection, Pair<UUID, UUID>>
    implements UserAccountRepository {

  private final CrudRepository<Account, UUID> accountRepository;

  @Override
  public List<Account> getUserConnectedAccounts(UUID userId) {
    return entries.stream()
        .filter(userAccountConnection -> userAccountConnection.getUserId().equals(userId))
        .map(
            userAccountConnection ->
                accountRepository.findById(userAccountConnection.getAccountId()).get())
        .collect(toList());
  }
}
