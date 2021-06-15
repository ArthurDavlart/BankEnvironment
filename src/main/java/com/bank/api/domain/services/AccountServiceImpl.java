package com.bank.api.domain.services;

import com.bank.api.domain.dao.AccountRepository;
import com.bank.api.domain.dao.UserRepository;
import com.bank.api.domain.dto.Account;
import com.bank.api.domain.dto.Card;
import com.bank.api.domain.dto.Deposit;
import com.bank.api.domain.dto.User;
import com.bank.api.domain.services.exceptions.AccountCurrencyException;
import com.bank.api.domain.services.exceptions.AccountNotFoundException;
import com.bank.api.domain.numbergenerators.AccountNumberGenerator;
import com.bank.api.domain.services.exceptions.NotCorrectQuantityException;
import com.bank.api.domain.services.exceptions.UserNotFoundException;

import java.math.BigDecimal;
import java.util.Set;

public class AccountServiceImpl implements AccountService{
    private final UserService userService;
    private final AccountRepository accountRepository;
    private final AccountNumberGenerator accountNumberGenerator;

    public AccountServiceImpl(UserService userService, AccountRepository accountRepository, AccountNumberGenerator accountNumberGenerator) {
        this.userService = userService;
        this.accountRepository = accountRepository;
        this.accountNumberGenerator = accountNumberGenerator;
    }

    @Override
    public Account create(long userId) throws UserNotFoundException {
        User userDB = userService.get(userId);

        if (userDB == null) {
            throw new UserNotFoundException();
        }

        String accountNumber = accountNumberGenerator.generateAccountNumber(userDB);

        Account newAccount = new Account(accountNumber, userDB.getId());
        accountRepository.creat(newAccount);

        return accountRepository.get(accountNumber);
    }

    @Override
    public Account get(String accountNumber) throws AccountNotFoundException {
        Account account = this.accountRepository.get(accountNumber);

        if (account == null) {
            throw new AccountNotFoundException();
        }

        return account;
    }

    @Override
    public Set<Account> getAll() {
        return this.accountRepository.getAll();
    }

    @Override
    public Set<Card> getCards(String accountNumber) throws AccountNotFoundException {
        Account account = this.accountRepository.get(accountNumber);

        if (account == null) {
            throw new AccountNotFoundException();
        }

        return account.getCards();
    }

    @Override
    public void depositFounds(String accountNumber, Deposit deposit)
            throws AccountNotFoundException, AccountCurrencyException, NotCorrectQuantityException {

        if (deposit.getQuantity().compareTo(new BigDecimal("0")) == -1){
            throw new NotCorrectQuantityException();
        }

        accountRepository.begin();

        Account account = accountRepository.get(accountNumber);

        if (account == null) {
            throw new AccountNotFoundException();
        }

        if (account.getCurrency() != deposit.getCurrency()){
            throw new AccountCurrencyException();
        }

        account.setBalance(account.getBalance().add(deposit.getQuantity()));
        accountRepository.update(account);

        accountRepository.commit();
    }

    @Override
    public BigDecimal getBalance(String accountNumber) throws AccountNotFoundException{
        Account account = this.accountRepository.get(accountNumber);

        if (account == null) {
            throw new AccountNotFoundException();
        }

        return account.getBalance();
    }
}
