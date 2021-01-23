package io.plucen.unclescrooge.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import io.plucen.unclescrooge.entities.Account;
import io.plucen.unclescrooge.entities.User;
import io.plucen.unclescrooge.exception.UncleScroogeException;
import io.plucen.unclescrooge.exception.UncleScroogeException.EmailAlreadyUsedException;
import io.plucen.unclescrooge.exception.UncleScroogeException.IdNotUniqueException;
import io.plucen.unclescrooge.exception.UncleScroogeException.NonExistingEntityException;
import io.plucen.unclescrooge.migrations.Migrations;
import java.math.BigDecimal;
import java.util.UUID;
import java.util.stream.StreamSupport;
import javax.sql.DataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserServiceTest {
  @Autowired DataSource dataSource;

  @Autowired UserService userService;
  @Autowired AccountService accountService;

  @BeforeEach
  public void setup() {
    Migrations.clean(dataSource);
    Migrations.migrate(dataSource);
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
        .isEqualToIgnoringCase(
            "There is no " + User.class.getSimpleName() + " stored with id " + userId.toString());
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
        .isEqualToIgnoringCase(
            "There is no "
                + Account.class.getSimpleName()
                + " stored with id "
                + accountId.toString());
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
    final Iterable<Account> connectedAccounts = userService.getConnectedAccounts(user.getId());
    assertThat(connectedAccounts).isNotEmpty();
    assertThat(StreamSupport.stream(connectedAccounts.spliterator(), false).map(Account::getName))
        .contains("firstAccount", "secondAccount");
  }

  @Test
  public void testFindUssersByAccount()
      throws EmailAlreadyUsedException, NonExistingEntityException, IdNotUniqueException {
    final User john = userService.create("jLennon@gmail.com");
    final User paul = userService.create("pmccartney@gmail.com");
    final Account firstAccount =
        accountService.createAccount("firstAccount", new BigDecimal("0.0"));
    final Account secondsAccount =
        accountService.createAccount("secondAccount", new BigDecimal("0.0"));
    userService.connectToAccount(john.getId(), firstAccount.getId());
    userService.connectToAccount(john.getId(), secondsAccount.getId());
    userService.connectToAccount(paul.getId(), secondsAccount.getId());

    assertThat(
            StreamSupport.stream(
                    userService.findAllByAccountId(firstAccount.getId()).spliterator(), false)
                .map(User::getEmail))
        .containsExactlyInAnyOrder("jLennon@gmail.com");
    assertThat(
            StreamSupport.stream(
                    userService.findAllByAccountId(secondsAccount.getId()).spliterator(), false)
                .map(User::getEmail))
        .containsExactly("jLennon@gmail.com", "pmccartney@gmail.com");
  }
}
