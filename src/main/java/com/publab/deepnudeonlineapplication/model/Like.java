package com.publab.deepnudeonlineapplication.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "tbl_like")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;
}
