package com.example.restfulwebservices.dao;

import com.example.restfulwebservices.beans.Post;
import com.example.restfulwebservices.beans.UserBeans;
import com.example.restfulwebservices.exception.PostNotFoundException;
import com.example.restfulwebservices.repository.PostRepository;
import com.example.restfulwebservices.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDaoService {

    //    private static List<UserBeans> userBeans = new ArrayList<>();

    //    static {
//        userBeans.add(
//                new UserBeans(1, "A", LocalDate.now().minusYears(30))
//        );
//        userBeans.add(
//                new UserBeans(2, "B", LocalDate.now().minusYears(27))
//        );
//        userBeans.add(
//                new UserBeans(3, "C", LocalDate.now().minusYears(20))
//        );
//    }

    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public UserDaoService(UserRepository userRepository, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    public List<UserBeans> findAll() {
        return userRepository.findAll();
    }

    public UserBeans save(UserBeans userBeans) {
        int lastId = 0;
        if (userRepository.findFirstByOrderByIdDesc() != null)
            lastId = userRepository.findFirstByOrderByIdDesc().getId();
        userBeans.setId(lastId + 1);
        userRepository.save(userBeans);
        return userRepository.findFirstByOrderByIdDesc();
    }

    public UserBeans findOne(int id) {
        return userRepository.findById(id);
    }

    public void deleteById(int id) {
        userRepository.deleteById(id);
    }

    public Post saveUserPost(Post post){
        int lastId = 0;
        if (postRepository.findFirstByOrderByIdDesc() != null)
            lastId = postRepository.findFirstByOrderByIdDesc().getId();
        post.setId(lastId + 1);
        postRepository.save(post);
        return postRepository.findFirstByOrderByIdDesc();
    }

    public void deleteUserPost(int id){
        postRepository.deleteById(id);
    }

    public void updateUserPost(int id, Post post){
        Post existingPost = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException("PostId: " + id));
       existingPost.setDescription(post.getDescription());
       postRepository.save(existingPost);
    }
}
