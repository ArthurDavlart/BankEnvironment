package com.bank.api.domain.services;

import com.bank.api.domain.dto.Card;
import com.bank.api.domain.services.exceptions.AccountNotFoundException;
import com.bank.api.domain.services.exceptions.CardNotFoundException;

import java.util.Set;

public interface CardService {
    Set<Card> getAll();
    Card get(String cardNumber) throws CardNotFoundException;
    String create(String accountNumber) throws AccountNotFoundException;
}
