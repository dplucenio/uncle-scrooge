package io.plucen.unclescrooge.repositories;

import io.plucen.unclescrooge.entities.Category;
import io.plucen.unclescrooge.repositories.withinsert.WithInsert;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, UUID>, WithInsert<Category> {}
