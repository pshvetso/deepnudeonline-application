package com.publab.deepnudeonlineapplication.model;

import lombok.*;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Past;
import java.time.LocalDateTime;

@Entity
@Table
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column
    @Past
    private LocalDateTime date;

    @Column(length = 255)
    @NotEmpty
    private String title;

    @Column
    @Formula(value="(SELECT COUNT(*) FROM view v WHERE v.post_id = this.id)")
    private int views;

    @Column
    @Formula(value="(SELECT COUNT(*) FROM like l WHERE l.post_id = this.id)")
    private int likes;
}
