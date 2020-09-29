package io.plucen.unclescrooge.entities;

import io.plucen.unclescrooge.utils.Pair;
import java.util.UUID;
import lombok.Data;

@Data
public class UserAccountConnection implements Identifiable<Pair<UUID, UUID>> {
  private final UUID userId;
  private final UUID accountId;

  @Override
  public Pair<UUID, UUID> getId() {
    return Pair.of(userId, accountId);
  }
}
