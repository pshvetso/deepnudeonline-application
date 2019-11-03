package com.publab.deepnudeonlineapplication.service;

import com.publab.deepnudeonlineapplication.model.Post;
import com.publab.deepnudeonlineapplication.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service("postService")
public class PostService {
    private PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Post newPost(String title) {
        // TODO set current user
        Post newPost = Post.builder()
                .title(title)
                .date(LocalDateTime.now())
                .build();

        postRepository.saveAndFlush(newPost);

        return newPost;
    }

    public List<Post> getFeed(Integer startPostId) {
        if(startPostId == null) {
            return postRepository.getLatestPosts();
        } else {
            return postRepository.getPostsFromStartPostId(startPostId);
        }
    }
}
