package com.publab.deepnudeonlineapplication.repository.impl;

import com.publab.deepnudeonlineapplication.dto.PostDetailsDto;
import com.publab.deepnudeonlineapplication.model.Like;
import com.publab.deepnudeonlineapplication.model.Post;
import com.publab.deepnudeonlineapplication.model.User;
import com.publab.deepnudeonlineapplication.model.View;
import com.publab.deepnudeonlineapplication.repository.PostDetailsRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PostDetailsRepositoryImpl implements PostDetailsRepository {
    private final int pageSize = 10;
    private CriteriaBuilder cb;


    private final EntityManager em;

    public PostDetailsRepositoryImpl(EntityManager em) {
        this.em = em;
        this.cb = em.getCriteriaBuilder();
    }

    /*
    "SELECT " +
        "p.post_id AS id, " +
        "p.date AS date, " +
        "p.title AS title, " +
        "u.user_id AS userId, " +
        "u.username AS username, " +
        "u.first_name AS firstName, " +
        "u.last_name AS lastName, " +
        "u.avatar_id AS userAvatarId, " +
        "l.like_id AS hasBeenLiked, " +
        "v.view_id as hasBeenViewed, " +
        "(SELECT COUNT(*) FROM view v WHERE v.post_id = p.post_id) AS views, " +
        "(SELECT COUNT(*) FROM tbl_like l WHERE l.post_id = p.post_id) AS likes " +
    "FROM post p " +
    "JOIN user u " +
        "ON p.user_id = u.user_id " +
    "LEFT JOIN tbl_like l " +
        "ON p.post_id = l.post_id AND l.user_id = :currentUserId " +
    "LEFT JOIN view v " +
        "ON p.post_id = v.post_id AND v.user_id = :currentUserId " +
    "WHERE p.date <= (SELECT date FROM post WHERE post_id = :startPostId) " +
        "AND p.post_id != :startPostId " +
        "ORDER BY p.date DESC " +
    "LIMIT 10;"
     */
    @Override
    public List<PostDetailsDto> getFeedLaterThanPostId(Long startPostId, Long currentUserId) {
        CriteriaQuery<PostDetailsDto> cq = cb.createQuery(PostDetailsDto.class);
        Root<Post> root = cq.from(Post.class);
        constructQuery(cq, root, currentUserId);

        List<Predicate> predicates = new ArrayList<>();
        if(startPostId != null) {
            predicates.add( cb.lessThan(root.get("id"), startPostId) );
        }
        cq.where(predicates.toArray(new Predicate[0]));

        Order order = cb.desc(root.get("date"));
        TypedQuery<PostDetailsDto> query = createTypedQuery(cq, order);
        return query.getResultList();
    }

    /*
    "SELECT " +
            "p.post_id AS id, " +
            "p.date AS date, " +
            "p.title AS title, " +
            "u.user_id AS userId, " +
            "u.username AS username, " +
            "u.first_name AS firstName, " +
            "u.last_name AS lastName, " +
            "u.avatar_id AS userAvatarId, " +
            "l.like_id AS hasBeenLiked, " +
            "v.view_id as hasBeenViewed, " +
            "(SELECT COUNT(*) FROM view v WHERE v.post_id = p.post_id) AS views, " +
            "(SELECT COUNT(*) FROM tbl_like l WHERE l.post_id = p.post_id) AS likes " +
    "FROM post p " +
    "JOIN user u " +
            "ON p.user_id = u.user_id " +
    "LEFT JOIN tbl_like l " +
            "ON p.post_id = l.post_id AND l.user_id = :currentUserId " +
    "LEFT JOIN view v " +
            "ON p.post_id = v.post_id AND v.user_id = :currentUserId " +
    "WHERE p.date > :startOfTimeSpan " +
    "ORDER BY likes DESC " +
    "LIMIT :pageNum, 10;"
     */

    private String buildQueryTop10InTimeSpan(LocalDateTime startOfTimeSpan) {
        StringBuilder query = new StringBuilder(
            "SELECT new com.publab.deepnudeonlineapplication.dto.PostDetailsDto" +
                    "(p.id, p.date, p.title, u.id, u.username, u.firstName, u.lastName, u.avatarId, " +
                    "(SELECT l.id FROM Like l WHERE (l.post.id = p.id) AND (l.user.id = :currentUserId)), " +
                    "(SELECT v.id FROM View v WHERE (v.post.id = p.id) AND (v.user.id = :currentUserId)), " +
                    "(SELECT count(v1) FROM View v1 WHERE v1.post.id = p.id) AS views, " +
                    "(SELECT count(l1) FROM Like l1 WHERE l1.post.id = p.id) AS likes) " +
                    "FROM Post p " +
                    "JOIN User u ON p.user = u"
        );

        if(startOfTimeSpan != null) {
            query.append(" WHERE p.date > :startOfTimeSpan");
        }

        query.append(" ORDER BY likes");

        return query.toString();
    }

    /*@Override
    public List<PostDetailsDto> findTop10InTimeSpan(LocalDateTime startOfTimeSpan, Integer pageNum, Long currentUserId) {
        CriteriaQuery<PostDetailsDto> cq = cb.createQuery(PostDetailsDto.class);
        Root<Post> root = cq.from(Post.class);
        constructQuery(cq, root, currentUserId);

        List<Predicate> predicates = new ArrayList<>();
        if(startOfTimeSpan != null) {
            predicates.add( cb.greaterThan(root.get("date"), startOfTimeSpan) );
        }
        cq.where(predicates.toArray(new Predicate[0]));

        Order order = cb.desc(root.get("date"));
        TypedQuery<PostDetailsDto> query = createTypedQuery(cq, order);
        query.setFirstResult(pageNum * pageSize);
        return query.getResultList();
    }*/

    @Override
    public List<PostDetailsDto> findTop10InTimeSpan(LocalDateTime startOfTimeSpan, Integer pageNum, Long currentUserId) {
        String queryString = buildQueryTop10InTimeSpan(startOfTimeSpan);
        TypedQuery<PostDetailsDto> query = em.createQuery(queryString, PostDetailsDto.class);

        if(startOfTimeSpan != null) {
            query.setParameter("startOfTimeSpan", startOfTimeSpan);
        }

        return query.setParameter("currentUserId", currentUserId)
                .setFirstResult(pageNum * pageSize)
                .setMaxResults(pageSize)
                .getResultList();
    }

    private void constructQuery(CriteriaQuery<PostDetailsDto> cq, Root<Post> root, Long currentUserId) {
        Join<Post, User> userJoin = root.join("user");

        Join<Post, Like> likeJoin = root.join("likes", JoinType.LEFT);
        likeJoin.on( cb.equal(likeJoin.get("user"), currentUserId) );
        Join<Post, View> viewJoin = root.join("views", JoinType.LEFT);
        viewJoin.on( cb.equal(viewJoin.get("user"), currentUserId) );

        Subquery<Long> viewsSubquery = cq.subquery(Long.class);
        Root<View> viewsSubqueryRoot = viewsSubquery.from(View.class);
        viewsSubquery.select( cb.count(viewsSubqueryRoot) )
                .where( cb.equal(viewsSubqueryRoot.get("post"), root.get("id")) )
                .alias("views");

        Subquery<Long> likesSubquery = cq.subquery(Long.class);
        Root<Like> likesSubqueryRoot = likesSubquery.from(Like.class);
        likesSubquery.select( cb.count(likesSubqueryRoot) )
                .where( cb.equal(likesSubqueryRoot.get("post"), root.get("id")) )
                .alias("likes");

        cq.select( cb.construct(PostDetailsDto.class,
                root.get("id"),
                root.get("date"),
                root.get("title"),
                userJoin.get("id"),
                userJoin.get("username"),
                userJoin.get("firstName"),
                userJoin.get("lastName"),
                userJoin.get("avatarId"),
                likeJoin.get("id"),
                viewJoin.get("id"),
                viewsSubquery,
                likesSubquery) );
    }

    private TypedQuery<PostDetailsDto> createTypedQuery(CriteriaQuery<PostDetailsDto> cq, Order order) {
        cq.orderBy(order);
        TypedQuery<PostDetailsDto> query = em.createQuery(cq);
        query.setMaxResults(pageSize);
        return query;
    }
}
