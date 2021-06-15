package com.bank.api.db.jdbc.dao;

import com.bank.api.db.jdbc.JdbcTemplate;
import com.bank.api.db.jdbc.JdbcTemplateImpl;
import com.bank.api.db.jdbc.connections.ConnectionDB;
import com.bank.api.db.jdbc.connections.DBConnectionSettings;
import com.bank.api.db.jdbc.connections.H2Connection;
import com.bank.api.db.jdbc.tablecreator.TableCreator;
import com.bank.api.domain.dao.AccountRepository;
import com.bank.api.domain.dao.CardRepository;
import com.bank.api.domain.dao.UserRepository;
import com.bank.api.domain.dto.Account;
import com.bank.api.domain.dto.Card;
import com.bank.api.domain.dto.Currency;
import com.bank.api.utils.Logger;
import com.bank.api.utils.LoggerMock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.*;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AccountRepositoryImplTests {
    private UserRepository userRepository;
    private AccountRepository accountRepository;
    private CardRepository cardRepository;
    private Logger logger;
    JdbcTemplate jdbcTemplate;
    ConnectionDB connectionDB;

    @BeforeEach
    void init() throws SQLException {
        DBConnectionSettings dbConnectionSettings =
                new DBConnectionSettings(
                        "jdbc:h2:mem:test",
                        "sa",
                        "sa"
                );
        logger = new LoggerMock();
        connectionDB = H2Connection.getInstance(dbConnectionSettings);
        jdbcTemplate = new JdbcTemplateImpl(connectionDB);
        cardRepository = new CardRepositoryImpl(jdbcTemplate, logger);
        accountRepository = new AccountRepositoryImpl(cardRepository, jdbcTemplate, logger);
        userRepository = new UserRepositoryImpl(accountRepository, jdbcTemplate, logger);

        TableCreator tableCreator = new TableCreator(connectionDB);
        tableCreator.createUsersTable();
        tableCreator.createAccountsTable();
        tableCreator.createCardsTable();
    }

    @AfterEach
    void destroy() throws SQLException {

        connectionDB.close();
    }

    @Test
    void create_userWithOutAccountInDB_userWithAccountInDb() throws SQLException {
        String sql = "INSERT INTO users(id, first_name, second_name, middle_name, passport_serial, passport_number, passport_type) VALUES\n" +
                "(1, 'Arthur', 'Davletkaliev', ' ', 0, 1, 'KZ');";

        connectionDB.get().prepareStatement(sql).execute();

        String[] extend = {"1", "11", "0.0", "RUB", "1"};

        accountRepository.creat(new Account("11", Currency.RUB, 1));

        PreparedStatement preparedStatement = connectionDB.get().prepareStatement("Select * from accounts;");
        preparedStatement.execute();

        ResultSet resultSet = preparedStatement.getResultSet();

        assertTrue(resultSet.next());
        assertEquals(extend[0], resultSet.getString("id"));
        assertEquals(extend[1], resultSet.getString("account_number"));
        assertEquals(extend[2], resultSet.getString("balance"));
        assertEquals(extend[3], resultSet.getString("currency"));
        assertEquals(extend[4], resultSet.getString("user_id"));
    }

    @Test
    void read_userWithOneAccountAndOneCardInDb_accountObjectFromDB() throws SQLException {
        String sql = "INSERT INTO users(id, first_name, second_name, middle_name, passport_serial, passport_number, passport_type) VALUES\n" +
                "(1, 'Arthur', 'Davletkaliev', ' ', 0, 1, 'KZ');\n" +
                "\n" +
                "INSERT INTO accounts (id, account_number, balance, currency, user_id) values\n" +
                "(1, '11', 0, 'RUB', 1);\n" +
                "INSERT INTO cards (id, card_number, account_id) values\n" +
                "(1, '111', 1);";

        connectionDB.get().prepareStatement(sql).execute();

        Account extend = new Account(1, "11", Currency.RUB, 1);
        // отдельно сохраняю бигдецимал в ноль так как есть отличие между BigDecimal.valueOf(0) и BigDecimal.valueOf(0.0)
        extend.setBalance(BigDecimal.valueOf(0));
        extend.getCards().add(new Card(1, "111", 1));

        Account account = accountRepository.read(1);

        assertEquals(extend, account);
    }

    @Test
    void get_userWithOneAccountAndOneCardInDb_accountObjectFromDB() throws SQLException {
        String sql = "INSERT INTO users(id, first_name, second_name, middle_name, passport_serial, passport_number, passport_type) VALUES\n" +
                "(1, 'Arthur', 'Davletkaliev', ' ', 0, 1, 'KZ');\n" +
                "\n" +
                "INSERT INTO accounts (id, account_number, balance, currency, user_id) values\n" +
                "(1, '11', 0, 'RUB', 1);\n" +
                "INSERT INTO cards (id, card_number, account_id) values\n" +
                "(1, '111', 1);";

        connectionDB.get().prepareStatement(sql).execute();

        Account extend = new Account(1, "11", Currency.RUB, 1);
        // отдельно сохраняю бигдецимал в ноль так как есть отличие между BigDecimal.valueOf(0) и BigDecimal.valueOf(0.0)
        extend.setBalance(BigDecimal.valueOf(0));
        extend.getCards().add(new Card(1, "111", 1));

        Account account = accountRepository.read(1);

        assertEquals(extend, account);
    }

    @Test
    void getAllByUserId_userWithOneAccountAndOneCardInDb_accountObjectFromDB() throws SQLException {
        String sql = "INSERT INTO users(id, first_name, second_name, middle_name, passport_serial, passport_number, passport_type) VALUES\n" +
                "(1, 'Arthur', 'Davletkaliev', ' ', 0, 1, 'KZ');\n" +
                "\n" +
                "INSERT INTO accounts (id, account_number, balance, currency, user_id) values\n" +
                "(1, '11', 0, 'RUB', 1);\n" +
                "INSERT INTO cards (id, card_number, account_id) values\n" +
                "(1, '111', 1);";

        connectionDB.get().prepareStatement(sql).execute();
        Account account = new Account(1, "11", Currency.RUB, 1);
        account.setBalance(BigDecimal.valueOf(0));
        account.getCards().add(new Card(1, "111", 1));
        Set<Account> extend = new HashSet<Account>(){{add(account);}} ;


        Set<Account> actual = accountRepository.getAllByUserId(1);

        assertEquals(extend, actual);
    }

    @Test
    void update_accountInOneState_accountInNewState() throws SQLException {
        String sql = "INSERT INTO users(id, first_name, second_name, middle_name, passport_serial, passport_number, passport_type) VALUES\n" +
                "(1, 'Arthur', 'Davletkaliev', ' ', 0, 1, 'KZ');\n" +
                "\n" +
                "INSERT INTO accounts (id, account_number, balance, currency, user_id) values\n" +
                "(1, '11', 0, 'RUB', 1);";

        connectionDB.get().prepareStatement(sql).execute();

        Account account = new Account(1,"11", Currency.RUB, 1);
        account.setBalance(BigDecimal.valueOf(100));

        accountRepository.update(account);

        PreparedStatement preparedStatement = connectionDB.get().prepareStatement("Select * from accounts;");

        preparedStatement.execute();

        ResultSet resultSet = preparedStatement.getResultSet();

        assertTrue(resultSet.next());
        assertEquals(account.getBalance(), new BigDecimal(resultSet.getString("balance")));
    }
}
