package io.plucen.unclescrooge.entities;

import java.math.BigDecimal;
import java.util.UUID;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("account")
public class Account {
  @Id private final UUID id;
  private final String name;
  private final BigDecimal initialAmount;

  public static Account create(String name, BigDecimal initialAmount) {
    return new Account(UUID.randomUUID(), name, initialAmount);
  }
}
