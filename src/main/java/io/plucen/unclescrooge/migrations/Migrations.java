package io.plucen.unclescrooge.migrations;

import javax.sql.DataSource;
import org.flywaydb.core.Flyway;

public class Migrations {
  public static void migrate(final DataSource dataSource) {
    Flyway.configure().dataSource(dataSource).load().migrate();
  }

  public static void clean(final DataSource dataSource) {
    Flyway.configure().dataSource(dataSource).load().clean();
  }
}
