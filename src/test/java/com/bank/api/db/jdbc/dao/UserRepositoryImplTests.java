package com.bank.api.db.jdbc.dao;

import com.bank.api.db.jdbc.JdbcTemplate;
import com.bank.api.db.jdbc.JdbcTemplateImpl;
import com.bank.api.db.jdbc.connections.ConnectionDB;
import com.bank.api.db.jdbc.connections.DBConnectionSettings;
import com.bank.api.db.jdbc.connections.H2Connection;
import com.bank.api.db.jdbc.dao.AccountRepositoryImpl;
import com.bank.api.db.jdbc.dao.CardRepositoryImpl;
import com.bank.api.db.jdbc.dao.UserRepositoryImpl;
import com.bank.api.db.jdbc.tablecreator.TableCreator;
import com.bank.api.domain.dao.AccountRepository;
import com.bank.api.domain.dao.CardRepository;
import com.bank.api.domain.dao.UserRepository;
import com.bank.api.domain.dto.Passport;
import com.bank.api.domain.dto.PassportType;
import com.bank.api.domain.dto.User;
import com.bank.api.utils.Logger;
import com.bank.api.utils.LoggerMock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserRepositoryImplTests {
    private ConnectionDB connectionDB;
    private UserRepository userRepository;
    private AccountRepository accountRepository;
    private CardRepository cardRepository;
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
        TableCreator tableCreator = new TableCreator(connectionDB);
        tableCreator.createUsersTable();
        tableCreator.createAccountsTable();
        tableCreator.createCardsTable();

        jdbcTemplate = new JdbcTemplateImpl(connectionDB);

        cardRepository = new CardRepositoryImpl(jdbcTemplate, logger);
        accountRepository = new AccountRepositoryImpl(cardRepository, jdbcTemplate, logger);
        userRepository = new UserRepositoryImpl(accountRepository, jdbcTemplate, logger);
    }

    @AfterEach
    void destroy() throws Exception {
        connectionDB.close();
    }


    @Test
    void createAndRead_haveNotUserInDb_UserInDb(){
        User extend = new User();
        extend.setId(1);
        extend.setFirstName("Arthur");
        extend.setSecondName("Davletkaliev");
        extend.setPassport(new Passport(0, 111111, PassportType.KZ));

        userRepository.creat(extend);
        User actual = userRepository.read(1);

        assertEquals(extend, actual);
    }

    @Test
    void read_InSystem1User2Account4Cards_HaveUserFromDbWithAllData() throws SQLException {
        String sql = "\n" +
                "INSERT INTO users(id, first_name, second_name, middle_name, passport_serial, passport_number, passport_type) VALUES\n" +
                "(1, 'Arthur', 'Davletkaliev', ' ', 0, 1, 'KZ');\n" +
                "\n" +
                "INSERT INTO accounts (id, account_number, balance, currency, user_id) values\n" +
                "(11, 11, 0, 'RUB', 1),\n" +
                "(12, 12, 0, 'RUB', 1);\n" +
                "\n" +
                "INSERT INTO cards (id, card_number, account_id) values\n" +
                "(1, 111, 11),\n" +
                "(2, 112, 11),\n" +
                "(3, 121, 12),\n" +
                "(4, 122, 12);";

        connectionDB.get().prepareStatement(sql).execute();

        User user = userRepository.read(1);

        assertNotNull(user);
        assertEquals(2, user.getAccounts().size());
        assertEquals(2, user.getAccounts().iterator().next().getCards().size());
        assertEquals(2, user.getAccounts().iterator().next().getCards().size());
    }

    @Test
    void getAll_InSystem1User2Account4Cards_HaveUserFromDbWithAllData() throws SQLException {
        String sql = "\n" +
                "INSERT INTO users(id, first_name, second_name, middle_name, passport_serial, passport_number, passport_type) VALUES\n" +
                "(1, 'Arthur', 'Davletkaliev', ' ', 0, 1, 'KZ');\n" +
                "\n" +
                "INSERT INTO accounts (id, account_number, balance, currency, user_id) values\n" +
                "(11, 11, 0, 'RUB', 1),\n" +
                "(12, 12, 0, 'RUB', 1);\n" +
                "\n" +
                "INSERT INTO cards (id, card_number, account_id) values\n" +
                "(1, 111, 11),\n" +
                "(2, 112, 11),\n" +
                "(3, 121, 12),\n" +
                "(4, 122, 12);";

        connectionDB.get().prepareStatement(sql).execute();

        Set<User> extend = userRepository.getAll();

        User user = extend.iterator().next();

        assertNotNull(user);
        assertEquals(2, user.getAccounts().size());
        assertEquals(2, user.getAccounts().iterator().next().getCards().size());
        assertEquals(2, user.getAccounts().iterator().next().getCards().size());
    }

}
