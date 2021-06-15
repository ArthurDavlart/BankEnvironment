package com.bank.api.db.jdbc.mappers;

import com.bank.api.db.jdbc.RowMapper;
import com.bank.api.domain.dto.Account;
import com.bank.api.domain.dto.Currency;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountRowMapper implements RowMapper<Account> {
    @Override
    public Account mapRow(ResultSet resultSet) throws SQLException {
        Account account = new Account();
        account.setId(Long.parseLong(resultSet.getString("id")));
        account.setNumber(resultSet.getString("account_number"));
        account.setBalance(new BigDecimal(resultSet.getString("balance")));
        account.setCurrency(Currency.valueOf(resultSet.getString("currency")));
        account.setUserId(Long.parseLong(resultSet.getString("user_id")));

        return account;
    }
}
