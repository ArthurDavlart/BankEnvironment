package com.bank.api.main.springbootapp;

import com.bank.api.db.jdbc.JdbcTemplate;
import com.bank.api.db.jdbc.JdbcTemplateImpl;
import com.bank.api.db.jdbc.connections.ConnectionDB;
import com.bank.api.db.jdbc.connections.DBConnectionSettings;
import com.bank.api.db.jdbc.connections.H2Connection;
import com.bank.api.db.jdbc.dao.AccountRepositoryImpl;
import com.bank.api.db.jdbc.dao.CardRepositoryImpl;
import com.bank.api.db.jdbc.dao.UserRepositoryImpl;
import com.bank.api.domain.dao.AccountRepository;
import com.bank.api.domain.dao.CardRepository;
import com.bank.api.domain.dao.UserRepository;
import com.bank.api.domain.numbergenerators.AccountNumberGenerator;
import com.bank.api.domain.numbergenerators.AccountNumberGeneratorMock;
import com.bank.api.domain.numbergenerators.CardNumberGenerator;
import com.bank.api.domain.numbergenerators.CardNumberGeneratorMock;
import com.bank.api.domain.services.*;
import com.bank.api.utils.Logger;
import com.bank.api.utils.LoggerMock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class ApplicationConfiguration {

    @Bean
    public UserService userService(){
        UserService userService = new UserServiceImpl(userRepository());

        return userService;
    }

    @Bean
    public UserRepository userRepository(){
        return new UserRepositoryImpl(accountRepository(), jdbcTemplate(), logger());
    }

    @Bean
    public AccountService accountService(){
        return new AccountServiceImpl(userService(), accountRepository(), accountNumberGenerator());
    }

    @Bean
    public CardService cardService(){
        return new CardServiceImpl(accountService(), cardRepository(), cardNumberGenerator());
    }

    @Bean
    public CardNumberGenerator cardNumberGenerator(){
        return CardNumberGeneratorMock.getInstance();
    }

    @Bean
    public AccountNumberGenerator accountNumberGenerator(){
        return AccountNumberGeneratorMock.getInstance();
    }

    @Bean
    public AccountRepository accountRepository(){
        return new AccountRepositoryImpl(cardRepository(), jdbcTemplate(), logger());
    }

    @Bean
    public CardRepository cardRepository(){
        return new CardRepositoryImpl(jdbcTemplate(), logger());
    }

    @Bean
    public JdbcTemplate jdbcTemplate(){
        return new JdbcTemplateImpl(connectionDB());
    }

    @Bean
    @Scope("singleton")
    public ConnectionDB connectionDB(){
        DBConnectionSettings dbConnectionSettings =
                new DBConnectionSettings(
                        "jdbc:h2:mem:test",
                        //"jdbc:h2:/Users/davletkalievartur/Desktop/mProjects/db/prod",
                        "sa",
                        "sa"
                );

        return H2Connection.getInstance(dbConnectionSettings);
    }

    @Bean
    public Logger logger(){
        return new LoggerMock();
    }

}
