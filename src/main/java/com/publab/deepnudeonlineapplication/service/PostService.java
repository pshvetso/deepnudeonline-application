package com.publab.deepnudeonlineapplication.service;

import com.publab.deepnudeonlineapplication.controller.PostRestController;
import com.publab.deepnudeonlineapplication.dto.PostDetailsDTO;
import com.publab.deepnudeonlineapplication.model.Like;
import com.publab.deepnudeonlineapplication.model.Post;
import com.publab.deepnudeonlineapplication.model.User;
import com.publab.deepnudeonlineapplication.model.View;
import com.publab.deepnudeonlineapplication.repository.LikeRepository;
import com.publab.deepnudeonlineapplication.repository.PostRepository;
import com.publab.deepnudeonlineapplication.repository.ViewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service("postService")
public class PostService {
    private final EntityManager entityManager;
    private final PostRepository postRepository;
    private final LikeRepository likeRepository;
    private final ViewRepository viewRepository;

    // TODO set current user
    private final User loggedInUser = User.builder().id(1L).build();

    @Autowired
    public PostService(EntityManager entityManager, PostRepository postRepository, LikeRepository likeRepository, ViewRepository viewRepository) {
        this.entityManager = entityManager;
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

    public List<PostDetailsDTO> getFeed(Long startPostId) {
        List<PostDetailsDTO> result;

        if(startPostId == null) {
            result = postRepository.getFeed(loggedInUser.getId());
        } else {
            result = postRepository.getFeedLaterThanPostId(startPostId, loggedInUser.getId());
        }

        markPostsAsViewed(result);

        return result;
    }

    public List<PostDetailsDTO> getTopPosts(PostRestController.TimeSpan topListTimeSpan, Long startPostId) {
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

        List<PostDetailsDTO> result;

        if(startPostId == null) {
            if (startOfTimeSpan == null) {
                result = postRepository.findTop10(loggedInUser.getId());
            } else {
                result = postRepository.findTop10InTimeSpan(startOfTimeSpan, loggedInUser.getId());
            }
        } else {
            if(startOfTimeSpan == null) {
                result = postRepository.findTop10ByIdLessThan(startPostId, loggedInUser.getId());
            } else {
                result = postRepository.findTop10InTimeSpanByIdLessThan(startOfTimeSpan, startPostId, loggedInUser.getId());
            }
        }

        markPostsAsViewed(result);

        return result;
    }

    public List<PostDetailsDTO> getUserWall(Long userId, Long startPostId) {
        List<PostDetailsDTO> result;

        if(startPostId == null) {
            result = postRepository.findLatestPostsByUserId(userId, loggedInUser.getId());
        } else {
            result = postRepository.findLatestPostsByUserIdAndLaterThanPostId(userId, startPostId, loggedInUser.getId());
        }

        markPostsAsViewed(result);

        return result;
    }

    public PostDetailsDTO getPost(Long id) {
        return postRepository.getPost(id, loggedInUser.getId());
    }

    @Transactional
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

    @Transactional
    void markPostsAsViewed(List<PostDetailsDTO> viewedPosts) {
        List<View> views = new ArrayList<>();

        for(PostDetailsDTO postDTO : viewedPosts) {
            if(postDTO.getHasBeenViewed() == null) {
                Post post = entityManager.getReference(Post.class, postDTO.getId());

                View view = View.builder()
                        .post(post)
                        .user(loggedInUser)
                        .build();
                views.add(view);
            }
        }

        viewRepository.saveAll(views);
    }

}
