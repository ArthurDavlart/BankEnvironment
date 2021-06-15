package com.bank.api.domain.numbergenerators;

import com.bank.api.domain.dto.Account;

public class CardNumberGeneratorMock implements CardNumberGenerator{
    private static CardNumberGenerator instance;
    private long currentLong;

    private CardNumberGeneratorMock(long currentLong){
        this.currentLong = currentLong;
    }

    public static CardNumberGenerator getInstance(){
        if (instance == null) {
            synchronized(CardNumberGeneratorMock.class) {
                instance = new CardNumberGeneratorMock(0);
            }
        }
        return instance;
    }
    @Override
    public String generateCardNumber(Account account) {
        currentLong++;
        return String.valueOf(currentLong);
    }
}
