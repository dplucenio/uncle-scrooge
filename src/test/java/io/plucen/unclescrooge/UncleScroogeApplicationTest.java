package io.plucen.unclescrooge;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UncleScroogeApplicationTest {

  @Autowired DataSource dataSource;

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
}
