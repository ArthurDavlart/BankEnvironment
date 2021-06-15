package com.bank.api.main.springbootapp.controllers.advices;

import com.bank.api.domain.services.exceptions.NotCorrectQuantityException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class NotCorrectQuantityAdvice {
    @ResponseBody
    @ExceptionHandler(NotCorrectQuantityException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String notCorrectQuantityHandler(NotCorrectQuantityException ex) {
        return ex.getMessage();
    }
}
