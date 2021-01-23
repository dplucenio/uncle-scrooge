package io.plucen.unclescrooge.entities;

import java.util.UUID;
import lombok.Data;
import org.springframework.data.relational.core.mapping.Table;

@Table("application_user_account")
@Data
public class AccountRef {
  private final UUID account;
}
