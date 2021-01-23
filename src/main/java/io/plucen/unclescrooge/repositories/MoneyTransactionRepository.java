package io.plucen.unclescrooge.repositories;

import io.plucen.unclescrooge.entities.MoneyTransaction;
import io.plucen.unclescrooge.repositories.withinsert.WithInsert;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;

public interface MoneyTransactionRepository
    extends CrudRepository<MoneyTransaction, UUID>, WithInsert<MoneyTransaction> {}
