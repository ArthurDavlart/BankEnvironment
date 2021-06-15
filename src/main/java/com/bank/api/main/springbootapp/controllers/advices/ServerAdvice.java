package com.bank.api.main.springbootapp.controllers.advices;

import com.bank.api.domain.services.exceptions.ServerException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ServerAdvice {
    @ResponseBody
    @ExceptionHandler(ServerException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    String serverExceptionHandler(ServerException ex) {
        return ex.getMessage();
    }
}
