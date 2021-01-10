package io.plucen.unclescrooge.repositories.newrepos;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;

@RequiredArgsConstructor
public class WithInsertImpl<T> implements WithInsert<T> {

  @Autowired private final JdbcAggregateTemplate jdbcAggregateTemplate;

  @Override
  public void insert(T t) {
    jdbcAggregateTemplate.insert(t);
  }
}
