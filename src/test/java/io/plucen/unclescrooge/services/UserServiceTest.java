package io.plucen.unclescrooge.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import io.plucen.unclescrooge.UncleScroogeApplication;
import io.plucen.unclescrooge.UncleScroogeException;
import io.plucen.unclescrooge.UncleScroogeException.EmailAlreadyUsedException;
import io.plucen.unclescrooge.UncleScroogeException.IdNotUniqueException;
import io.plucen.unclescrooge.UncleScroogeException.NonExistingEntityException;
import io.plucen.unclescrooge.entities.Account;
import io.plucen.unclescrooge.entities.User;
import io.plucen.unclescrooge.repositories.AccountRepository;
import io.plucen.unclescrooge.repositories.UserAccountRepository;
import io.plucen.unclescrooge.repositories.UserRepository;
import io.plucen.unclescrooge.repositories.fakes.FakeAccountRepository;
import io.plucen.unclescrooge.repositories.fakes.FakeUserAccountRepository;
import io.plucen.unclescrooge.repositories.fakes.FakeUserRepository;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {UncleScroogeApplication.class})
@ActiveProfiles("test")
class UserServiceTest {
  UserRepository userRepository;
  AccountRepository accountRepository;
  UserAccountRepository userAccountRepository;
  UserService userService;
  AccountService accountService;

  @BeforeEach
  public void setup() {
    accountRepository = new FakeAccountRepository();
    accountService = new AccountService(accountRepository);
    userRepository = new FakeUserRepository();
    userAccountRepository = new FakeUserAccountRepository(accountRepository);
    userService = new UserService(userRepository, accountRepository, userAccountRepository);
  }

  @Test
  public void testUserCreation() throws UncleScroogeException {
    final User user = userService.create("jlennon@mail.com");
    assertThat(user.getEmail()).isEqualTo("jlennon@mail.com");
  }

  @Test
  public void testThatCreatedUsersHaveUniqueEmails() throws UncleScroogeException {
    userService.create("jlennon@mail.com");
    assertThrows(EmailAlreadyUsedException.class, () -> userService.create("jlennon@mail.com"));
  }

  @Test
  public void testCantConnectAccountToNonExistingUser() throws UncleScroogeException {
    final UUID userId = UUID.randomUUID();
    final NonExistingEntityException nonExistingUser =
        assertThrows(
            NonExistingEntityException.class,
            () -> assertThat(userService.connectToAccount(userId, UUID.randomUUID())));
    assertThat(nonExistingUser.getMessage())
        .isEqualToIgnoringCase("There is no User stored with id " + userId.toString());
  }

  @Test
  public void testCantConnectToNonExistingAccount() throws UncleScroogeException {
    final User user = userService.create("jlennon@mail.com");
    final UUID accountId = UUID.randomUUID();
    final NonExistingEntityException nonExistingUser =
        assertThrows(
            NonExistingEntityException.class,
            () -> {
              assertThat(userService.connectToAccount(user.getId(), accountId));
            });
    assertThat(nonExistingUser.getMessage())
        .isEqualToIgnoringCase("There is no Account stored with id " + accountId.toString());
  }

  @Test
  public void testCantGetConnectedAccountsOfNonExistingUser() throws UncleScroogeException {
    final UUID userId = UUID.randomUUID();
    assertThrows(NonExistingEntityException.class, () -> userService.getConnectedAccounts(userId));
  }

  @Test
  public void testUserAccountConnectionsShouldBeUnique() throws UncleScroogeException {
    final User user = userService.create("jlennon@mail.com");
    final Account account = accountService.createAccount("bank 1", new BigDecimal("0.0"));
    userService.connectToAccount(user.getId(), account.getId());
    assertThrows(
        IdNotUniqueException.class,
        () -> userService.connectToAccount(user.getId(), account.getId()));
  }

  @Test
  public void testAccountConnection() throws UncleScroogeException {
    final User user = userService.create("jlennon@mail.com");
    final Account firstAccount =
        accountService.createAccount("firstAccount", new BigDecimal("0.0"));
    final Account secondAccount =
        accountService.createAccount("secondAccount", new BigDecimal("0.0"));

    assertThat(userService.getConnectedAccounts(user.getId())).isEmpty();

    userService.connectToAccount(user.getId(), firstAccount.getId());
    userService.connectToAccount(user.getId(), secondAccount.getId());
    final List<Account> connectedAccounts = userService.getConnectedAccounts(user.getId());
    assertThat(connectedAccounts).isNotEmpty();
    assertThat(connectedAccounts.stream().map(Account::getName))
        .contains("firstAccount", "secondAccount");
  }
}
