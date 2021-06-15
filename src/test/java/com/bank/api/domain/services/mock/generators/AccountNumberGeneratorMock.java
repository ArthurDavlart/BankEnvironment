package com.bank.api.domain.services.mock.generators;

import com.bank.api.domain.dto.User;
import com.bank.api.domain.numbergenerators.AccountNumberGenerator;

public class AccountNumberGeneratorMock implements AccountNumberGenerator {
    private long currentLong;

    public AccountNumberGeneratorMock(long currentLong) {
        this.currentLong = currentLong;
    }

    @Override
    public String generateAccountNumber(User user) {
        currentLong++;
        return String.valueOf(currentLong);
    }
}
