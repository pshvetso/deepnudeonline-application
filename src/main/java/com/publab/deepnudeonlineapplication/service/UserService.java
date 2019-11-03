package com.publab.deepnudeonlineapplication.service;

import com.publab.deepnudeonlineapplication.model.User;
import com.publab.deepnudeonlineapplication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

@Service("userService")
public class UserService {
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerUser(String username, String firstName, String lastName, Byte avatarId) {
        User newUser = User.builder()
                .username(username)
                .firstName(firstName)
                .lastName(lastName)
                .avatarId(avatarId)
                .lastVisitDate(LocalDateTime.now())
                .build();

        // TODO check if hash exists?
        newUser.setHash(Objects.hash(newUser));
        userRepository.saveAndFlush(newUser);

        return newUser;
    }
}
