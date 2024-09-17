package com.example.junitexample.domain.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

// 학과정보
@Getter
@AllArgsConstructor
public enum Department {
    KOREAN_LANGUAGE(1, "국어 국문과"),
    JAPAN_LANGUAGE(2, "일어 문학과"),
    DESIGN_NORMAL(3, "디자인 일반"),
    NURSE(4, "간호학과"),
    COMPUTER_SCIENCE(5, "컴퓨터 과학과");

    private final int code;
    private final String desc;
}
