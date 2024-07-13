package com.example.restfulwebservices.beans;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Post {
    @Id
    private int id;
    private String description;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private UserBeans userBeans;
}
