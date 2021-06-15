package com.bank.api.domain.services;

import com.bank.api.domain.dao.UserRepository;
import com.bank.api.domain.dto.*;
import com.bank.api.domain.services.exceptions.AccountCurrencyException;
import com.bank.api.domain.services.exceptions.AccountNotFoundException;
import com.bank.api.domain.numbergenerators.AccountNumberGenerator;
import com.bank.api.domain.services.exceptions.NotCorrectQuantityException;
import com.bank.api.domain.services.exceptions.UserNotFoundException;
import com.bank.api.domain.services.mock.generators.AccountNumberGeneratorMock;
import com.bank.api.domain.services.mock.repositories.AccountRepositoryMock;
import com.bank.api.domain.services.mock.repositories.UserRepositoryMock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AccountServiceTests {
    private UserRepositoryMock userRepository;
    private UserService userService;
    private AccountRepositoryMock accountRepositoryMock;
    private AccountNumberGenerator accountNumberGenerator;
    private AccountService accountService;

    @BeforeEach
    void init(){
        userRepository = new UserRepositoryMock(new ArrayList<>());
        userService = new UserServiceImpl(userRepository);
        accountRepositoryMock = new AccountRepositoryMock(new ArrayList<>());
        accountNumberGenerator = new AccountNumberGeneratorMock(0);
        accountService = new AccountServiceImpl(userService, accountRepositoryMock, accountNumberGenerator);
    }

    @Test
    void create_haveNotAccount_accountInDb() throws UserNotFoundException {
        User user = new User();
        userRepository.getUsers().add(user);
        Account extend = new Account(String.valueOf(1L), 0);

        accountService.create(1L);

        assertEquals(extend, accountRepositoryMock.getAccounts().get(0));
    }

    @Test
    void get_haveNotLinkAccount_haveAccountLink() throws AccountNotFoundException {
        Account extend = new Account();
        extend.setNumber("0");
        accountRepositoryMock.getAccounts().add(extend);

        Account account = accountService.get("0");

        assertEquals(extend, account);
    }

    @Test
    void get_notAccountInSystem_throwException(){
        Assertions.assertThrows(AccountNotFoundException.class, () -> accountService.get("0"));
    }

    @Test
    void getCards_haveNotCards_haveCardArray() throws AccountNotFoundException {
        Account account = new Account();
        account.setNumber("0");
        Set<Card> extend = new HashSet<Card>(){{ add(new Card("0", account.getId()));}};
        account.setCards(extend);
        accountRepositoryMock.getAccounts().add(account);

        Set<Card> actual = accountService.getCards("0");

        assertEquals(extend.iterator().next(), actual.iterator().next());
    }

    @Test
    void getCards_notAccountInSystem_throwException(){
        Assertions.assertThrows(AccountNotFoundException.class, () -> accountService.getCards("0"));
    }

    @Test
    void depositFounds_100RubInAccount_150RubInAccount() throws AccountNotFoundException, AccountCurrencyException, NotCorrectQuantityException {
        Account account = new Account("0", 0);
        account.setBalance(BigDecimal.valueOf(100));
        BigDecimal extend = new BigDecimal(200);
        accountRepositoryMock.getAccounts().add(account);

        accountService.depositFounds("0", new Deposit(new BigDecimal(100), Currency.RUB));

        assertEquals(extend, accountRepositoryMock.getAccounts().get(0).getBalance());
    }

    @Test
    void depositFounds_100RubInAccount_throwExceptionBecauseAdd100EUR() throws AccountNotFoundException, AccountCurrencyException {
        Account account = new Account("0", 0);
        account.setBalance(BigDecimal.valueOf(100));
        accountRepositoryMock.getAccounts().add(account);

        Assertions.assertThrows(AccountCurrencyException.class,
                () -> accountService.depositFounds("0", new Deposit(new BigDecimal(100), Currency.EUR)));
    }

    @Test
    void depositFounds_notAccountInSystem_throwException(){
        Assertions.assertThrows(AccountNotFoundException.class, () -> accountService.depositFounds("0",
                new Deposit(new BigDecimal(100), Currency.RUB)));
    }

    @Test
    void getBalance_haveNotInformationAboutBalance_HaveBalanceInformation() throws AccountNotFoundException {
        BigDecimal extend = BigDecimal.valueOf(100);
        Account account = new Account("0", 0);
        account.setBalance(extend);
        accountRepositoryMock.getAccounts().add(account);

        BigDecimal actual = accountService.getBalance("0");

        assertEquals(extend, actual);
    }

    @Test
    void getBalance_notAccountInSystem_throwException(){
        Assertions.assertThrows(AccountNotFoundException.class, () -> accountService.getBalance("0"));
    }


}
