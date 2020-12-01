package io.plucen.unclescrooge;

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
}
