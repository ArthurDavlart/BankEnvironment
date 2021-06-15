package com.bank.api.domain.services.exceptions;

public class UserNotFoundException extends Exception{
    @Override
    public String getMessage() {
        return "User not found!";
    }
}
