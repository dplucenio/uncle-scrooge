package io.plucen.unclescrooge.controllers;

import io.plucen.unclescrooge.entities.Account;
import io.plucen.unclescrooge.entities.Person;
import io.plucen.unclescrooge.exception.UncleScroogeException;
import io.plucen.unclescrooge.exception.UncleScroogeException.IdNotUniqueException;
import io.plucen.unclescrooge.exception.UncleScroogeException.NonExistingEntityException;
import io.plucen.unclescrooge.services.UserService;
import io.plucen.unclescrooge.utils.Pair;
import java.util.Optional;
import java.util.UUID;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @GetMapping("/users")
  public Iterable<Person> index() {
    return userService.index();
  }

  @PostMapping("/users")
  public Person createUser(@RequestBody UserCreationDto userCreationDTO)
      throws UncleScroogeException {
    return userService.create(userCreationDTO.getEmail());
  }

  @GetMapping("/users/{userId}")
  public Optional<Person> findById(@PathVariable UUID userId) {
    final Optional<Person> byId = userService.findById(userId);
    if (byId.isPresent()) return byId;
    else throw new RuntimeException("A");
  }

  @GetMapping("/users/{userId}/accounts")
  public Iterable<Account> getConnectedAccounts(@PathVariable UUID userId)
      throws NonExistingEntityException {
    return userService.getConnectedAccounts(userId);
  }

  @PostMapping("/users/{userId}/accounts")
  public Pair<UUID, UUID> connectUserToAccount(
      @PathVariable UUID userId,
      @RequestBody UserAccountConnectionCreationDto connectionCreationDto)
      throws NonExistingEntityException, IdNotUniqueException {
    return userService.connectToAccount(userId, connectionCreationDto.getAccountId());
  }

  @Data
  private static class UserCreationDto {
    private String email;
  }

  @Data
  private static class UserAccountConnectionCreationDto {
    private UUID accountId;
  }
}
