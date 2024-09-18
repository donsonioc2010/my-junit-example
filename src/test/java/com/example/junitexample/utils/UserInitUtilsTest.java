package com.example.junitexample.utils;

import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import com.example.junitexample.domain.type.Department;
import com.example.junitexample.domain.type.UserType;
import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;

@Slf4j
class UserInitUtilsTest {

    @Test
    @DisplayName("로그인용 번호를 생성시, UserType이 Null인 경우 NPE가 발생합니다.")
    void ofLoginNumber_whenUserTypeIsNull_thenExceptedNPE() {
        final LocalDate mockStartDate = LocalDate.of(2024, Month.JUNE, 22);

        assertThrowsExactly(
            NullPointerException.class,
            () -> UserInitUtils.ofLoginNumber(null, Department.COMPUTER_SCIENCE, mockStartDate),
            "매개변수 중 Null이 존재합니다.");
    }

    @Test
    @DisplayName("로그인용 번호를 생성시, Department가 Null인 경우 NPE가 발생합니다.")
    void ofLoginNumber_whenDepartmentIsNull_thenExceptedNPE() {
        final LocalDate mockStartDate = LocalDate.of(2024, Month.JUNE, 22);

        assertThrowsExactly(
            NullPointerException.class,
            () -> UserInitUtils.ofLoginNumber(UserType.STUDENT, null, mockStartDate),
            "매개변수 중 Null이 존재합니다.");
    }

    @Test
    @DisplayName("로그인용 번호를 생성시, 근무시작일 또는 입학일인 StartDate가 Null인 경우 NPE가 발생합니다.")
    void ofLoginNumber_whenStartDateIsNull_thenExceptedNPE() {
        assertThrowsExactly(
            NullPointerException.class,
            () -> UserInitUtils.ofLoginNumber(UserType.STUDENT, Department.COMPUTER_SCIENCE, null),
            "매개변수 중 Null이 존재합니다.");
    }

    @ParameterizedTest
    @EnumSource(value = UserType.class, names = {"STUDENT", "PROFESSOR"})
    @DisplayName("로그인용 번호를 생성시, UserType에 따라 교수용, 학생용에 따라 코드값이 구분되도록 생성된다.")
    void ofLoginNumber_whenGivenChangeUserType_thenClassificationUserId(UserType userType) {
        // given
        final LocalDate mockStartDate = LocalDate.of(2024, Month.JUNE, 22);

        // when
        String result =
            UserInitUtils.ofLoginNumber(userType, Department.COMPUTER_SCIENCE, mockStartDate);

        // then
        String expect = String.format("%d%02d", mockStartDate.getYear(), userType.getCode());
        log.info("[{}] UserType에 따른 생성 계정 result >>> {} , expect >>> {}", this.getClass().getName(),
            result, expect);
        Assertions.assertTrue(result.startsWith(expect));
    }

    @ParameterizedTest(name = "UserType >>> {0},  Department >>> {1}, StartDate >>>{2} 일때 기대값 >>>{3}")
    @MethodSource("provideAllUserTypeAndDepartmentCase")
    @DisplayName("로그인용 번호를 생성시, UserType, Department에 따라 코드값이 구분되도록 생성된다.")
    void ofLoginNumber_whenGivenAllUserTypeAndDepartmentType_thenClassificationUserId(
        UserType userType, Department department, LocalDate startDate, String expectlyString
    ) {
        // given

        // when
        String result =
            UserInitUtils.ofLoginNumber(userType, department, startDate);

        // then
        log.info("[{}] UserType에 따른 생성 계정 result >>> {} , expectlyString >>> {}", this.getClass().getName(),
            result, expectlyString);
        Assertions.assertTrue(result.startsWith(expectlyString));
    }

    @Test
    @DisplayName("계정이 생성될때 사용되는 기본 패스워드는 qwer1234!!이다.")
    void defaultPassword_whenCall_thenPasswordIs_qwer1234() {
        Assertions.assertEquals("qwer1234!!", UserInitUtils.defaultPassword());
    }


    /**
     * 사용자 학번이 생성될 수 있는 케이스를 전부 제작한다.
     * @return Stream<Arguments>
     */
    private static Stream<Arguments> provideAllUserTypeAndDepartmentCase() {
        final LocalDate mockStartDate = LocalDate.of(2024, Month.JUNE, 22);
        List<Arguments> exampleCase = Arrays.stream(UserType.values())
            .map(userType ->
                Arrays.stream(Department.values())
                    .map(department ->
                        Arguments.of(
                            userType,
                            department,
                            mockStartDate,
                            String.format(
                                "%d%02d%03d",
                                mockStartDate.getYear(),
                                userType.getCode(),
                                department.getCode()
                            )
                        )
                    )
                    .toList()
            ).flatMap(List::stream)
            .toList();

        return exampleCase.stream();
    }
}