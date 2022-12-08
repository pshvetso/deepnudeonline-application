package com.publab.deepnudeonlineapplication.controller;

import com.publab.deepnudeonlineapplication.model.User;
import com.publab.deepnudeonlineapplication.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserRestController {
    private final UserService userService;

    //curl -v -X POST localhost:8080/api/users -H "Content-type:application/x-www-form-urlencoded" -d "username=testusername&firstName=&lastName=&avatarId=0"
    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    // domain object
    public User registerUser(@RequestParam(value = "username") String username,
                             @RequestParam(value = "firstName") String firstName,
                             @RequestParam(value = "lastName") String lastName,
                             @RequestParam(value = "avatarId") byte avatarId) {
        return userService.registerUser(username, firstName, lastName, avatarId);
    }
}
