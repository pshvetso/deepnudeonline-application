package com.publab.deepnudeonlineapplication.controller;

import com.publab.deepnudeonlineapplication.dto.PostDetailsDTO_;
import com.publab.deepnudeonlineapplication.dto.PostDetailsDto;
import com.publab.deepnudeonlineapplication.model.Post;
import com.publab.deepnudeonlineapplication.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PostRestController {
    private final PostService postService;

    public enum TimeSpan { DAY, WEEK, MONTH, ALLTIME }

    @PostMapping("/posts")
    @ResponseStatus(HttpStatus.CREATED)
    public Post newPost(@RequestParam String title) {
        return postService.newPost(title);
    }

    //curl -v http://localhost:8080/api/feed?startFrom=
    @GetMapping("/feed")
    public List<PostDetailsDto> getFeed(@RequestParam(value = "page") Integer pageNum) {
        return postService.getFeed(pageNum);
    }

    //http://localhost:8080/api/top?interval=ALLTIME&page=0
    //http://localhost:8080/api/top?interval=day&page=0
    @GetMapping("/top")
    public List<PostDetailsDto> getTop(@RequestParam(value = "interval") String topListTimeSpan, @RequestParam(value = "page") Integer pageNum) {
        TimeSpan timeSpan = TimeSpan.valueOf(topListTimeSpan.toUpperCase());
        return postService.getTopPosts(timeSpan, pageNum);
    }

    @GetMapping("/wall")
    public List<PostDetailsDto> getUserWall(@RequestParam(value = "id") Long userId, @RequestParam(value = "page") Integer pageNum) {
        return postService.getUserWall(userId, pageNum);
    }

    //curl -v localhost:8080/api/post?id=14
    @GetMapping("/post")
    public PostDetailsDTO_ getPost(@RequestParam Long id) {
        return postService.getPost(id);
    }

    @PostMapping("/like")
    public void like(@RequestParam Long id) {
        postService.likePost(id);
    }

    @PostMapping("/dislike")
    public void dislike(@RequestParam Long id) {
        postService.dislikePost(id);
    }

}
