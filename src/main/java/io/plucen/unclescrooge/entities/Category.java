package io.plucen.unclescrooge.entities;

import java.util.UUID;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("category")
public class Category {
  @Id private final UUID id;
  private final String name;

  public static Category create(String name) {
    return new Category(UUID.randomUUID(), name);
  }
}
