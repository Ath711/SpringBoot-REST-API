package com.example.restfulwebservices.controller;

import com.example.restfulwebservices.beans.Post;
import com.example.restfulwebservices.beans.UserBeans;
import com.example.restfulwebservices.dao.UserDaoService;
import com.example.restfulwebservices.exception.PostNotFoundException;
import com.example.restfulwebservices.exception.UserNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class PostController {

    private final UserDaoService userDaoService;

    public PostController(UserDaoService userDaoService) {
        this.userDaoService = userDaoService;
    }

    @GetMapping("/users/{id}/posts")
    public List<Post> retrievePostsForUser(@PathVariable int id) {
        UserBeans userBeans = userDaoService.findOne(id);
        if (userBeans == null)
            throw new UserNotFoundException("Id:" + id);
        return userBeans.getPostList();
    }

    @GetMapping("/users/{userId}/posts/{postId}")
    public Post retrieveSpecificPostsForUser(@PathVariable int userId, @PathVariable int postId) {
        UserBeans userBeans = getUserBeansIfExist(userId);
        checkPostExist(postId, userBeans);
        return userBeans.getPostList().get(postId - 1);
    }

    @PostMapping("/users/{id}/posts")
    public ResponseEntity<Object> createPostsForUser(@PathVariable int id, @RequestBody Post post) {
        UserBeans userBeans = userDaoService.findOne(id);
        if (userBeans == null)
            throw new UserNotFoundException("Id:" + id);
        post.setUserBeans(userBeans);
        Post savedPost = userDaoService.saveUserPost(post);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedPost.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PatchMapping("/users/{userId}/posts/{postId}")
    public void updateSpecificPostsForUser(@PathVariable int userId, @PathVariable int postId, @RequestBody Post post) {
        UserBeans userBeans = getUserBeansIfExist(userId);
        checkPostExist(postId, userBeans);
        post.setUserBeans(userBeans);
        userDaoService.updateUserPost(postId, post);
    }

    @DeleteMapping("/users/{userId}/posts/{postId}")
    public void deletePostsForUser(@PathVariable int userId, @PathVariable int postId) {
        checkPostExist(postId, getUserBeansIfExist(userId));
        userDaoService.deleteUserPost(postId);
    }

    private UserBeans getUserBeansIfExist(int userId) {
        UserBeans userBeans = userDaoService.findOne(userId);
        if (userBeans == null)
            throw new UserNotFoundException("Id:" + userId);
        return userBeans;
    }

    private static void checkPostExist(int postId, UserBeans userBeans) {
        userBeans.getPostList().stream()
                .filter(post -> post.getId() == postId)
                .findFirst()
                .orElseThrow(() -> new PostNotFoundException("PostId: " + postId));
    }
}
