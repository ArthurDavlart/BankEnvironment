package com.bank.api.main.httpserver.controllers;

import com.bank.api.domain.controllers.UserController;
import com.bank.api.domain.dto.User;
import com.bank.api.domain.services.exceptions.NotValidUserException;
import com.bank.api.domain.services.exceptions.UserNotFoundException;
import com.bank.api.main.httpserver.annotations.GetMapping;
import com.bank.api.main.httpserver.annotations.PostMapping;
import com.bank.api.main.httpserver.annotations.RestController;

import java.util.Set;

@RestController(uri = "/users")
public class HttpServerUserController implements UserController {
    @Override
    @GetMapping(uri = "/{id}")
    public User get(long id) throws UserNotFoundException {
        return null;
    }

    @Override
    @GetMapping(uri = "/")
    public Set<User> getAll() {
        return null;
    }

    @Override
    @PostMapping(uri = "/create")
    public User create(User user) throws NotValidUserException {
        return null;
    }
}
