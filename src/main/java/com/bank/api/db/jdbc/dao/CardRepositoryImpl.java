package com.bank.api.db.jdbc.dao;

import com.bank.api.db.jdbc.JdbcTemplate;
import com.bank.api.db.jdbc.mappers.CardRowMapper;
import com.bank.api.domain.dao.CardRepository;
import com.bank.api.domain.dto.Card;
import com.bank.api.domain.services.exceptions.ServerException;
import com.bank.api.utils.Logger;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class CardRepositoryImpl implements CardRepository {
    private final JdbcTemplate jdbcTemplate;
    private final Logger logger;

    public CardRepositoryImpl(JdbcTemplate jdbcTemplate, Logger logger) {
        this.jdbcTemplate = jdbcTemplate;
        this.logger = logger;
    }

    @Override
    public void creat(Card card) {
        try {
            String insertSql = "INSERT INTO cards(card_number, account_id) values ( ?, ? );";

            jdbcTemplate.query(insertSql, card.getNumber(), String.valueOf(card.getAccountId()));

        } catch (SQLException throwables) {
            logger.write(throwables.getMessage());
            throw new ServerException();
        }
    }

    @Override
    public void update(Card card) {

    }

    @Override
    public Card read(long id) {
        Card card = null;

        try {
            String selectSql = "Select * from cards where id = ? ;";
            CardRowMapper cardRowMapper = new CardRowMapper();

            card = jdbcTemplate.getObject(cardRowMapper, selectSql, String.valueOf(id));
        } catch (SQLException throwables) {
            logger.write(throwables.getMessage());
            throw new ServerException();
        }

        return card;
    }

    @Override
    public void delete(Card card) {

    }

    @Override
    public void begin() {

    }

    @Override
    public void commit() {

    }

    @Override
    public Set<Card> getAllByAccountId(long accountId) {
        Set<Card> cards = new HashSet<>();

        try {
            String selectSql = "Select * from cards where account_id = ? ;";

            CardRowMapper cardRowMapper = new CardRowMapper();

            cards = jdbcTemplate
                    .getObjects(cardRowMapper, selectSql, String.valueOf(accountId))
                    .stream().collect(Collectors.toSet());

        } catch (SQLException throwables) {
            logger.write(throwables.getMessage());
            throw new ServerException();
        }

        return cards;
    }

    @Override
    public Set<Card> getAll() {
        Set<Card> cards = new HashSet<>();

        try {
            String selectSql = "Select * from cards;";

            CardRowMapper cardRowMapper = new CardRowMapper();

            cards = jdbcTemplate
                    .getObjects(cardRowMapper, selectSql)
                    .stream().collect(Collectors.toSet());

        } catch (SQLException throwables) {
            logger.write(throwables.getMessage());
            throw new ServerException();
        }

        return cards;
    }

    @Override
    public Card get(String cardNumber) {
        Card card = null;

        try {
            String selectSql = "Select * from cards where card_number = ? ;";

            CardRowMapper cardRowMapper = new CardRowMapper();

            card = jdbcTemplate
                    .getObject(cardRowMapper, selectSql, cardNumber);
        } catch (SQLException throwables) {
            logger.write(throwables.getMessage());
            throw new ServerException();
        }

        return card;
    }
}
