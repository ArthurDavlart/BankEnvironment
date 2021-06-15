package com.bank.api.db.jdbc.mappers;

import com.bank.api.db.jdbc.RowMapper;
import com.bank.api.domain.dto.Card;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CardRowMapper implements RowMapper<Card> {
    @Override
    public Card mapRow(ResultSet resultSet) throws SQLException {
        Card card = new Card();

        card.setId(Long.parseLong(resultSet.getString("id")));
        card.setNumber(resultSet.getString("card_number"));
        card.setAccountId(Long.parseLong(resultSet.getString("account_id")));

        return card;
    }
}
