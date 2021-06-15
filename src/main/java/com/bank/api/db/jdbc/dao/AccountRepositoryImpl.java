package com.bank.api.db.jdbc.dao;

import com.bank.api.db.jdbc.JdbcTemplate;
import com.bank.api.db.jdbc.mappers.AccountRowMapper;
import com.bank.api.domain.dao.AccountRepository;
import com.bank.api.domain.dao.CardRepository;
import com.bank.api.domain.dto.Account;
import com.bank.api.domain.services.exceptions.ServerException;
import com.bank.api.utils.Logger;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class AccountRepositoryImpl implements AccountRepository {
    private final CardRepository cardRepository;
    private final JdbcTemplate jdbcTemplate;
    private final Logger logger;

    public AccountRepositoryImpl(CardRepository cardRepository, JdbcTemplate jdbcTemplate, Logger logger) {
        this.cardRepository = cardRepository;
        this.jdbcTemplate = jdbcTemplate;
        this.logger = logger;
    }

    @Override
    public Account get(String accountNumber) {
        Account account = null;
        try {
            String selectSql = "Select * from accounts where account_number = ? ;";
            AccountRowMapper accountRowMapper = new AccountRowMapper();
            account = jdbcTemplate.getObject(accountRowMapper, selectSql, accountNumber);

            if (account != null){
                account.setCards(cardRepository.getAllByAccountId(account.getId()));
            }

        } catch (SQLException throwables) {
            logger.write(throwables.getMessage());
            throw new ServerException();
        }

        return account;
    }

    @Override
    public Set<Account> getAll() {
        Set<Account> accounts = new HashSet<>();

        try {
            String sql = "Select * from accounts;";

            AccountRowMapper accountRowMapper = new AccountRowMapper();

            accounts = jdbcTemplate.getObjects(accountRowMapper, sql)
                    .stream().collect(Collectors.toSet());

            accounts.stream().forEach(account ->
                    account.setCards(cardRepository.getAllByAccountId(account.getId())));


        } catch (SQLException throwables) {
            logger.write(throwables.getMessage());
            throw new ServerException();
        }

        return accounts;
    }

    @Override
    public Set<Account> getAllByUserId(long id) {
        Set<Account> accounts = new HashSet<>();
        try {
            String sql = "Select * from accounts where user_id = ? ;";

            AccountRowMapper accountRowMapper = new AccountRowMapper();

            accounts = jdbcTemplate.getObjects(accountRowMapper, sql, String.valueOf(id))
                    .stream().collect(Collectors.toSet());

            accounts.stream().forEach(account ->
                    account.setCards(cardRepository.getAllByAccountId(account.getId())));

            return accounts;
        } catch (SQLException throwables) {
            logger.write(throwables.getMessage());
            throw new ServerException();
        }
    }

    @Override
    public void creat(Account account) {
        try {
            String insertSql = "INSERT INTO accounts(account_number, balance, currency, user_id) values (?,?,?,?);";

            jdbcTemplate.query(insertSql,
                    account.getNumber(),
                    String.valueOf(account.getBalance()),
                    String.valueOf(account.getCurrency()),
                    String.valueOf(account.getUserId()));
        } catch (SQLException throwables) {
            logger.write(throwables.getMessage());
            throw new ServerException();
        }
    }

    @Override
    public void update(Account account) {
        try {
            String updateSql = "UPDATE accounts SET account_number = ? ,\n" +
                    "balance = ? ,\n" +
                    "currency = ? ,\n" +
                    "user_id = ? \n" +
                    "where id = ? ;";

            jdbcTemplate.query(updateSql,
                    account.getNumber(),
                    String.valueOf(account.getBalance()),
                    String.valueOf(account.getCurrency()),
                    String.valueOf(account.getUserId()),
                    String.valueOf(account.getId()));
        } catch (SQLException throwables) {
            logger.write(throwables.getMessage());
            throw new ServerException();
        }
    }

    @Override
    public Account read(long id) {
        Account account = null;
        try {
            String selectSql = "Select * from accounts where id = ? ;";
            AccountRowMapper accountRowMapper = new AccountRowMapper();
            account = jdbcTemplate.getObject(accountRowMapper, selectSql, String.valueOf(id));

            if (account != null){
                account.setCards(cardRepository.getAllByAccountId(account.getId()));
            }

        } catch (SQLException throwables) {
            logger.write(throwables.getMessage());
            throw new ServerException();
        }

        return account;
    }

    @Override
    public void delete(Account account) {

    }

    @Override
    public void begin() {

    }

    @Override
    public void commit() {

    }
}
