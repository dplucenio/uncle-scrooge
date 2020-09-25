package io.plucen.unclescrooge.entities;

import java.math.BigDecimal;
import java.util.UUID;
import lombok.Data;

@Data
public class Transaction {
  private final UUID id;
  private final UUID accountId;
  private final BigDecimal amount; // TODO: review this type
}
