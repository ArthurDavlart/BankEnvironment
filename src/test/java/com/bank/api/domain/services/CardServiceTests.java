package com.bank.api.domain.services;

import com.bank.api.domain.dao.UserRepository;
import com.bank.api.domain.dto.Account;
import com.bank.api.domain.dto.Card;
import com.bank.api.domain.numbergenerators.AccountNumberGenerator;
import com.bank.api.domain.services.exceptions.AccountNotFoundException;
import com.bank.api.domain.services.mock.generators.AccountNumberGeneratorMock;
import com.bank.api.domain.services.mock.generators.CardNumberGeneratorMock;
import com.bank.api.domain.services.mock.repositories.AccountRepositoryMock;
import com.bank.api.domain.services.mock.repositories.CardRepositoryMock;
import com.bank.api.domain.services.mock.repositories.UserRepositoryMock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CardServiceTests {
    private CardRepositoryMock cardRepository;
    private AccountRepositoryMock accountRepositoryMock;
    private AccountNumberGenerator accountNumberGenerator;
    private AccountService accountService;
    private UserRepository userRepository;
    private UserService userService;
    private CardNumberGeneratorMock cardNumberGeneratorMock;
    private CardService cardService;

    @BeforeEach
    void init(){
        cardRepository = new CardRepositoryMock(new ArrayList<>());

        userRepository = new UserRepositoryMock(new ArrayList<>());

        userService = new UserServiceImpl(userRepository);

        accountRepositoryMock = new AccountRepositoryMock(new ArrayList<>());

        accountNumberGenerator = new AccountNumberGeneratorMock(0);

        accountService = new AccountServiceImpl(userService, accountRepositoryMock, accountNumberGenerator);

        cardNumberGeneratorMock = new CardNumberGeneratorMock(0);

        cardService = new CardServiceImpl(accountService, cardRepository, cardNumberGeneratorMock);
    }

    @Test
    void create_haveNotCard_haveNewCard() throws AccountNotFoundException {
        Account account = new Account();
        account.setNumber("0");
        accountRepositoryMock.getAccounts().add(account);
        Card extend = new Card(String.valueOf(1L), accountRepositoryMock.getAccounts().get(0).getId());

        cardService.create("0");

        assertEquals(extend.getNumber(), cardRepository.getCards().get(0).getNumber());
    }

    @Test
    void create_haveNotAccountInSystem_ThrowAccountNotFoundException(){
        Assertions.assertThrows(AccountNotFoundException.class, () -> cardService.create("0"));
    }

}
