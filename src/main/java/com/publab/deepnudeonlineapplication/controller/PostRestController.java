package com.publab.deepnudeonlineapplication.controller;

import com.publab.deepnudeonlineapplication.model.Like;
import com.publab.deepnudeonlineapplication.model.Post;
import com.publab.deepnudeonlineapplication.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PostRestController {
    private final PostService postService;

    public enum TimeSpan { DAY, WEEK, MONTH, ALLTIME }

    @Autowired
    public PostRestController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/newPost")
    public Post newPost(@RequestParam(value = "title") String title) {
        return postService.newPost(title);
    }

    @RequestMapping("/getFeed")
    public List<Post> getFeed(@RequestParam(value = "startFrom") Long startPostId) {
        return postService.getFeed(startPostId);
    }

    @RequestMapping("/getTop")
    public List<Post> getTop(@RequestParam(value = "interval") String topListTimeSpan, @RequestParam(value = "startFrom") Long startPostId) {
        TimeSpan timeSpan = TimeSpan.valueOf(topListTimeSpan.toUpperCase());
        return postService.getTopPosts(timeSpan, startPostId);
    }

    @RequestMapping("/getUserWall")
    public List<Post> getUserWall(@RequestParam(value = "startFrom") Long startPostId) {
        return postService.getUserWall(startPostId);
    }

    @RequestMapping("/likePost")
    public Like likePost(@RequestParam(value = "postId") Long postId) {
        return postService.likePost(postId);
    }

    @RequestMapping("/unlikePost")
    public List<Like> unlikePost(@RequestParam(value = "postId") Long postId) {
        return postService.unlikePost(postId);
    }

}
