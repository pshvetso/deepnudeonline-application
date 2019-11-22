package com.publab.deepnudeonlineapplication.repository;

import com.publab.deepnudeonlineapplication.dto.PostDetailsDTO;
import com.publab.deepnudeonlineapplication.model.Post;
import com.publab.deepnudeonlineapplication.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface PostRepository extends JpaRepository<Post,Long> {
    //getFeed implementations

    List<Post> findTop10ByOrderByDateDesc();
    List<Post> findTop10ByIdLessThanOrderByDateDesc(Long startPostId);

    @Query(value =
            "SELECT " +
                    "p.user_id AS id, " +
                    "u.user_id AS userId, " +
                    "l.like_id AS likeId, " +
                    "u.username AS username, " +
                    "u.avatar_id AS userAvatarId, " +
                    "p.date AS date, " +
                    "p.title AS title, " +
                    "(SELECT COUNT(*) FROM view v WHERE v.post_id = p.post_id) AS views, " +
                    "(SELECT COUNT(*) FROM tbl_like l WHERE l.post_id = p.post_id) AS likes " +
            "FROM post p JOIN user u " +
                    "ON p.user_id = u.user_id " +
            "LEFT JOIN tbl_like l " +
                    "ON p.post_id = l.post_id AND l.user_id = :currentUserId " +
            "ORDER BY p.date " +
            "LIMIT 10;",
            nativeQuery = true)
    List<PostDetailsDTO> getFeed(@Param("currentUserId") Long currentUserId);

    //getTopPosts implementations

    List<Post> findTop10ByOrderByIdDesc();
    List<Post> findTop10ByIdLessThanOrderByIdDesc(Long startPostId);

    List<Post> findTop10ByDateGreaterThanOrderByIdDesc(LocalDateTime startOfTimeSpan);
    List<Post> findTop10ByDateGreaterThanAndIdLessThanOrderByIdDesc(LocalDateTime startOfTimeSpan, Long startPostId);

    //getUserPosts implementations

    List<Post> findTop10ByUserOrderByDateDesc(User user);
    List<Post> findTop10ByUserAndIdLessThanOrderByDateDesc(User user, Long startPostId);
}
