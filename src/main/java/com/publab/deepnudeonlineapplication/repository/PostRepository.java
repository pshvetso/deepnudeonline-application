package com.publab.deepnudeonlineapplication.repository;

import com.publab.deepnudeonlineapplication.model.Post;
import com.publab.deepnudeonlineapplication.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface PostRepository extends JpaRepository<Post,Long> {
    //getFeed implementations

    List<Post> findTop10ByOrderByDateDesc();
    List<Post> findTop10ByIdLessThanOrderByDateDesc(Long startPostId);

    //getTopPosts implementations

    List<Post> findTop10ByOrderByLikesDesc();
    List<Post> findTop10ByIdLessThanOrderByLikesDesc(Long startPostId);

    List<Post> findTop10ByDateGreaterThanOrderByLikesDesc(LocalDateTime startOfTimeSpan);
    List<Post> findTop10ByDateGreaterThanAndIdLessThanOrderByLikesDesc(LocalDateTime startOfTimeSpan, Long startPostId);

    //getUserPosts implementations

    List<Post> findTop10ByUserOrderByDateDesc(User user);
    List<Post> findTop10ByUserAndIdLessThanOrderByDateDesc(User user, Long startPostId);
}
