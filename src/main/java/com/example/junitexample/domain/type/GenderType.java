package com.example.junitexample.domain.type;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GenderType {
    MAN("남자"),WOMAN("여자");

    private final String description;
}
