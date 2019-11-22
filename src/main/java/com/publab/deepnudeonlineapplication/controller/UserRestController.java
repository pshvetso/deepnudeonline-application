package com.publab.deepnudeonlineapplication.controller;

import com.publab.deepnudeonlineapplication.model.User;
import com.publab.deepnudeonlineapplication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserRestController {
    private final UserService userService;

    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    //curl -v -X POST localhost:8080/users -H "Content-type:application/x-www-form-urlencoded" -d "username=testusername&firstName=&lastName=&avatarId=0"
    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    public User registerUser(@RequestParam(value = "username") String username,
                             @RequestParam(value = "firstName") String firstName,
                             @RequestParam(value = "lastName") String lastName,
                             @RequestParam(value = "avatarId") byte avatarId) {
        return userService.registerUser(username, firstName, lastName, avatarId);
    }
}
