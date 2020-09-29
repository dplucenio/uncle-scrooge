package io.plucen.unclescrooge.controllers;

import io.plucen.unclescrooge.entities.Account;
import io.plucen.unclescrooge.entities.User;
import io.plucen.unclescrooge.services.UserService;
import io.plucen.unclescrooge.utils.Pair;
import java.util.List;
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
  public List<User> index() {
    return userService.index();
  }

  @PostMapping("/users")
  public User createUser(@RequestBody UserCreationDto userCreationDTO) {
    return userService.create(userCreationDTO.getEmail());
  }

  @GetMapping("/users/{userId}/accounts")
  public List<Account> getConnectedAccounts(@PathVariable UUID userId) {
    return userService.getConnectedAccounts(userId);
  }

  @PostMapping("/users/{userId}/accounts")
  public Pair<UUID, UUID> connectUserToAccount(
      @PathVariable UUID userId,
      @RequestBody UserAccountConnectionCreationDto connectionCreationDto) {
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
