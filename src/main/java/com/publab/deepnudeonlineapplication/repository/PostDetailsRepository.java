package com.publab.deepnudeonlineapplication.repository;

import com.publab.deepnudeonlineapplication.dto.PostDetailsDto;

import java.time.LocalDateTime;
import java.util.List;

public interface PostDetailsRepository {
    List<PostDetailsDto> getFeedLaterThanPostId(Integer pageNum, Long currentUserId);

    List<PostDetailsDto> findTop10InTimeSpan(LocalDateTime startOfTimeSpan, Integer pageNum, Long currentUserId);

    List<PostDetailsDto> getUserWall(Long userId, Integer pageNum, Long currentUserId);
}
