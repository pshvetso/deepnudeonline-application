package com.publab.deepnudeonlineapplication.dto;

import java.time.LocalDateTime;

public interface PostDetailsDTO {
    Long getId();

    Long getUserId();

    Long getLikeId();

    String getUsername();

    String getUserAvatarId();

    LocalDateTime getDate();

    String getTitle();

    Integer getViews();

    Integer getLikes();
}
