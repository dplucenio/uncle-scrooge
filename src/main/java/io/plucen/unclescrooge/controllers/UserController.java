package io.plucen.unclescrooge.controllers;

import io.plucen.unclescrooge.entities.User;
import java.util.List;
import java.util.UUID;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

  @RequestMapping("/users")
  public List<User> index() {
    return List.of(new User(UUID.randomUUID(), "Daniel"));
  }
}
