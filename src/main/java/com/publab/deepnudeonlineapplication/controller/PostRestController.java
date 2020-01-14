package com.publab.deepnudeonlineapplication.controller;

import com.publab.deepnudeonlineapplication.dto.PostDetailsDTO;
import com.publab.deepnudeonlineapplication.model.Like;
import com.publab.deepnudeonlineapplication.model.Post;
import com.publab.deepnudeonlineapplication.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
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

    //curl -v localhost:8080/api/feed?startFrom=0
    @GetMapping(value = "/feed", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PostDetailsDTO> getFeed(@RequestParam(value = "startFrom") Long startPostId) {
        return postService.getFeed(startPostId);
    }

    @GetMapping("/top")
    public List<PostDetailsDTO> getTop(@RequestParam(value = "interval") String topListTimeSpan, @RequestParam(value = "startFrom") Long startPostId) {
        TimeSpan timeSpan = TimeSpan.valueOf(topListTimeSpan.toUpperCase());
        return postService.getTopPosts(timeSpan, startPostId);
    }

    @GetMapping("/userWall")
    public List<PostDetailsDTO> getUserWall(@RequestParam(value = "userId") Long userId, @RequestParam(value = "startFrom") Long startPostId) {
        return postService.getUserWall(userId, startPostId);
    }

    //curl -v localhost:8080/api/post?id=14
    @GetMapping("/post")
    public PostDetailsDTO getPost(@RequestParam(value = "id") Long id) {
        return postService.getPost(id);
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
