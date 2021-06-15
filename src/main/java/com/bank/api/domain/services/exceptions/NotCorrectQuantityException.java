package com.bank.api.domain.services.exceptions;

public class NotCorrectQuantityException extends Exception{
    @Override
    public String getMessage() {
        return "Not correct quantity!";
    }
}
