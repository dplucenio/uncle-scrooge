package io.plucen.unclescrooge.repositories.fakes;

import io.plucen.unclescrooge.entities.Identifiable;
import io.plucen.unclescrooge.repositories.CrudRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class FakeCrudRepository<T extends Identifiable<Q>, Q>
    implements CrudRepository<T, Q> {
  protected final List<T> entries;

  FakeCrudRepository() {
    entries = new ArrayList<>();
  }

  @Override
  public List<T> index() {
    return entries;
  }

  @Override
  public void save(T entry) {
    entries.add(entry);
  }

  @Override
  public Optional<T> findById(Q id) {
    return entries.stream().filter(T -> T.getId().equals(id)).findFirst();
  }
}
