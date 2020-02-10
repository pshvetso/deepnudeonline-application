package com.publab.deepnudeonlineapplication.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class PostDetailsDto {
    private Long id;
    private LocalDateTime date;
    private String title;
    private Long userId;
    private String username;
    private String firstName;
    private String lastName;
    private Byte userAvatarId;
    private Long hasBeenLiked;
    private Long hasBeenViewed;
    private Long views;
    private Long likes;
}
