package io.plucen.unclescrooge.services;

import com.google.common.collect.Iterables;
import io.plucen.unclescrooge.entities.Account;
import io.plucen.unclescrooge.entities.Person;
import io.plucen.unclescrooge.exception.UncleScroogeException.EmailAlreadyUsedException;
import io.plucen.unclescrooge.exception.UncleScroogeException.IdNotUniqueException;
import io.plucen.unclescrooge.exception.UncleScroogeException.NonExistingEntityException;
import io.plucen.unclescrooge.repositories.AccountRepository;
import io.plucen.unclescrooge.repositories.PersonRepository;
import io.plucen.unclescrooge.utils.Pair;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

  private final PersonRepository personRepository;
  private final AccountRepository accountRepository;

  public Iterable<Person> index() {
    return personRepository.findAll();
  }

  public Person create(String email) throws EmailAlreadyUsedException {
    if (personRepository.findByEmail(email).isEmpty()) {
      Person person = new Person(UUID.randomUUID(), email);
      personRepository.insert(person);
      return person;
    }
    throw new EmailAlreadyUsedException(email);
  }

  public Optional<Person> findById(UUID id) {
    return personRepository.findById(id);
  }

  public Pair<UUID, UUID> connectToAccount(UUID personId, UUID accountId)
      throws NonExistingEntityException, IdNotUniqueException {

    final Optional<Person> person = personRepository.findById(personId);
    if (person.isEmpty()) throw new NonExistingEntityException(Person.class, personId);
    final Optional<Account> account = accountRepository.findById(accountId);
    if (account.isEmpty()) throw new NonExistingEntityException(Account.class, accountId);

    final Iterable<Account> alreadyConnectedAccounts = getConnectedAccounts(personId);
    if (Iterables.contains(alreadyConnectedAccounts, account.get()))
      throw new IdNotUniqueException("User already has a connection with this account");
    person.get().connectToAccount(account.get());
    personRepository.save(person.get());
    return Pair.of(personId, accountId);
  }

  public Iterable<Account> getConnectedAccounts(UUID userId) throws NonExistingEntityException {
    final Optional<Person> person = personRepository.findById(userId);
    if (person.isPresent()) {
      return accountRepository.findAllById(person.get().getAccountIds());
    } else {
      throw new NonExistingEntityException(Person.class, userId);
    }
  }
}
