package com.bank.api.main.springbootapp.controllers;

import com.bank.api.domain.controllers.UserController;
import com.bank.api.domain.dto.User;
import com.bank.api.domain.services.UserService;
import com.bank.api.domain.services.exceptions.NotValidUserException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
public class SpringBootUserController implements UserController {
    private final UserService userService;

    public SpringBootUserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public Set<User> getAll(){
        return userService.getAll();
    }

    @GetMapping("/users/{id}")
    public User get(@PathVariable long id){
        return userService.get(id);
    }

    @PostMapping("/users/create")
    @ResponseStatus( HttpStatus.CREATED )
    public User create(@RequestBody User user) throws NotValidUserException {
        return userService.create(user);
    }

    @PostMapping("/users/{id}/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id){
        userService.delete(id);
    }
}
