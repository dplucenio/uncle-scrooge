package io.plucen.unclescrooge.entities;

import java.util.UUID;
import lombok.Data;

@Data
public class AccountUserConnection {
  private final UUID userId;
  private final UUID accountId;
}
