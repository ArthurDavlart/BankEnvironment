package com.bank.api.main.springbootapp.controllers.advices;

import com.bank.api.domain.services.exceptions.AccountCurrencyException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class AccountCurrencyAdvice {
    @ResponseBody
    @ExceptionHandler(AccountCurrencyException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String accountCurrencyHandler(AccountCurrencyException ex) {
        return ex.getMessage();
    }
}
