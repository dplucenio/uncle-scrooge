package io.plucen.unclescrooge.services;

import com.google.common.collect.Iterables;
import io.plucen.unclescrooge.entities.Account;
import io.plucen.unclescrooge.entities.User;
import io.plucen.unclescrooge.exception.UncleScroogeException.EmailAlreadyUsedException;
import io.plucen.unclescrooge.exception.UncleScroogeException.IdNotUniqueException;
import io.plucen.unclescrooge.exception.UncleScroogeException.NonExistingEntityException;
import io.plucen.unclescrooge.repositories.AccountRepository;
import io.plucen.unclescrooge.repositories.UserRepository;
import io.plucen.unclescrooge.utils.Pair;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final AccountRepository accountRepository;
  private final PasswordEncoder passwordEncoder;

  public Iterable<User> index() {
    return userRepository.findAll();
  }

  public User create(String email, String password) throws EmailAlreadyUsedException {
    if (userRepository.findByEmail(email).isEmpty()) {
      User user = User.create(email, passwordEncoder.encode(password));
      userRepository.insert(user);
      return user;
    }
    throw new EmailAlreadyUsedException(email);
  }

  public Optional<User> findById(UUID id) {
    return userRepository.findById(id);
  }

  public Pair<UUID, UUID> connectToAccount(UUID userId, UUID accountId)
      throws NonExistingEntityException, IdNotUniqueException {

    final Optional<User> user = userRepository.findById(userId);
    if (user.isEmpty()) throw new NonExistingEntityException(User.class, userId);
    final Optional<Account> account = accountRepository.findById(accountId);
    if (account.isEmpty()) throw new NonExistingEntityException(Account.class, accountId);

    final Iterable<Account> alreadyConnectedAccounts = getConnectedAccounts(userId);
    if (Iterables.contains(alreadyConnectedAccounts, account.get()))
      throw new IdNotUniqueException("User already has a connection with this account");
    user.get().connectToAccount(account.get());
    userRepository.save(user.get());
    return Pair.of(userId, accountId);
  }

  public Iterable<Account> getConnectedAccounts(UUID userId) throws NonExistingEntityException {
    final Optional<User> user = userRepository.findById(userId);
    if (user.isPresent()) {
      return accountRepository.findAllById(user.get().getAccountIds());
    } else {
      throw new NonExistingEntityException(User.class, userId);
    }
  }

  public Iterable<User> findAllByAccountId(UUID accountId) {
    return userRepository.findAllByAccountId(accountId);
  }
}
