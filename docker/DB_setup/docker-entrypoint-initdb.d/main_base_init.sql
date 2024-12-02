CREATE TYPE currency AS ENUM ('USD', 'EUR', 'RUB', 'BYN');

CREATE TYPE time_period AS ENUM ('YEAR', 'QUARTER', 'MONTH', 'DAY', 'HOUR');

CREATE TABLE users (
    user_id SERIAL PRIMARY KEY,
    username varchar(20) UNIQUE NOT NULL,
    password varchar(40) NOT NULL
);

CREATE TABLE saving (
    saving_id SERIAL PRIMARY KEY,
    saving_amount numeric NOT NULL,
    saving_currency currency NOT NULL,
    interest numeric NOT NULL,
    deposit boolean NOT NULL,
    capitalization boolean NOT NULL
);

CREATE TABLE account (
    account_id SERIAL PRIMARY KEY,
    account_name text UNIQUE NOT NULL, 
    user_id integer UNIQUE CONSTRAINT FK_users REFERENCES users ON DELETE CASCADE,
    saving_id integer UNIQUE CONSTRAINT FK_saving REFERENCES saving ON DELETE RESTRICT,
    last_seen timestamptz,
    note varchar(20000)
);

CREATE TABLE item (
    item_id SERIAL PRIMARY KEY ,
    title varchar(20) NOT NULL,
    item_amount numeric NOT NULL,
    item_currency currency NOT NULL,
    time_period time_period NOT NULL,
    icon varchar(40) NOT NULL,
    account_id integer CONSTRAINT FK_account REFERENCES account ON DELETE CASCADE 
);    

CREATE VIEW all_accounts AS 
SELECT a.account_id, account_name, a.user_id, a.saving_id, last_seen, a.note, saving_amount, s.saving_currency, interest, deposit, capitalization, i.item_id, i.title, 
	item_amount, i.item_currency, time_period, i.icon, username, u.password
FROM account a LEFT JOIN saving s ON a.saving_id = s.saving_id 
LEFT JOIN item i ON a.account_id = i.account_id 
LEFT JOIN users u ON a.user_id = u.user_id;    