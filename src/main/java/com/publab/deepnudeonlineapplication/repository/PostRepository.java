package com.publab.deepnudeonlineapplication.repository;

import com.publab.deepnudeonlineapplication.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Long> {
    @Query("SELECT DISTINCT p " +
            "FROM Post p " +
            "JOIN p.user u " +
            "WHERE t.id < :startPostId " +
            "ORDER BY p.date")
    List<Post> getPostsFromStartPostId(@Param("startPostId") Integer startPostId);

    @Query("SELECT DISTINCT p " +
            "FROM Post p " +
            "JOIN p.user u " +
            "ORDER BY p.date")
    List<Post> getLatestPosts();

    //getLatestPosts
    List<Post> findTop10OrderByDate();
}
