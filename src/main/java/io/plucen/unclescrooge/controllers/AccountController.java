package io.plucen.unclescrooge.controllers;

import io.plucen.unclescrooge.entities.Account;
import io.plucen.unclescrooge.services.AccountService;
import java.math.BigDecimal;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AccountController {

  private final AccountService accountService;

  @GetMapping("/accounts")
  public Iterable<Account> index() {
    return accountService.index();
  }

  @PostMapping("/accounts")
  public Account createAccount(@RequestBody AccountCreationDto accountCreationDto) {
    return accountService.createAccount(
        accountCreationDto.getName(), accountCreationDto.getInitialAmount());
  }

  @Data
  private static class AccountCreationDto {
    private String name;
    private BigDecimal initialAmount;
  }
}
