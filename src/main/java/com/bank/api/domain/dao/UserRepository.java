package com.bank.api.domain.dao;

import com.bank.api.domain.dto.User;

import java.util.Set;

public interface UserRepository extends CRUDRepository<User> {
    User get(User user);
    Set<User> getAll();
}
