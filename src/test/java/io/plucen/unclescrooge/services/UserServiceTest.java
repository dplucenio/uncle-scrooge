package io.plucen.unclescrooge.services;

import static org.assertj.core.api.Assertions.assertThat;

import io.plucen.unclescrooge.UncleScroogeApplication;
import io.plucen.unclescrooge.entities.Account;
import io.plucen.unclescrooge.entities.User;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {UncleScroogeApplication.class})
class UserServiceTest {
  @Autowired UserService userService;
  @Autowired AccountService accountService;

  @Test
  public void testUserCreation() {
    final User user = userService.create("jlennon@mail.com");
    assertThat(user.getEmail()).isEqualTo("jlennon@mail.com");
  }

  @Test
  public void testAccountConnection() {
    final User user = userService.create("jlennon@mail.com");
    assertThat(userService.getConnectedAccounts(user.getId())).isEmpty();
    final Account firstAccount =
        accountService.createAccount("firstAccount", new BigDecimal("0.0"));
    final Account secondAccount =
        accountService.createAccount("secondAccount", new BigDecimal("0.0"));

    userService.connectToAccount(user.getId(), firstAccount.getId());
    userService.connectToAccount(user.getId(), secondAccount.getId());
    final List<Account> connectedAccounts = userService.getConnectedAccounts(user.getId());
    assertThat(connectedAccounts).isNotEmpty();
    assertThat(connectedAccounts.stream().map(Account::getName))
        .contains("firstAccount", "secondAccount");
  }
}
