package io.plucen.unclescrooge.entities;

import static java.util.stream.Collectors.toSet;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("application_user")
public class User {
  @Id private final UUID id;
  private final String email;
  private final String password;

  // @Column here is used to set custom column name on conjunction table (default would be "user")
  // to change the other field ("account"), use same annotation on AccountRef class
  @Column(value = "application_user")
  private Set<AccountRef> accountRefs = new HashSet<>();

  public static User create(String email, String password) {
    return new User(UUID.randomUUID(), email, password);
  }

  public void connectToAccount(final Account account) {
    accountRefs.add(new AccountRef(account.getId()));
  }

  public Set<UUID> getAccountIds() {
    return accountRefs.stream().map(AccountRef::getAccount).collect(toSet());
  }
}
