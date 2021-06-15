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
import com.bank.api.domain.dto.*;
import com.bank.api.utils.Logger;
import com.bank.api.utils.LoggerMock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CardRepositoryImplTests {
    private UserRepository userRepository;
    private AccountRepository accountRepository;
    private CardRepository cardRepository;
    private ConnectionDB connectionDB;
    private JdbcTemplate jdbcTemplate;
    private Logger logger;
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
    void create_InSystemOneUserWithOneAccount_InSystemOneUserWithOneAccountWithOneCard() throws SQLException {
        String sql = "INSERT INTO users(id, first_name, second_name, middle_name, passport_serial, passport_number, passport_type) VALUES\n" +
                "(1, 'Arthur', 'Davletkaliev', ' ', 0, 1, 'KZ');\n" +
                "\n" +
                "INSERT INTO accounts (id, account_number, balance, currency, user_id) values\n" +
                "(1, 11, 0, 'RUB', 1);";

        String[] extend = {"1", "111", "1"};

        User user = new User(1,
                "Arthur",
                "Davletkaliev",
                "",
                new Passport(0, 1, PassportType.KZ));
        Account account = new Account( "11", Currency.RUB, user.getId());
        account.setId(1L);
        Card newCard = new Card("111", account.getId());

        connectionDB.get().prepareStatement(sql).execute();

        cardRepository.creat(newCard);

        PreparedStatement preparedStatement = connectionDB.get().prepareStatement("Select * from cards;");
        preparedStatement.execute();

        ResultSet resultSet = preparedStatement.getResultSet();
        assertTrue(resultSet.next());
        assertEquals(resultSet.getString("id"), extend[0]);
        assertEquals(resultSet.getString("card_number"), extend[1]);
        assertEquals(resultSet.getString("account_id"), extend[2]);
    }

    @Test
    void read_CardInDb_HaveCardInObjectModel() throws SQLException {
        String sql = "INSERT INTO users(id, first_name, second_name, middle_name, passport_serial, passport_number, passport_type) VALUES\n" +
                "(1, 'Arthur', 'Davletkaliev', ' ', 0, 1, 'KZ');\n" +
                "\n" +
                "INSERT INTO accounts (id, account_number, balance, currency, user_id) values\n" +
                "(1, '11', 0, 'RUB', 1);\n" +
                "INSERT INTO cards (id, card_number, account_id) values\n" +
                "(1, '111', 1);";

        connectionDB.get().prepareStatement(sql).execute();

        User user = new User(1,
                "Arthur",
                "Davletkaliev",
                "",
                new Passport(0, 1, PassportType.KZ));
        Account account = new Account( "11", Currency.RUB, user.getId());
        account.setId(1L);
        Card extend = new Card(1,"111", account.getId());

        Card card = cardRepository.read(1);

        assertEquals(extend, card);
    }

    @Test
    void get_CardInDb_HaveCardInObjectModel() throws SQLException {
        String sql = "INSERT INTO users(id, first_name, second_name, middle_name, passport_serial, passport_number, passport_type) VALUES\n" +
                "(1, 'Arthur', 'Davletkaliev', ' ', 0, 1, 'KZ');\n" +
                "\n" +
                "INSERT INTO accounts (id, account_number, balance, currency, user_id) values\n" +
                "(1, '11', 0, 'RUB', 1);\n" +
                "INSERT INTO cards (id, card_number, account_id) values\n" +
                "(1, '111', 1);";

        connectionDB.get().prepareStatement(sql).execute();

        User user = new User(1,
                "Arthur",
                "Davletkaliev",
                "",
                new Passport(0, 1, PassportType.KZ));
        Account account = new Account( "11", Currency.RUB, user.getId());
        account.setId(1L);
        Card extend = new Card(1,"111", account.getId());

        Card card = cardRepository.get("111");

        assertEquals(extend, card);
    }

    @Test
    void getAll_CardsInDb_HaveCardsInObjectModel() throws SQLException {
        String sql = "INSERT INTO users(id, first_name, second_name, middle_name, passport_serial, passport_number, passport_type) VALUES\n" +
                "(1, 'Arthur', 'Davletkaliev', ' ', 0, 1, 'KZ');\n" +
                "\n" +
                "INSERT INTO accounts (id, account_number, balance, currency, user_id) values\n" +
                "(1, '11', 0, 'RUB', 1);\n" +
                "INSERT INTO cards (id, card_number, account_id) values\n" +
                "(1, '111', 1);";

        connectionDB.get().prepareStatement(sql).execute();

        Card card = new Card(1,"111", 1);

        Set<Card> extend = new HashSet<Card>(){{add(card);}};

        Set<Card> actual = cardRepository.getAll();

        assertEquals(extend, actual);
    }

    @Test
    void getAllByAccountId_CardInDb_HaveCardSetInObjectModel() throws SQLException {
        String sql = "INSERT INTO users(id, first_name, second_name, middle_name, passport_serial, passport_number, passport_type) VALUES\n" +
                "(1, 'Arthur', 'Davletkaliev', ' ', 0, 1, 'KZ');\n" +
                "\n" +
                "INSERT INTO accounts (id, account_number, balance, currency, user_id) values\n" +
                "(1, '11', 0, 'RUB', 1);\n" +
                "INSERT INTO cards (id, card_number, account_id) values\n" +
                "(1, '111', 1);";

        connectionDB.get().prepareStatement(sql).execute();

        Card card = new Card(1,"111", 1);
        Set<Card> extend = new HashSet<Card>(){{add(card);}};

        Set<Card> actual = cardRepository.getAllByAccountId(1);

        assertEquals(extend, actual);
    }
}
