package com.example.restfulwebservices.repository;

import com.example.restfulwebservices.beans.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post,Integer> {
    Post findFirstByOrderByIdDesc();
}
