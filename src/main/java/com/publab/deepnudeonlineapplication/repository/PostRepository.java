package com.publab.deepnudeonlineapplication.repository;

import com.publab.deepnudeonlineapplication.dto.PostDetailsDTO_;
import com.publab.deepnudeonlineapplication.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Long> {
    //getFeed implementations

    /*@Query(value =
            "SELECT " +
                    "p.post_id AS id, " +
                    "p.date AS date, " +
                    "p.title AS title, " +
                    "u.user_id AS userId, " +
                    "u.username AS username, " +
                    "u.first_name AS firstName, " +
                    "u.last_name AS lastName, " +
                    "u.avatar_id AS userAvatarId, " +
                    "l.like_id AS hasBeenLiked, " +
                    "v.view_id as hasBeenViewed, " +
                    "(SELECT COUNT(*) FROM view v WHERE v.post_id = p.post_id) AS views, " +
                    "(SELECT COUNT(*) FROM tbl_like l WHERE l.post_id = p.post_id) AS likes " +
            "FROM post p " +
            "JOIN user u " +
                    "ON p.user_id = u.user_id " +
            "LEFT JOIN tbl_like l " +
                    "ON p.post_id = l.post_id AND l.user_id = :currentUserId " +
            "LEFT JOIN view v " +
                    "ON p.post_id = v.post_id AND v.user_id = :currentUserId " +
            "ORDER BY p.date DESC " +
            "LIMIT 10;",
            nativeQuery = true)
    List<PostDetailsDTO_> getFeed(@Param("currentUserId") Long currentUserId);

    @Query(value =
            "SELECT " +
                    "p.post_id AS id, " +
                    "p.date AS date, " +
                    "p.title AS title, " +
                    "u.user_id AS userId, " +
                    "u.username AS username, " +
                    "u.first_name AS firstName, " +
                    "u.last_name AS lastName, " +
                    "u.avatar_id AS userAvatarId, " +
                    "l.like_id AS hasBeenLiked, " +
                    "v.view_id as hasBeenViewed, " +
                    "(SELECT COUNT(*) FROM view v WHERE v.post_id = p.post_id) AS views, " +
                    "(SELECT COUNT(*) FROM tbl_like l WHERE l.post_id = p.post_id) AS likes " +
                "FROM post p " +
                "JOIN user u " +
                    "ON p.user_id = u.user_id " +
                "LEFT JOIN tbl_like l " +
                    "ON p.post_id = l.post_id AND l.user_id = :currentUserId " +
                "LEFT JOIN view v " +
                    "ON p.post_id = v.post_id AND v.user_id = :currentUserId " +
                "WHERE p.date <= (SELECT date FROM post WHERE post_id = :startPostId) " +
                    "AND p.post_id != :startPostId " +
                    "ORDER BY p.date DESC " +
                "LIMIT 10;",
            nativeQuery = true)
    List<PostDetailsDTO_> getFeedLaterThanPostId(@Param("startPostId") Long startPostId, @Param("currentUserId") Long currentUserId);
    //getTopPosts implementations

    @Query(value =
            "SELECT " +
                    "p.post_id AS id, " +
                    "p.date AS date, " +
                    "p.title AS title, " +
                    "u.user_id AS userId, " +
                    "u.username AS username, " +
                    "u.first_name AS firstName, " +
                    "u.last_name AS lastName, " +
                    "u.avatar_id AS userAvatarId, " +
                    "l.like_id AS hasBeenLiked, " +
                    "v.view_id as hasBeenViewed, " +
                    "(SELECT COUNT(*) FROM view v WHERE v.post_id = p.post_id) AS views, " +
                    "(SELECT COUNT(*) FROM tbl_like l WHERE l.post_id = p.post_id) AS likes " +
            "FROM post p " +
            "JOIN user u " +
                    "ON p.user_id = u.user_id " +
            "LEFT JOIN tbl_like l " +
                    "ON p.post_id = l.post_id AND l.user_id = :currentUserId " +
            "LEFT JOIN view v " +
                    "ON p.post_id = v.post_id AND v.user_id = :currentUserId " +
            "ORDER BY likes DESC " +
            "LIMIT 10;",
            nativeQuery = true)
    List<PostDetailsDTO_> findTop10(@Param("currentUserId") Long currentUserId);

    @Query(value =
            "SELECT " +
                    "p.post_id AS id, " +
                    "p.date AS date, " +
                    "p.title AS title, " +
                    "u.user_id AS userId, " +
                    "u.username AS username, " +
                    "u.first_name AS firstName, " +
                    "u.last_name AS lastName, " +
                    "u.avatar_id AS userAvatarId, " +
                    "l.like_id AS hasBeenLiked, " +
                    "v.view_id as hasBeenViewed, " +
                    "(SELECT COUNT(*) FROM view v WHERE v.post_id = p.post_id) AS views, " +
                    "(SELECT COUNT(*) FROM tbl_like l WHERE l.post_id = p.post_id) AS likes " +
            "FROM post p " +
            "JOIN user u " +
                    "ON p.user_id = u.user_id " +
            "LEFT JOIN tbl_like l " +
                    "ON p.post_id = l.post_id AND l.user_id = :currentUserId " +
            "LEFT JOIN view v " +
                    "ON p.post_id = v.post_id AND v.user_id = :currentUserId " +
            "WHERE p.post_id < :startPostId " +
            "ORDER BY likes DESC " +
            "LIMIT 10;",
            nativeQuery = true)
    List<PostDetailsDTO_> findTop10ByIdLessThan(@Param("startPostId") Long startPostId, @Param("currentUserId") Long currentUserId);

    @Query(value =
            "SELECT " +
                    "p.post_id AS id, " +
                    "p.date AS date, " +
                    "p.title AS title, " +
                    "u.user_id AS userId, " +
                    "u.username AS username, " +
                    "u.first_name AS firstName, " +
                    "u.last_name AS lastName, " +
                    "u.avatar_id AS userAvatarId, " +
                    "l.like_id AS hasBeenLiked, " +
                    "v.view_id as hasBeenViewed, " +
                    "(SELECT COUNT(*) FROM view v WHERE v.post_id = p.post_id) AS views, " +
                    "(SELECT COUNT(*) FROM tbl_like l WHERE l.post_id = p.post_id) AS likes " +
            "FROM post p " +
            "JOIN user u " +
                    "ON p.user_id = u.user_id " +
            "LEFT JOIN tbl_like l " +
                    "ON p.post_id = l.post_id AND l.user_id = :currentUserId " +
            "LEFT JOIN view v " +
                    "ON p.post_id = v.post_id AND v.user_id = :currentUserId " +
            "WHERE p.date > :startOfTimeSpan " +
            "ORDER BY likes DESC " +
            "LIMIT 10;",
            nativeQuery = true)
    List<PostDetailsDTO_> findTop10InTimeSpan(@Param("startOfTimeSpan") LocalDateTime startOfTimeSpan, @Param("currentUserId") Long currentUserId);

    @Query(value =
            "SELECT " +
                    "p.post_id AS id, " +
                    "p.date AS date, " +
                    "p.title AS title, " +
                    "u.user_id AS userId, " +
                    "u.username AS username, " +
                    "u.first_name AS firstName, " +
                    "u.last_name AS lastName, " +
                    "u.avatar_id AS userAvatarId, " +
                    "l.like_id AS hasBeenLiked, " +
                    "v.view_id as hasBeenViewed, " +
                    "(SELECT COUNT(*) FROM view v WHERE v.post_id = p.post_id) AS views, " +
                    "(SELECT COUNT(*) FROM tbl_like l WHERE l.post_id = p.post_id) AS likes " +
            "FROM post p " +
            "JOIN user u " +
                    "ON p.user_id = u.user_id " +
            "LEFT JOIN tbl_like l " +
                    "ON p.post_id = l.post_id AND l.user_id = :currentUserId " +
            "LEFT JOIN view v " +
                    "ON p.post_id = v.post_id AND v.user_id = :currentUserId " +
            "WHERE p.date > :startOfTimeSpan " +
                    "AND p.post_id < :startPostId " +
            "ORDER BY likes DESC " +
            "LIMIT 10;",
            nativeQuery = true)
    List<PostDetailsDTO_> findTop10InTimeSpanByIdLessThan(@Param("startOfTimeSpan") LocalDateTime startOfTimeSpan, @Param("startPostId") Long startPostId, @Param("currentUserId") Long currentUserId);
    */
    //getUserPosts implementations

    /*@Query(value =
            "SELECT " +
                    "p.post_id AS id, " +
                    "p.date AS date, " +
                    "p.title AS title, " +
                    "u.user_id AS userId, " +
                    "u.username AS username, " +
                    "u.first_name AS firstName, " +
                    "u.last_name AS lastName, " +
                    "u.avatar_id AS userAvatarId, " +
                    "l.like_id AS hasBeenLiked, " +
                    "v.view_id as hasBeenViewed, " +
                    "(SELECT COUNT(*) FROM view v WHERE v.post_id = p.post_id) AS views, " +
                    "(SELECT COUNT(*) FROM tbl_like l WHERE l.post_id = p.post_id) AS likes " +
            "FROM post p " +
            "JOIN user u " +
                    "ON p.user_id = u.user_id " +
            "LEFT JOIN tbl_like l " +
                    "ON p.post_id = l.post_id AND l.user_id = :currentUserId " +
            "LEFT JOIN view v " +
                    "ON p.post_id = v.post_id AND v.user_id = :currentUserId " +
            "WHERE p.user_id = :userId " +
            "ORDER BY p.date DESC " +
            "LIMIT 10;",
            nativeQuery = true)
    List<PostDetailsDTO_> findLatestPostsByUserId(@Param("userId") Long userId, @Param("currentUserId") Long currentUserId);

    @Query(value =
            "SELECT " +
                    "p.post_id AS id, " +
                    "p.date AS date, " +
                    "p.title AS title, " +
                    "u.user_id AS userId, " +
                    "u.username AS username, " +
                    "u.first_name AS firstName, " +
                    "u.last_name AS lastName, " +
                    "u.avatar_id AS userAvatarId, " +
                    "l.like_id AS hasBeenLiked, " +
                    "v.view_id as hasBeenViewed, " +
                    "(SELECT COUNT(*) FROM view v WHERE v.post_id = p.post_id) AS views, " +
                    "(SELECT COUNT(*) FROM tbl_like l WHERE l.post_id = p.post_id) AS likes " +
            "FROM post p " +
            "JOIN user u " +
                    "ON p.user_id = u.user_id " +
            "LEFT JOIN tbl_like l " +
                    "ON p.post_id = l.post_id AND l.user_id = :currentUserId " +
            "LEFT JOIN view v " +
                    "ON p.post_id = v.post_id AND v.user_id = :currentUserId " +
            "WHERE p.date <= (SELECT date FROM post WHERE post_id = :startPostId) " +
                    "AND p.post_id != :startPostId " +
                    "AND p.user_id = :userId " +
            "ORDER BY p.date DESC " +
            "LIMIT 10;",
            nativeQuery = true)
    List<PostDetailsDTO_> findLatestPostsByUserIdAndLaterThanPostId(@Param("userId") Long userId, @Param("startPostId") Long startPostId, @Param("currentUserId") Long currentUserId);*/

    @Query(value =
            "SELECT " +
                    "p.post_id AS id, " +
                    "p.date AS date, " +
                    "p.title AS title, " +
                    "u.user_id AS userId, " +
                    "u.username AS username, " +
                    "u.first_name AS firstName, " +
                    "u.last_name AS lastName, " +
                    "u.avatar_id AS userAvatarId, " +
                    "l.like_id AS hasBeenLiked, " +
                    "v.view_id as hasBeenViewed, " +
                    "(SELECT COUNT(*) FROM view v WHERE v.post_id = p.post_id) AS views, " +
                    "(SELECT COUNT(*) FROM tbl_like l WHERE l.post_id = p.post_id) AS likes " +
                    "FROM post p " +
                    "JOIN user u " +
                    "ON p.user_id = u.user_id " +
                    "LEFT JOIN tbl_like l " +
                    "ON p.post_id = l.post_id AND l.user_id = :currentUserId " +
                    "LEFT JOIN view v " +
                    "ON p.post_id = v.post_id AND v.user_id = :currentUserId " +
                    "WHERE p.post_id = :id ;",
            nativeQuery = true)
    PostDetailsDTO_ getPost(@Param("id") Long id, @Param("currentUserId") Long currentUserId);

}
