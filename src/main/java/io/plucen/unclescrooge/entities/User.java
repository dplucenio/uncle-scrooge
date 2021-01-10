package io.plucen.unclescrooge.entities;

import java.util.UUID;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("users")
public class User implements Identifiable<UUID> {
  @Id private final UUID id;
  private final String email;
}
