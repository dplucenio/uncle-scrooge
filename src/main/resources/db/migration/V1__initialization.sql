CREATE TABLE application_user (id uuid PRIMARY KEY, email varchar);

CREATE TABLE account (id uuid PRIMARY KEY, name varchar, initial_amount decimal);

CREATE TABLE application_user_account(
  application_user UUID REFERENCES application_user(id),
  account UUID REFERENCES account(id),
  UNIQUE(application_user, account));

CREATE TABLE category(
  id UUID PRIMARY KEY,
  name VARCHAR
);

CREATE TABLE money_transaction(
  id UUID PRIMARY KEY,
  amount DECIMAL(20,2),
  description VARCHAR,
  application_user UUID REFERENCES application_user(id),
  date_time timestamp without time zone,
  account UUID REFERENCES account(id),
  category UUID REFERENCES category(id)
);

