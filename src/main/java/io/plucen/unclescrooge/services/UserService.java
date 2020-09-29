package io.plucen.unclescrooge.services;

import io.plucen.unclescrooge.entities.Account;
import io.plucen.unclescrooge.entities.User;
import io.plucen.unclescrooge.entities.UserAccountConnection;
import io.plucen.unclescrooge.repositories.CrudRepository;
import io.plucen.unclescrooge.repositories.UserAccountRepository;
import io.plucen.unclescrooge.utils.Pair;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

  private final CrudRepository<User, UUID> userRepository;
  private final UserAccountRepository userAccountRepository;

  public List<User> index() {
    return userRepository.index();
  }

  public User create(String email) {
    User user = new User(UUID.randomUUID(), email);
    userRepository.save(user);
    return user;
  }

  public Optional<User> findById(UUID id) {
    return userRepository.findById(id);
  }

  public Pair<UUID, UUID> connectToAccount(UUID userId, UUID accountId) {
    userAccountRepository.save(new UserAccountConnection(userId, accountId));
    return Pair.of(userId, accountId);
  }

  public List<Account> getConnectedAccounts(UUID userId) {
    return userAccountRepository.getUserConnectedAccounts(userId);
  }
}
