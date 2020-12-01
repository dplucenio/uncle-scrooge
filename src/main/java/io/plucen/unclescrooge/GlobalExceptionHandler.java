package io.plucen.unclescrooge;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(NoHandlerFoundException.class)
  public ErrorDTO handleNoHandlerFoundException(NoHandlerFoundException exception) {
    return new ErrorDTO(exception.getMessage());
  }

  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(Exception.class)
  public ErrorDTO handleInternalError(Exception exception) {
    return new ErrorDTO(exception.getMessage());
  }

  @Data
  private static class ErrorDTO {
    private final String message;
  }
}
