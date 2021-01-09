package io.plucen.unclescrooge.services;

import io.plucen.unclescrooge.entities.Account;
import io.plucen.unclescrooge.entities.User;
import io.plucen.unclescrooge.entities.UserAccountConnection;
import io.plucen.unclescrooge.exception.UncleScroogeException.EmailAlreadyUsedException;
import io.plucen.unclescrooge.exception.UncleScroogeException.IdNotUniqueException;
import io.plucen.unclescrooge.exception.UncleScroogeException.NonExistingEntityException;
import io.plucen.unclescrooge.repositories.AccountRepository;
import io.plucen.unclescrooge.repositories.UserAccountRepository;
import io.plucen.unclescrooge.repositories.UserRepository;
import io.plucen.unclescrooge.utils.Pair;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final AccountRepository accountRepository;
  private final UserAccountRepository userAccountRepository;

  public List<User> index() {
    return userRepository.index();
  }

  public User create(String email) throws EmailAlreadyUsedException {
    if (userRepository.findByEmail(email).isEmpty()) {
      User user = new User(UUID.randomUUID(), email);
      userRepository.save(user);
      return user;
    }
    throw new EmailAlreadyUsedException(email);
  }

  public Optional<User> findById(UUID id) {
    return userRepository.findById(id);
  }

  // TODO: study if we should receive ids or objects
  public Pair<UUID, UUID> connectToAccount(UUID userId, UUID accountId)
      throws NonExistingEntityException, IdNotUniqueException {
    if (userRepository.findById(userId).isEmpty())
      throw new NonExistingEntityException(User.class, userId);
    if (accountRepository.findById(accountId).isEmpty())
      throw new NonExistingEntityException(Account.class, accountId);

    final List<Account> connectedAccounts = getConnectedAccounts(userId);
    if (connectedAccounts.stream().anyMatch(account -> account.getId().equals(accountId)))
      throw new IdNotUniqueException("User already has a connection with this account");
    userAccountRepository.save(new UserAccountConnection(userId, accountId));
    return Pair.of(userId, accountId);
  }

  public List<Account> getConnectedAccounts(UUID userId) throws NonExistingEntityException {
    if (userRepository.findById(userId).isEmpty())
      throw new NonExistingEntityException(User.class, userId);
    return userAccountRepository.getUserConnectedAccounts(userId);
  }
}
