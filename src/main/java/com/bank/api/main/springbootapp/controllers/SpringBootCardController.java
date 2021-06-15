package com.bank.api.main.springbootapp.controllers;

import com.bank.api.domain.controllers.CardController;
import com.bank.api.domain.dto.Card;
import com.bank.api.domain.services.CardService;
import com.bank.api.domain.services.exceptions.AccountNotFoundException;
import com.bank.api.domain.services.exceptions.CardNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
public class SpringBootCardController implements CardController {
    private final CardService cardService;

    public SpringBootCardController(CardService cardService) {
        this.cardService = cardService;
    }

    @GetMapping("/cards")
    public Set<Card> getAll(){
        return cardService.getAll();
    }

    @GetMapping("/cards/{cardNumber}")
    public Card get(@PathVariable String cardNumber) throws CardNotFoundException {
        return cardService.get(cardNumber);
    }

    @PostMapping("/cards/account/{accountNumber}/create")
    @ResponseStatus( HttpStatus.CREATED )
    public Card create(@PathVariable String accountNumber) throws AccountNotFoundException, CardNotFoundException {
        String cardNumber = cardService.create(accountNumber);
        return cardService.get(cardNumber);
    }
}
