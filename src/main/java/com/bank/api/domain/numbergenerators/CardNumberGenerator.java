package com.bank.api.domain.numbergenerators;

import com.bank.api.domain.dto.Account;

public interface CardNumberGenerator {
    String generateCardNumber(Account account);
}
