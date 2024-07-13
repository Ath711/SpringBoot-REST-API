package com.example.restfulwebservices.beans;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class UserBeans {
    @Id
    private int id;
    @Size(min = 2, message = "Name must have at least 2 characters")
    @JsonProperty("user_name")
    private String name;
    @JsonProperty("birth_date")
    @Past(message = "Birthdate must be in past")
    private LocalDate birthDate;
    @OneToMany(mappedBy = "userBeans")
    @JsonIgnore
    private List<Post> postList;
}
