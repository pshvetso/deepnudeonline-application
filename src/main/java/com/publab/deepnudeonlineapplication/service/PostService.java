package com.publab.deepnudeonlineapplication.service;

import com.publab.deepnudeonlineapplication.controller.PostRestController;
import com.publab.deepnudeonlineapplication.model.Like;
import com.publab.deepnudeonlineapplication.model.Post;
import com.publab.deepnudeonlineapplication.model.User;
import com.publab.deepnudeonlineapplication.model.View;
import com.publab.deepnudeonlineapplication.repository.LikeRepository;
import com.publab.deepnudeonlineapplication.repository.PostRepository;
import com.publab.deepnudeonlineapplication.repository.ViewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service("postService")
public class PostService {
    private final PostRepository postRepository;
    private final LikeRepository likeRepository;
    private final ViewRepository viewRepository;

    // TODO set current user
    private final User loggedInUser = User.builder().build();

    @Autowired
    public PostService(PostRepository postRepository, LikeRepository likeRepository, ViewRepository viewRepository) {
        this.postRepository = postRepository;
        this.likeRepository = likeRepository;
        this.viewRepository = viewRepository;
    }

    public Post newPost(String title) {
        Post newPost = Post.builder()
                .user(loggedInUser)
                .title(title)
                .date(LocalDateTime.now())
                .build();

        postRepository.saveAndFlush(newPost);

        return newPost;
    }

    public List<Post> getFeed(Long startPostId) {
        List<Post> result;

        if(startPostId == null) {
            result = postRepository.findTop10ByOrderByDateDesc();
        } else {
            result = postRepository.findTop10ByIdLessThanOrderByDateDesc(startPostId);
        }

        markPostsAsViewed(result);

        return result;
    }

    public List<Post> getTopPosts(PostRestController.TimeSpan topListTimeSpan, Long startPostId) {
        LocalDateTime startOfTimeSpan;

        switch(topListTimeSpan) {
            case ALLTIME:
                startOfTimeSpan = null;
                break;
            case DAY:
                startOfTimeSpan = LocalDate.now().atStartOfDay();
                break;
            case WEEK:
                startOfTimeSpan = LocalDate.now().with(DayOfWeek.MONDAY).atStartOfDay();
                break;
            case MONTH:
            default:
                startOfTimeSpan = LocalDate.now().withDayOfMonth(1).atStartOfDay();
                break;
        }

        List<Post> result;

        if(startPostId == null) {
            if (startOfTimeSpan == null) {
                result = postRepository.findTop10ByOrderByLikesDesc();
            } else {
                result = postRepository.findTop10ByDateGreaterThanOrderByLikesDesc(startOfTimeSpan);
            }
        } else {
            if(startOfTimeSpan == null) {
                result = postRepository.findTop10ByIdLessThanOrderByLikesDesc(startPostId);
            } else {
                result = postRepository.findTop10ByDateGreaterThanAndIdLessThanOrderByLikesDesc(startOfTimeSpan, startPostId);
            }
        }

        markPostsAsViewed(result);

        return result;
    }

    public List<Post> getUserWall(Long startPostId) {
        List<Post> result;

        if(startPostId == null) {
            result = postRepository.findTop10ByUserOrderByDateDesc(loggedInUser);
        } else {
            result = postRepository.findTop10ByUserAndIdLessThanOrderByDateDesc(loggedInUser, startPostId);
        }

        markPostsAsViewed(result);

        return result;
    }

    public Like likePost(Long postId) {
        Post post = postRepository.getOne(postId);
        Like like = null;

        if(post != null) {
            like = Like.builder()
                    .post(post)
                    .user(loggedInUser)
                    .build();

            // TODO ignore error if post already liked
            likeRepository.saveAndFlush(like);
        }

        return like;
    }

    public List<Like> unlikePost(long postId) {
        Post post = postRepository.getOne(postId);

        List<Like> postLikes = likeRepository.findByPostAndUser(post, loggedInUser);

        likeRepository.deleteAll(postLikes);

        return postLikes;
    }

    private void markPostsAsViewed(List<Post> viewedPosts) {
        List<View> views = new ArrayList<>();

        for(Post post : viewedPosts) {
            View view = View.builder()
                    .post(post)
                    .user(loggedInUser)
                    .build();
            views.add(view);
        }

        // TODO ignore error if post already viewed
        viewRepository.saveAll(views);
    }

}
