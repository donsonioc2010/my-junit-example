package com.example.junitexample.utils;

import com.example.junitexample.domain.type.Department;
import com.example.junitexample.domain.type.UserType;
import java.time.LocalDate;
import org.springframework.util.ObjectUtils;

public class UserInitUtils {

    public static String ofLoginNumber(UserType userType, Department department, LocalDate startDate) {
        if (ObjectUtils.isEmpty(new Object[] {userType, department, startDate})) {
            throw new NullPointerException("매개변수 중 Null이 존재합니다.");
        }
        return String.format("%d%02d%03d-", startDate.getYear(), userType.getCode(), department.getCode());
    }

    // 최초 패스워드 ㅋㅋㅋ
    public static String defaultPassword() {
        return "qwer1234!!";
    }
}
