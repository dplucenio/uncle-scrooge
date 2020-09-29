package io.plucen.unclescrooge.entities;

import java.math.BigDecimal;
import java.util.UUID;
import lombok.Data;

@Data
public class Account implements Identifiable<UUID> {
  private final UUID id;
  private final String name;
  private final BigDecimal initialAmount;
}
