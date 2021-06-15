package com.bank.api.domain.controllers;

import com.bank.api.domain.dto.Card;
import com.bank.api.domain.services.exceptions.AccountNotFoundException;
import com.bank.api.domain.services.exceptions.CardNotFoundException;

import java.util.Set;

public interface CardController {
    Card get(String cardNumber) throws CardNotFoundException;
    Set<Card> getAll();
    Card create(String accountNumber) throws AccountNotFoundException, CardNotFoundException;
}
