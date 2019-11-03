package com.publab.deepnudeonlineapplication.repository;

import com.publab.deepnudeonlineapplication.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
