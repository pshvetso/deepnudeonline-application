package com.publab.deepnudeonlineapplication.controller;

import com.publab.deepnudeonlineapplication.model.Post;
import com.publab.deepnudeonlineapplication.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PostRestController {
    private final PostService postService;

    @Autowired
    public PostRestController(PostService postService) {
        this.postService = postService;
    }

    @RequestMapping("/newPost")
    public Post newPost(@RequestParam(value = "title") String title) {
        return postService.newPost(title);
    }

    @RequestMapping("/getFeed")
    public List<Post> getFeed(@RequestParam(value = "start") Integer startPostId) {
        return postService.getFeed(startPostId);
    }
}
