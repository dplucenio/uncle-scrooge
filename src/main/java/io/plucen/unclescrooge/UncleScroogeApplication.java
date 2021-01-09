package io.plucen.unclescrooge;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class UncleScroogeApplication {

  // todo
  // [ ] remove identifiable
  // [ ] move datasource configuration to separate file

  @Value("${spring.datasource.url}")
  private String datasourceUrl;

  @Bean
  public DataSource getDataSource() {
    final HikariConfig hikariConfig = new HikariConfig();
    hikariConfig.setJdbcUrl(datasourceUrl);
    return new HikariDataSource(hikariConfig);
  }

  public static void main(String[] args) {
    SpringApplication.run(UncleScroogeApplication.class, args);
  }
}
