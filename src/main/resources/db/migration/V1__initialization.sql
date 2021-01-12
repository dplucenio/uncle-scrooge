CREATE TABLE person (id uuid PRIMARY KEY, email varchar);
CREATE TABLE account (id uuid PRIMARY KEY, name varchar, initial_amount decimal);
CREATE TABLE person_account(
  person UUID REFERENCES person(id),
  account UUID REFERENCES account(id),
  UNIQUE(person, account));
