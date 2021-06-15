package com.bank.api.domain.services;

import com.bank.api.domain.dto.Account;
import com.bank.api.domain.dto.Card;
import com.bank.api.domain.dto.Deposit;
import com.bank.api.domain.dto.User;
import com.bank.api.domain.services.exceptions.AccountCurrencyException;
import com.bank.api.domain.services.exceptions.AccountNotFoundException;
import com.bank.api.domain.services.exceptions.NotCorrectQuantityException;
import com.bank.api.domain.services.exceptions.UserNotFoundException;

import java.math.BigDecimal;
import java.util.Set;

public interface AccountService {
    Account create(long userId) throws UserNotFoundException;
    Account get(String accountNumber) throws AccountNotFoundException;
    Set<Account> getAll();
    Set<Card> getCards(String accountNumber) throws AccountNotFoundException;
    void depositFounds(String accountNumber, Deposit deposit) throws AccountNotFoundException, AccountCurrencyException, NotCorrectQuantityException;
    BigDecimal getBalance(String accountNumber) throws AccountNotFoundException;
}
