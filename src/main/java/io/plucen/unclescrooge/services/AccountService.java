package io.plucen.unclescrooge.services;

import io.plucen.unclescrooge.entities.Account;
import io.plucen.unclescrooge.repositories.AccountRepository;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {

  private final AccountRepository accountRepository;

  public Iterable<Account> index() {
    return accountRepository.findAll();
  }

  public Account createAccount(String name, BigDecimal initialAmount) {
    final Account account = new Account(UUID.randomUUID(), name, initialAmount);
    accountRepository.insert(account);
    return account;
  }

  public Optional<Account> findById(UUID id) {
    return accountRepository.findById(id);
  }
}
