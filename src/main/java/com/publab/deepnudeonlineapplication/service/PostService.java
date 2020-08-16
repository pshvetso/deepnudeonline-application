package com.publab.deepnudeonlineapplication.service;

import com.publab.deepnudeonlineapplication.controller.PostRestController;
import com.publab.deepnudeonlineapplication.dto.PostDetailsDTO_;
import com.publab.deepnudeonlineapplication.dto.PostDetailsDto;
import com.publab.deepnudeonlineapplication.model.Like;
import com.publab.deepnudeonlineapplication.model.Post;
import com.publab.deepnudeonlineapplication.model.User;
import com.publab.deepnudeonlineapplication.model.View;
import com.publab.deepnudeonlineapplication.repository.LikeRepository;
import com.publab.deepnudeonlineapplication.repository.PostDetailsRepository;
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
    private final PostDetailsRepository postDetailsRepository;
    private final LikeRepository likeRepository;
    private final ViewRepository viewRepository;

    // TODO set current user
    private final User loggedInUser = User.builder().id(1L).build();

    @Autowired
    public PostService(EntityManager entityManager, PostRepository postRepository, PostDetailsRepository postDetailsRepository,
                       LikeRepository likeRepository, ViewRepository viewRepository) {
        this.entityManager = entityManager;
        this.postRepository = postRepository;
        this.postDetailsRepository = postDetailsRepository;
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

    public List<PostDetailsDto> getFeed(Integer pageNum) {
        List<PostDetailsDto> result;
        result = postDetailsRepository.getFeedLaterThanPostId(pageNum, loggedInUser.getId());
        markPostsAsViewed(result);
        return result;
    }

    public List<PostDetailsDto> getTopPosts(PostRestController.TimeSpan topListTimeSpan, Integer pageNum) {
        LocalDateTime startOfTimeSpan;

        switch (topListTimeSpan) {
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

        List<PostDetailsDto> result = postDetailsRepository.findTop10InTimeSpan(startOfTimeSpan, pageNum, loggedInUser.getId());
        markPostsAsViewed(result);
        return result;
    }

    public List<PostDetailsDto> getUserWall(Long userId, Integer pageNum) {
        List<PostDetailsDto> result = postDetailsRepository.getUserWall(userId, pageNum, loggedInUser.getId());
        markPostsAsViewed(result);
        return result;
    }

    public PostDetailsDTO_ getPost(Long id) {
        return postRepository.getPost(id, loggedInUser.getId());
    }

    @Transactional
    public void likePost(Long id) {
        Post post = postRepository.getOne(id);
        Like like = null;

        if (post != null) {
            like = Like.builder()
                    .post(post)
                    .user(loggedInUser)
                    .build();

            // TODO ignore error if post already liked
            likeRepository.saveAndFlush(like);
        }
    }

    public void dislikePost(long id) {
        Post post = postRepository.getOne(id);
        List<Like> postLikes = likeRepository.findByPostAndUser(post, loggedInUser);
        likeRepository.deleteAll(postLikes);
    }

    /*@Transactional
    void markPostsAsViewed_(List<PostDetailsDTO_> viewedPosts) {
        List<View> views = new ArrayList<>();

        for (PostDetailsDTO_ postDTO : viewedPosts) {
            if (postDTO.getHasBeenViewed() == null) {
                Post post = entityManager.getReference(Post.class, postDTO.getId());

                View view = View.builder()
                        .post(post)
                        .user(loggedInUser)
                        .build();
                views.add(view);
            }
        }

        viewRepository.saveAll(views);
    }*/

    @Transactional
    void markPostsAsViewed(List<PostDetailsDto> viewedPosts) {
        List<View> views = new ArrayList<>();

        for (PostDetailsDto postDto : viewedPosts) {
            if (postDto.getHasBeenViewed() == null) {
                Post post = entityManager.getReference(Post.class, postDto.getId());

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
