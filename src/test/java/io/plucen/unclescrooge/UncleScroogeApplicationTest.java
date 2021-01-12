package io.plucen.unclescrooge;

import io.plucen.unclescrooge.entities.Account;
import io.plucen.unclescrooge.entities.Person;
import io.plucen.unclescrooge.migrations.Migrations;
import io.plucen.unclescrooge.repositories.AccountRepository;
import io.plucen.unclescrooge.repositories.PersonRepository;
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
  @Autowired PersonRepository personRepository;
  @Autowired AccountRepository accountRepository;
  @Autowired JdbcAggregateTemplate jdbcAggregateTemplate;

  @Test
  public void bb() {
    Migrations.clean(dataSource);
  }

  @Test
  public void aa() {
    Migrations.clean(dataSource);
    Migrations.migrate(dataSource);

    final Account bank = new Account(UUID.randomUUID(), "Banco do Brasil", new BigDecimal(0.0));
    accountRepository.insert(bank);

    final Person dplucenio = new Person(UUID.randomUUID(), "dplucenio@gmail.com");
    dplucenio.connectToAccount(bank);
    personRepository.insert(dplucenio);
    final Person clarice = new Person(UUID.randomUUID(), "cplucenio@gmail.com");
    personRepository.insert(clarice);

    System.out.println(personRepository.findByEmail("cplucenio@gmail.com"));
    System.out.println(personRepository.findAll());
  }
}
