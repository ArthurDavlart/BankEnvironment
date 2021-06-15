package com.bank.api.domain.numbergenerators;

import com.bank.api.domain.dto.User;

public class AccountNumberGeneratorMock implements AccountNumberGenerator {
    private long currentLong;
    private static AccountNumberGeneratorMock instance;

    private AccountNumberGeneratorMock(long currentLong){
        this.currentLong = currentLong;
    }

    public static synchronized AccountNumberGeneratorMock getInstance() {
        if (instance == null){
            instance = new AccountNumberGeneratorMock(0);
        }
        return instance;
    }

    @Override
    public synchronized String generateAccountNumber(User user) {
        currentLong++;
        return String.valueOf(currentLong);
    }
}
