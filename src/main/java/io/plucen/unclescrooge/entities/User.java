package io.plucen.unclescrooge.entities;

import java.util.UUID;
import lombok.Data;

@Data
public class User {
  private final UUID id;
  private final String email;
}
