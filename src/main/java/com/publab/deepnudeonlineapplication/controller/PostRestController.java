package com.publab.deepnudeonlineapplication.controller;

import com.publab.deepnudeonlineapplication.dto.PostDetailsDTO_;
import com.publab.deepnudeonlineapplication.dto.PostDetailsDto;
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

    //curl -v http://localhost:8080/api/feed?startFrom=
    @GetMapping(value = "/feed", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PostDetailsDto> getFeed(@RequestParam(value = "page") Integer pageNum) {
        return postService.getFeed(pageNum);
    }

    //http://localhost:8080/api/top?interval=ALLTIME&page=0
    //http://localhost:8080/api/top?interval=day&page=0
    @GetMapping(value = "/top", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PostDetailsDto> getTop(@RequestParam(value = "interval") String topListTimeSpan, @RequestParam(value = "page") Integer pageNum) {
        TimeSpan timeSpan = TimeSpan.valueOf(topListTimeSpan.toUpperCase());
        return postService.getTopPosts(timeSpan, pageNum);
    }

    @GetMapping(value = "/wall", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PostDetailsDto> getUserWall(@RequestParam(value = "id") Long userId, @RequestParam(value = "page") Integer pageNum) {
        return postService.getUserWall(userId, pageNum);
    }

    //curl -v localhost:8080/api/post?id=14
    @GetMapping(value = "/post", produces = MediaType.APPLICATION_JSON_VALUE)
    public PostDetailsDTO_ getPost(@RequestParam(value = "id") Long id) {
        return postService.getPost(id);
    }

    @PostMapping(value = "/like", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void like(@RequestParam(value = "id") Long id) {
        postService.likePost(id);
    }

    @PostMapping(value = "/dislike", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void dislike(@RequestParam(value = "id") Long id) {
        postService.dislikePost(id);
    }

}
