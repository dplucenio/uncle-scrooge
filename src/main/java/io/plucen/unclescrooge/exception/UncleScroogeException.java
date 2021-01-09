package io.plucen.unclescrooge.exception;

import io.plucen.unclescrooge.entities.Identifiable;
import java.util.UUID;
import lombok.Getter;
import org.springframework.http.HttpStatus;

public class UncleScroogeException extends Exception {
  @Getter private final HttpStatus httpStatus;

  public UncleScroogeException(String message, HttpStatus httpStatus) {
    super(message);
    this.httpStatus = httpStatus;
  }

  @Override
  public String toString() {
    return super.toString() + "," + httpStatus;
  }

  // TODO: review HTTP status

  public static class IdNotUniqueException extends UncleScroogeException {
    public IdNotUniqueException(String message) {
      super(message, HttpStatus.BAD_REQUEST);
    }
  }

  public static class NonExistingEntityException extends UncleScroogeException {
    public NonExistingEntityException(Class<? extends Identifiable<?>> type, UUID id) {
      super(
          "There is no " + type.getSimpleName() + " stored with id " + id.toString(),
          HttpStatus.BAD_REQUEST);
    }
  }

  public static class EmailAlreadyUsedException extends UncleScroogeException {
    public EmailAlreadyUsedException(String email) {
      super(email + "is already used", HttpStatus.BAD_REQUEST);
    }
  }
}
