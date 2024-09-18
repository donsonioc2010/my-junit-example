package com.example.junitexample.utils;

import com.example.junitexample.domain.type.Department;
import com.example.junitexample.domain.type.UserType;
import java.time.LocalDate;
import org.springframework.util.ObjectUtils;

public class UserInitUtils {

    // [TODO] 학생 숫자인 001등을 붙이는 부분은 각각의 비즈니스 로직에서 실행한다.
    public static String ofLoginNumber(
        UserType userType, Department department, LocalDate startDate
    ) {
        if ( ObjectUtils.isEmpty(userType) || ObjectUtils.isEmpty(department) || ObjectUtils.isEmpty(startDate)) {
            throw new NullPointerException("매개변수 중 Null이 존재합니다.");
        }
        return String.format("%d%02d%03d-", startDate.getYear(), userType.getCode(), department.getCode());
    }

    // 최초 패스워드 ㅋㅋㅋ
    public static String defaultPassword() {
        return "qwer1234!!";
    }
}
