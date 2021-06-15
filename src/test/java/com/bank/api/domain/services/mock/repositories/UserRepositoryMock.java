package com.bank.api.domain.services.mock.repositories;

import com.bank.api.domain.dao.UserRepository;
import com.bank.api.domain.dto.User;

import java.util.List;
import java.util.Set;

public class UserRepositoryMock implements UserRepository {
    private List<User> users;

    public UserRepositoryMock(List<User> users) {
        this.users = users;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public void creat(User user) {
        users.add(user);
    }

    @Override
    public void update(User user) {

    }

    @Override
    public User read(long id) {
        return this.users.get((int) id - 1);
    }

    @Override
    public void delete(User user) {

    }

    @Override
    public void begin() {

    }

    @Override
    public void commit() {

    }

    @Override
    public User get(User user) {
        return users.get(0);
    }

    @Override
    public Set<User> getAll() {
        return null;
    }
}
