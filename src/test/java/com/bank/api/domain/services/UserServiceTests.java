package com.bank.api.domain.services;

import com.bank.api.domain.dto.User;
import com.bank.api.domain.services.exceptions.NotValidUserException;
import com.bank.api.domain.services.mock.repositories.UserRepositoryMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserServiceTests {
    private UserRepositoryMock userRepositoryMock;
    private UserService userService;

    @BeforeEach
    void init(){
        userRepositoryMock = new UserRepositoryMock(new ArrayList<>());
        userService = new UserServiceImpl(userRepositoryMock);
    }

    @Test
    void create_haveNotUser_haveUserInDB() throws NotValidUserException {
        User extend = new User();

        userService.create(extend);

        assertTrue(extend == userRepositoryMock.getUsers().get(0));
    }

}
