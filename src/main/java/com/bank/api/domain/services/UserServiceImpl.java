package com.bank.api.domain.services;

import com.bank.api.domain.dao.UserRepository;
import com.bank.api.domain.dto.User;
import com.bank.api.domain.services.exceptions.NotValidUserException;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User create(User user) throws NotValidUserException {
        if (!isValid(user)){
            throw new NotValidUserException();
        }

        userRepository.creat(user);

        return userRepository.get(user);
    }

    @Override
    public Set<User> getAll() {
        return userRepository.getAll();
    }

    @Override
    public User get(long id) {
        return userRepository.read(id);
    }

    @Override
    public void delete(long id) {
        User user = new User();
        user.setId(id);
        this.userRepository.delete(user);
    }

    // todo реализовать валидацию юзера
    private boolean isValid(User user){
        return true;
    }
}
