package io.plucen.unclescrooge.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("money_transaction")
@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
public class MoneyTransaction {
  @Id private final UUID id;
  private final BigDecimal amount; // TODO: review this type
  private final String description;
  private final UUID applicationUser;
  private final UUID account;
  private final UUID category;
  private final LocalDateTime dateTime;

  public static MoneyTransaction create(
      BigDecimal amount,
      String description,
      UUID user,
      UUID account,
      UUID category,
      LocalDateTime dateTime) {
    return new MoneyTransaction(
        UUID.randomUUID(), amount, description, user, account, category, dateTime);
  }

  public static MoneyTransaction create(
      BigDecimal amount,
      String description,
      User user,
      Account account,
      Category category,
      LocalDateTime dateTime) {
    return create(amount, description, user.getId(), account.getId(), category.getId(), dateTime);
  }

  public static MoneyTransaction create(
      BigDecimal amount, String description, UUID user, UUID account, UUID category) {
    return create(amount, description, user, account, category, LocalDateTime.now());
  }

  public static MoneyTransaction create(
      BigDecimal amount, String description, User user, Account account, Category category) {
    return create(amount, description, user, account, category, LocalDateTime.now());
  }
}
