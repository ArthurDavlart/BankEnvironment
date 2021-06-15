package com.bank.api.domain.controllers;

import com.bank.api.domain.dto.User;
import com.bank.api.domain.services.exceptions.NotValidUserException;
import com.bank.api.domain.services.exceptions.UserNotFoundException;

import java.util.Set;

public interface UserController {
    User get(long id) throws UserNotFoundException;
    Set<User> getAll();
    User create(User user) throws NotValidUserException;
}
