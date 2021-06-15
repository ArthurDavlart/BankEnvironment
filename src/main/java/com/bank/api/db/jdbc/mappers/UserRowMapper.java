package com.bank.api.db.jdbc.mappers;

import com.bank.api.db.jdbc.RowMapper;
import com.bank.api.domain.dto.Account;
import com.bank.api.domain.dto.Passport;
import com.bank.api.domain.dto.PassportType;
import com.bank.api.domain.dto.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet resultSet) throws SQLException {
        User user = new User();

        user.setId(Long.parseLong(resultSet.getString("id")));
        user.setFirstName(resultSet.getString("first_name"));
        user.setSecondName(resultSet.getString("second_name"));
        user.setMiddleName(resultSet.getString("middle_name"));

        Passport passport = new Passport();
        passport.setSerial(Integer.parseInt(resultSet.getString("passport_serial")));
        passport.setNumber(Integer.parseInt(resultSet.getString("passport_number")));
        passport.setPassportType(PassportType.valueOf(resultSet.getString("passport_type")));

        user.setPassport(passport);

        return user;
    }

}
