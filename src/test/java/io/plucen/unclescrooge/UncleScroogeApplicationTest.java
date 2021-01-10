package io.plucen.unclescrooge;

import io.plucen.unclescrooge.entities.User;
import io.plucen.unclescrooge.migrations.Migrations;
import io.plucen.unclescrooge.repositories.newrepos.UserRepository;
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
  @Autowired UserRepository userRepository;
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
    userRepository.insert(new User(UUID.randomUUID(), "a"));
    userRepository.insert(new User(UUID.randomUUID(), "dododo"));
    final Optional<User> user = userRepository.findByEmail("dododo");
    user.ifPresent(System.out::println);
    System.out.println(userRepository.findAll());
  }
}
