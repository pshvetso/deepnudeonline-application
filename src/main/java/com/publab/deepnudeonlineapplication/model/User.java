package com.publab.deepnudeonlineapplication.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Entity
@Table
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private long id;

    @Column(unique = true, length = 16)
    @NotEmpty
    private int hash;

    @Column(length = 16)
    @NotEmpty
    private String username;

    @Column(length = 16)
    @NotEmpty
    private String firstName;

    @Column(length = 16)
    @NotEmpty
    private String lastName;

    @Column
    @NotEmpty
    private byte avatarId;

    @Column
    @NotEmpty
    private LocalDateTime lastVisitDate;
}
