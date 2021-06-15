CREATE TABLE IF NOT EXISTS users(
    id BIGINT PRIMARY KEY auto_increment,
    first_name VARCHAR(255) NOT NULL,
    second_name VARCHAR(255) NOT NULL,
    middle_name VARCHAR(255),
    passport_serial INT NOT NULL,
    passport_number INT NOT NULL,
    passport_type VARCHAR(2) NOT NULL);

CREATE TABLE IF NOT EXISTS accounts(
    id BIGINT PRIMARY KEY auto_increment,
    account_number VARCHAR(20) NOT NULL,
    balance DECIMAL NOT NULL,
    currency VARCHAR(3) NOT NULL DEFAULT ('RUB'),
    user_id BIGINT,
    foreign key (user_id) references users(id)
);

CREATE TABLE IF NOT EXISTS cards(
    id BIGINT PRIMARY KEY auto_increment,
    card_number VARCHAR(20) NOT NULL,
    account_id BIGINT,
    foreign key (account_id) references accounts(id)
);

INSERT INTO users(id, first_name, second_name, middle_name, passport_serial, passport_number, passport_type) VALUES
(1, 'Arthur', 'Davletkaliev', ' ', 0, 1, 'KZ');

INSERT INTO accounts (id, account_number, balance, currency, user_id) values
(1, 11, 0, 'RUB', 1),
(2, 12, 0, 'RUB', 1);

INSERT INTO cards (id, card_number, account_id) values
(1, 111, 1),
(2, 112, 1),
(3, 121, 2),
(4, 122, 2);