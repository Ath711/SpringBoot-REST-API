package com.example.restfulwebservices.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class ErrorDetails {
    private LocalDateTime timestamp;
    private String message;
    private String details;
}

