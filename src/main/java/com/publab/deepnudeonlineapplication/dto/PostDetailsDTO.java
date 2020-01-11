package com.publab.deepnudeonlineapplication.dto;

import java.time.LocalDateTime;

public interface PostDetailsDTO {
    Long getId();

    LocalDateTime getDate();

    String getTitle();

    Long getUserId();

    String getUsername();

    String getFirstName();

    String getLastName();

    String getUserAvatarId();

    Long getHasBeenLiked();

    Long getHasBeenViewed();

    Integer getViews();

    Integer getLikes();
}
