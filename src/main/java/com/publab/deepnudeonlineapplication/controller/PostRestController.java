package com.publab.deepnudeonlineapplication.controller;

import com.publab.deepnudeonlineapplication.model.Like;
import com.publab.deepnudeonlineapplication.model.Post;
import com.publab.deepnudeonlineapplication.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PostRestController {
    private final PostService postService;

    public enum TimeSpan { DAY, WEEK, MONTH, ALLTIME }

    @Autowired
    public PostRestController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/posts")
    @ResponseStatus(HttpStatus.CREATED)
    public Post newPost(@RequestParam(value = "title") String title) {
        return postService.newPost(title);
    }

    @GetMapping("/feed")
    public List<Post> getFeed(@RequestParam(value = "startFrom") Long startPostId) {
        return postService.getFeed(startPostId);
    }

    @GetMapping("/top")
    public List<Post> getTop(@RequestParam(value = "interval") String topListTimeSpan, @RequestParam(value = "startFrom") Long startPostId) {
        TimeSpan timeSpan = TimeSpan.valueOf(topListTimeSpan.toUpperCase());
        return postService.getTopPosts(timeSpan, startPostId);
    }

    @GetMapping("/userWall")
    public List<Post> getUserWall(@RequestParam(value = "startFrom") Long startPostId) {
        return postService.getUserWall(startPostId);
    }

    @PostMapping("/likePost")
    public Like likePost(@RequestParam(value = "postId") Long postId) {
        return postService.likePost(postId);
    }

    @PostMapping("/unlikePost")
    public List<Like> unlikePost(@RequestParam(value = "postId") Long postId) {
        return postService.unlikePost(postId);
    }

}
