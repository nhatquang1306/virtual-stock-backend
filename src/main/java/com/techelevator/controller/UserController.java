package com.techelevator.controller;

import com.techelevator.dao.UserDao;
import com.techelevator.model.User;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
public class UserController {
    private UserDao userDao;

    public UserController(UserDao userDao) {
        this.userDao = userDao;
    }

    @GetMapping(value = "/users/{id}")
    public User getUserById(@PathVariable int id) {
        return userDao.getUserById(id);
    }
}
