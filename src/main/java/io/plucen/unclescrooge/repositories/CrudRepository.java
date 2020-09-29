package io.plucen.unclescrooge.repositories;

import io.plucen.unclescrooge.entities.Identifiable;
import java.util.List;
import java.util.Optional;

public interface CrudRepository<T extends Identifiable<Q>, Q> {
  List<T> index();

  void save(T entry);

  Optional<T> findById(Q id);
}
