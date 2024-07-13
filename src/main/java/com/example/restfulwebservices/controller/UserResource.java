package com.example.restfulwebservices.controller;

import com.example.restfulwebservices.beans.UserBeans;
import com.example.restfulwebservices.dao.UserDaoService;
import com.example.restfulwebservices.exception.UserNotFoundException;
import jakarta.validation.Valid;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collections;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class UserResource {
    private UserDaoService userDaoService;

    public UserResource(UserDaoService userDaoService) {
        this.userDaoService = userDaoService;
    }

    //Get /users
    @GetMapping("/users")
    public List<UserBeans> retrieveAllUsers() {
        try {
            return userDaoService.findAll();
        } catch (Exception e) {
            System.out.println(e);
            return Collections.emptyList();
        }
    }

    // add link to http://http://localhost:8080/users using hateoas
    // hateoas concepts EntityModel and WebMvcLinkBuilder
    @GetMapping("/users/{id}")
    public EntityModel<UserBeans> retrieveSpecificUsers(@PathVariable int id) {
        UserBeans userBeans = userDaoService.findOne(id);
        if(userBeans ==null)
            throw new UserNotFoundException("Id:"+id);
        EntityModel<UserBeans> entityModel = EntityModel.of(userBeans);
        WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).retrieveAllUsers());
        entityModel.add(link.withRel("all-users"));
        return entityModel;
    }

    @PostMapping("/users")
    public ResponseEntity<Object> saveNewUser(@Valid @RequestBody UserBeans userBeans) {
        UserBeans savedUserBeans = null;
        try {
            savedUserBeans = userDaoService.save(userBeans);
        } catch (Exception e) {
            System.out.println(e);
        }
        // /users/4 => /users/{id} , user.getId();
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUserBeans.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/users/{id}")
    public void deleteSpecificUser(@PathVariable int id) {
        userDaoService.deleteById(id);
    }
}

