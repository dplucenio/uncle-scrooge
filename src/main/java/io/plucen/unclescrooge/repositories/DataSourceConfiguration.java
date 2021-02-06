package io.plucen.unclescrooge.repositories;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSourceConfiguration {
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
}
