package com.bank.api.main.springbootapp.controllers.advices;

import com.bank.api.domain.services.exceptions.AccountNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class AccountNotFoundAdvice {
    @ResponseBody
    @ExceptionHandler(AccountNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String accountNotFoundHandler(AccountNotFoundException ex) {
        return ex.getMessage();
    }
}
