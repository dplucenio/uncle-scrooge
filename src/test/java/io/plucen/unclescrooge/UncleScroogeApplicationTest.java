package io.plucen.unclescrooge;

import io.plucen.unclescrooge.entities.Account;
import io.plucen.unclescrooge.entities.Category;
import io.plucen.unclescrooge.entities.MoneyTransaction;
import io.plucen.unclescrooge.entities.User;
import io.plucen.unclescrooge.migrations.Migrations;
import io.plucen.unclescrooge.repositories.AccountRepository;
import io.plucen.unclescrooge.repositories.CategoryRepository;
import io.plucen.unclescrooge.repositories.MoneyTransactionRepository;
import io.plucen.unclescrooge.repositories.UserRepository;
import java.math.BigDecimal;
import java.util.UUID;
import javax.sql.DataSource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;

@SpringBootTest
class UncleScroogeApplicationTest {

  @Autowired DataSource dataSource;
  @Autowired UserRepository userRepository;
  @Autowired AccountRepository accountRepository;
  @Autowired CategoryRepository categoryRepository;
  @Autowired MoneyTransactionRepository moneyTransactionRepository;
  @Autowired JdbcAggregateTemplate jdbcAggregateTemplate;

  @Test
  public void bb() {
    Migrations.clean(dataSource);
  }

  @Test
  public void aa() {
    Migrations.clean(dataSource);
    Migrations.migrate(dataSource);

    final User john = User.create("jLennon@gmail.com");
    final Account strawbeeryFieldsBank =
        Account.create("Strawberry Fields Bank", new BigDecimal("21.0"));
    john.connectToAccount(strawbeeryFieldsBank);
    accountRepository.insert(strawbeeryFieldsBank);
    userRepository.insert(john);
    final User paul = new User(UUID.randomUUID(), "pMccartney@gmail.com");
    System.out.println("---------------------------------------");
    userRepository.insert(paul);
    paul.connectToAccount(strawbeeryFieldsBank);
    userRepository.save(paul);

    final Category food = Category.create("food");
    categoryRepository.insert(food);

    final MoneyTransaction transaction =
        MoneyTransaction.create(
            new BigDecimal("9.99"), "balinha", john, strawbeeryFieldsBank, food);
    moneyTransactionRepository.insert(transaction);

    System.out.println(userRepository.findByEmail("cplucenio@gmail.com"));
    System.out.println(userRepository.findAll());
    System.out.println(userRepository.findAllByAccountId(strawbeeryFieldsBank.getId()));
    System.out.println(moneyTransactionRepository.findAll());
  }
}
