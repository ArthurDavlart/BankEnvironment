package com.bank.api.domain.services;

import com.bank.api.domain.dto.User;
import com.bank.api.domain.services.exceptions.NotValidUserException;

import java.util.Set;

public interface UserService {
    User create(User user) throws NotValidUserException;
    Set<User> getAll();
    User get(long id);
    void delete(long id);
}
