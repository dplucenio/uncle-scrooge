package io.plucen.unclescrooge.entities;

import java.util.UUID;
import lombok.Data;

@Data
public class User implements Identifiable<UUID> {
  private final UUID id;
  private final String email;
}
