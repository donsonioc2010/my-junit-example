package com.example.junitexample.domain.type;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserType {
    STUDENT(1), PROFESSOR(2);

    private final int code;
}
