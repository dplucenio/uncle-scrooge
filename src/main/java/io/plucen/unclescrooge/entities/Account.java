package io.plucen.unclescrooge.entities;

import java.math.BigDecimal;
import java.util.UUID;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("account")
public class Account {
  @Id private final UUID id;
  private final String name;

  @Column("initial_amount")
  private final BigDecimal initialAmount;
}
