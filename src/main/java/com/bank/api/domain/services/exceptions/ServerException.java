package com.bank.api.domain.services.exceptions;

public class ServerException extends RuntimeException{
    @Override
    public String getMessage() {
        String msg = super.getMessage();
        return "Все! Все пошло не по полан!" + msg;
    }
}
