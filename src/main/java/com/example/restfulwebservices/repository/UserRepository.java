package com.example.restfulwebservices.repository;

import com.example.restfulwebservices.beans.UserBeans;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserBeans,InternalError> {
    UserBeans findById(int id);

    void deleteById(int id);

    UserBeans findFirstByOrderByIdDesc();
}
