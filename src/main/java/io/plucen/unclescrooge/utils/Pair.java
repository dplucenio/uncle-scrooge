package io.plucen.unclescrooge.utils;

import lombok.Data;

@Data
public class Pair<T, U> {
  private final T first;
  private final U seconds;

  public static <T, U> Pair<T, U> of(T t, U u) {
    return new Pair<>(t, u);
  }
}
