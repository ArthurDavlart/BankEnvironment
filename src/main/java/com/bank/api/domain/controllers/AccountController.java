package com.bank.api.domain.controllers;

import com.bank.api.domain.dto.Account;
import com.bank.api.domain.dto.Deposit;
import com.bank.api.domain.services.exceptions.AccountCurrencyException;
import com.bank.api.domain.services.exceptions.AccountNotFoundException;
import com.bank.api.domain.services.exceptions.NotCorrectQuantityException;
import com.bank.api.domain.services.exceptions.UserNotFoundException;

import java.math.BigDecimal;
import java.util.Set;

public interface AccountController {
    Account get(String accountNumber) throws AccountNotFoundException;
    Set<Account> getAll();
    BigDecimal getBalance(String accountNumber) throws AccountNotFoundException;
    Account depositFounds(String accountNumber, Deposit deposit) throws AccountNotFoundException, AccountCurrencyException, NotCorrectQuantityException;
    Account create(long userId) throws UserNotFoundException;
}
