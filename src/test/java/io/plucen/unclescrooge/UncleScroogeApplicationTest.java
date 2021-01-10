package io.plucen.unclescrooge;

import io.plucen.unclescrooge.entities.User;
import io.plucen.unclescrooge.migrations.Migrations;
import io.plucen.unclescrooge.repositories.newrepos.NewUserRepository;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;
import javax.sql.DataSource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;

@SpringBootTest
class UncleScroogeApplicationTest {

  @Autowired DataSource dataSource;
  @Autowired NewUserRepository newUserRepository;
  @Autowired JdbcAggregateTemplate jdbcAggregateTemplate;

  @Test
  public void testPlaceHolder() {
    try {
      final Connection connection = dataSource.getConnection();
      System.out.println("Connected");
      System.out.println(connection.getMetaData().getURL());
    } catch (SQLException exception) {
      exception.printStackTrace();
    }
  }

  @Test
  public void aa() {
    Migrations.clean(dataSource);
    Migrations.migrate(dataSource);
    newUserRepository.insert(new User(UUID.randomUUID(), "a"));
    newUserRepository.insert(new User(UUID.randomUUID(), "dododo"));
    final Optional<User> user = newUserRepository.findByEmail("dododo");
    user.ifPresent(System.out::println);
    System.out.println(newUserRepository.findAll());
  }
}
