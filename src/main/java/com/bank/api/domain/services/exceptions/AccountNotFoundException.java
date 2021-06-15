package com.bank.api.domain.services.exceptions;

public class AccountNotFoundException extends Exception{
    @Override
    public String getMessage() {
        return "Не смог найти нужный аккаунт!";
    }
}
