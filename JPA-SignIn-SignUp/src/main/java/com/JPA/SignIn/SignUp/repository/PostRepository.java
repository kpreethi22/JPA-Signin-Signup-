package com.JPA.SignIn.SignUp.repository;

import com.JPA.SignIn.SignUp.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post,Long> {
}
