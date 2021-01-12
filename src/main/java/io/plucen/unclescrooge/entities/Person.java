package io.plucen.unclescrooge.entities;

import static java.util.stream.Collectors.toSet;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("person")
public class Person implements Identifiable<UUID> {
  @Id private final UUID id;
  private final String email;
  private Set<AccountRef> accountRefs = new HashSet<>();

  public void connectToAccount(final Account account) {
    accountRefs.add(new AccountRef(account.getId()));
  }

  public Set<UUID> getAccountIds() {
    return accountRefs.stream().map(AccountRef::getAccount).collect(toSet());
  }
}
