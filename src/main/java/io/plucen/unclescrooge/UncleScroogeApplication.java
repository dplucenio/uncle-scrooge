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

  @Value("${spring.datasource.password}")
  private String datasourcePassword;

  @Value("${spring.datasource.username}")
  private String datasourceUsername;

  @Bean
  public DataSource getDataSource() {
    final HikariConfig hikariConfig = new HikariConfig();
    hikariConfig.setJdbcUrl(datasourceUrl);
    hikariConfig.setPassword(datasourcePassword);
    hikariConfig.setUsername(datasourceUsername);
    return new HikariDataSource(hikariConfig);
  }

  public static void main(String[] args) {
    SpringApplication.run(UncleScroogeApplication.class, args);
  }
}
