package com.bank.api.main.springbootapp.controllers;

import com.bank.api.domain.controllers.AccountController;
import com.bank.api.domain.dto.Account;
import com.bank.api.domain.dto.Deposit;
import com.bank.api.domain.dto.User;
import com.bank.api.domain.services.AccountService;
import com.bank.api.domain.services.exceptions.AccountCurrencyException;
import com.bank.api.domain.services.exceptions.AccountNotFoundException;
import com.bank.api.domain.services.exceptions.NotCorrectQuantityException;
import com.bank.api.domain.services.exceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Set;


@RestController
public class SpringBootAccountController implements AccountController {
    private final AccountService accountService;

    public SpringBootAccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/accounts/{accountNumber}")
    public Account get(@PathVariable String accountNumber) throws AccountNotFoundException {
        return accountService.get(accountNumber);
    }

    @GetMapping("/accounts/{accountNumber}/balance")
    public BigDecimal getBalance(@PathVariable String accountNumber) throws AccountNotFoundException {
        return accountService.getBalance(accountNumber);
    }

    @GetMapping("/accounts")
    public Set<Account> getAll(){
        return accountService.getAll();
    }

    @PostMapping("/accounts/{accountNumber}/replenishment")
    public Account depositFounds(@PathVariable String accountNumber, @RequestBody Deposit deposit)
            throws AccountNotFoundException, AccountCurrencyException, NotCorrectQuantityException {
        accountService.depositFounds(accountNumber, deposit);
        return accountService.get(accountNumber);
    }

    @PostMapping("/user/{userId}/accounts/create")
    @ResponseStatus( HttpStatus.CREATED )
    public Account create(@PathVariable long userId) throws UserNotFoundException {
        return accountService.create(userId);
    }
}
