package com.bank.api.domain.services.mock.generators;

import com.bank.api.domain.dto.Account;
import com.bank.api.domain.numbergenerators.CardNumberGenerator;

import java.math.BigInteger;

public class CardNumberGeneratorMock implements CardNumberGenerator {
    private long currentNumber;

    public CardNumberGeneratorMock(long currentNumber) {
        this.currentNumber = currentNumber;
    }

    @Override
    public String generateCardNumber(Account account) {
        currentNumber ++;
        return String.valueOf(currentNumber);
    }
}
