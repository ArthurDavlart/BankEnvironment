package com.bank.api.domain.numbergenerators;

import com.bank.api.domain.dto.User;

public interface AccountNumberGenerator {
    String generateAccountNumber(User user);
}
