package com.bank.api.db.jdbc.dao;

import com.bank.api.db.jdbc.JdbcTemplate;
import com.bank.api.db.jdbc.mappers.UserRowMapper;
import com.bank.api.domain.dao.AccountRepository;
import com.bank.api.domain.dao.UserRepository;
import com.bank.api.domain.dto.Account;
import com.bank.api.domain.dto.User;
import com.bank.api.domain.services.exceptions.ServerException;
import com.bank.api.utils.Logger;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class UserRepositoryImpl implements UserRepository {
    private final Logger logger;
    private final JdbcTemplate jdbcTemplate;
    private final AccountRepository accountRepository;

    public UserRepositoryImpl(AccountRepository accountRepository, JdbcTemplate jdbcTemplate, Logger logger) {
        this.accountRepository = accountRepository;
        this.jdbcTemplate = jdbcTemplate;
        this.logger = logger;
    }

    @Override
    public void creat(User user) {
        try {
//            https://github.com/jOOQ/jOOQ/issues/3035
//            ОНИ НЕ ПОДДЕРЖИВАЮТ ЭТОТ ФУНКЦИОНАЛ!!!!!
//            String creatSql = "INSERT INTO users (first_name, second_name, middle_name, passport_serial, passport_number, passport_type)" +
//                    "VALUES ( ?, ?, ?, ?, ?, ? )" +
//                    " returning * ;";

            String creatSql = "INSERT INTO users (first_name, second_name, middle_name, passport_serial, passport_number, passport_type)" +
                                "VALUES ( ?, ?, ?, ?, ?, ? );";

            jdbcTemplate.query(creatSql,
                    user.getFirstName(),
                    user.getSecondName(),
                    user.getMiddleName(),
                    String.valueOf(user.getPassport().getSerial()),
                    String.valueOf(user.getPassport().getNumber()),
                    String.valueOf(user.getPassport().getPassportType()));

        } catch (SQLException throwables) {
            logger.write(throwables.getMessage());
            throw new ServerException();
        }
    }

    @Override
    public void update(User user) {

    }

    @Override
    public User read(long id) {
        try {
//            String selectSql = "SELECT * FROM users u " +
//                    "LEFT OUTER JOIN accounts a ON u.id = a.user_id " +
//                    "LEFT OUTER JOIN cards c ON a.id = c.account_id " +
//                    "WHERE u.id = ? ;";

            String selectSql = "SELECT * FROM users u WHERE id = ? ;";

            UserRowMapper userRowMapper = new UserRowMapper();

            User user = jdbcTemplate.getObject(userRowMapper, selectSql, String.valueOf(id));

            if (user == null){
                return null;
            }

            Set<Account> accounts = accountRepository.getAllByUserId(user.getId());

            user.setAccounts(accounts);

             return user;

        } catch (SQLException throwables) {
            logger.write(throwables.getMessage());
            throw new ServerException();
        }
    }

    @Override
    public void delete(User user) {
        throw new ServerException();
    }

    @Override
    public void begin() {

    }

    @Override
    public void commit() {

    }

    @Override
    public User get(User user) {
        try {
//            String selectSql = "SELECT * FROM users u " +
//                    "LEFT OUTER JOIN accounts a ON u.id = a.user_id " +
//                    "LEFT OUTER JOIN cards c ON a.id = c.account_id " +
//                    "WHERE u.id = ? ;";

            String selectSql = "SELECT * FROM users u WHERE passport_number = ? and passport_serial = ? ;";

            UserRowMapper userRowMapper = new UserRowMapper();

            User newUser = jdbcTemplate.getObject(userRowMapper, selectSql,
                    String.valueOf(user.getPassport().getNumber()),
                    String.valueOf(user.getPassport().getSerial()));

            if (newUser == null){
                return null;
            }

            Set<Account> accounts = accountRepository.getAllByUserId(newUser.getId());

            newUser.setAccounts(accounts);

            return newUser;

        } catch (SQLException throwables) {
            logger.write(throwables.getMessage());
            throw new ServerException();
        }
    }

    @Override
    public Set<User> getAll() {
        Set<User> users = new HashSet<User>();

        try {
//            String selectSql = "SELECT * FROM users u " +
//                    "LEFT OUTER JOIN accounts a ON u.id = a.user_id " +
//                    "LEFT OUTER JOIN cards c ON a.id = c.account_id " +
//                    "WHERE u.id = ? ;";

            String selectSql = "SELECT * FROM users ;";

            UserRowMapper userRowMapper = new UserRowMapper();

            users = jdbcTemplate.getObjects(userRowMapper, selectSql)
                    .stream().collect(Collectors.toSet());

            users.stream().forEach(user -> {
                Set<Account> accounts = accountRepository.getAllByUserId(user.getId());

                user.setAccounts(accounts);
            });
        } catch (SQLException throwables) {
            logger.write(throwables.getMessage());
            throw new ServerException();
        }

        return users;
    }
}
