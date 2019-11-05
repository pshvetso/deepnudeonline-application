package com.publab.deepnudeonlineapplication.controller;

import com.publab.deepnudeonlineapplication.model.User;
import com.publab.deepnudeonlineapplication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserRestController {
    private final UserService userService;

    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/registerUser")
    public User registerUser(@RequestParam(value = "username") String username,
                             @RequestParam(value = "firstName") String firstName,
                             @RequestParam(value = "lastName") String lastName,
                             @RequestParam(value = "avatarId") byte avatarId) {
        return userService.registerUser(username, firstName, lastName, avatarId);
    }
}