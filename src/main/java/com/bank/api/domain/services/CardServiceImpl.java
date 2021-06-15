package com.bank.api.domain.services;

import com.bank.api.domain.dao.AccountRepository;
import com.bank.api.domain.dao.CardRepository;
import com.bank.api.domain.dto.Account;
import com.bank.api.domain.dto.Card;
import com.bank.api.domain.numbergenerators.CardNumberGenerator;
import com.bank.api.domain.services.exceptions.AccountNotFoundException;
import com.bank.api.domain.services.exceptions.CardNotFoundException;

import java.util.Set;

public class CardServiceImpl implements CardService{
    private final CardRepository cardRepository;
    private final AccountService accountService;
    private final CardNumberGenerator cardNumberGenerator;

    public CardServiceImpl(AccountService accountService, CardRepository cardRepository, CardNumberGenerator cardNumberGenerator) {
        this.cardRepository = cardRepository;
        this.accountService = accountService;
        this.cardNumberGenerator = cardNumberGenerator;
    }

    @Override
    public Set<Card> getAll() {
        return cardRepository.getAll();
    }

    @Override
    public Card get(String cardNumber) throws CardNotFoundException {
        Card card = cardRepository.get(cardNumber);

        if (card == null) {
            throw new CardNotFoundException();
        }

        return cardRepository.get(cardNumber);
    }


    @Override
    public String create(String accountNumber) throws AccountNotFoundException {
        cardRepository.begin();

        Account account = accountService.get(accountNumber);

        if(account == null){
            throw new AccountNotFoundException();
        }

        String newCardNumber = cardNumberGenerator.generateCardNumber(account);
        Card newCard = new Card(newCardNumber, account.getId());
        cardRepository.creat(newCard);

        cardRepository.commit();

        return newCardNumber;
    }

    private long generateNewNumber(){
        return 0L;
    }
}
