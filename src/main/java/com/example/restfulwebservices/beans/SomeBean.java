package com.example.restfulwebservices.beans;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
//@JsonIgnoreProperties({"field3","field1"})
@JsonFilter("someBeanFilter")
public class SomeBean {
    private String field1;
    private String field2;
    //    @JsonIgnore
    private String field3;
}

