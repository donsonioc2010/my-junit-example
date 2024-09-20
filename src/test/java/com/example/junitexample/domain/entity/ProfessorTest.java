package com.example.junitexample.domain.entity;

import com.example.junitexample.domain.type.Department;
import com.example.junitexample.domain.type.GenderType;
import com.example.junitexample.utils.UserInitUtils;
import java.time.LocalDate;
import java.time.Month;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

@Slf4j
@DisplayName("교수 Entity 테스트")
class ProfessorTest {

    @Nested
    @DisplayName("생성자 테스트")
    class ConstructorTest {
        @Test
        @DisplayName("교수 객체를 생성시, 이름이 Null인 경우, 객체가 생성되지 않는다")
        void studentConstructor_givenNameIsNull_thenThrowsException() {
            // given
            LocalDate mockCommissionDate = LocalDate.of(2024, Month.AUGUST, 22);

            // when && then
            Assertions.assertThatThrownBy(() -> new Professor(null, GenderType.MAN, Department.COMPUTER_SCIENCE, mockCommissionDate))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .isInstanceOf(Exception.class) // 부모타입 체크가 가능함
                .hasMessage("필수 입력해야 하는 값이 입력되지 않았습니다.");
        }

        @ParameterizedTest(name = "부여되는 이름명 >>>[{0}]")
        @ValueSource(strings = {"", " ", "   "})
        @DisplayName("교수 객체를 생성시, 이름이 빈 공백인 경우, 객체가 생성되지 않는다")
        void studentConstructor_givenNameIsBlank_thenThrowsException(String professorName) {
            // given
            LocalDate mockCommissionDate = LocalDate.of(2024, Month.AUGUST, 22);

            // when && then
            Assertions.assertThatThrownBy(() -> new Professor(professorName, GenderType.MAN, Department.COMPUTER_SCIENCE, mockCommissionDate))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .isInstanceOf(Exception.class) // 부모타입 체크가 가능함
                .hasMessage("필수 입력해야 하는 값이 입력되지 않았습니다.");
        }

        @Test
        @DisplayName("교수 객체를 생성시, 성별이 Null인 경우, 객체가 생성되지 않는다")
        void studentConstructor_givenGenderIsNull_thenThrowsException() {
            // given
            LocalDate mockCommissionDate = LocalDate.of(2024, Month.AUGUST, 22);
            String mockProfessorName = "테스트";

            // when && then
            Assertions.assertThatThrownBy(() -> new Professor(mockProfessorName, null, Department.COMPUTER_SCIENCE, mockCommissionDate))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .isInstanceOf(Exception.class) // 부모타입 체크가 가능함
                .hasMessage("필수 입력해야 하는 값이 입력되지 않았습니다.");
        }

        @Test
        @DisplayName("교수 객체를 생성시, 입학일자가 Null인 경우, 객체가 생성되지 않는다")
        void studentConstructor_givenEntranceAtIsNull_thenThrowsException() {
            // given
            LocalDate mockCommissionDate = LocalDate.of(2024, Month.AUGUST, 22);
            String mockProfessorName = "테스트";

            // when && then
            Assertions.assertThatThrownBy(() -> new Professor(mockProfessorName, GenderType.MAN, Department.COMPUTER_SCIENCE, null))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .isInstanceOf(Exception.class) // 부모타입 체크가 가능함
                .hasMessage("필수 입력해야 하는 값이 입력되지 않았습니다.");
        }

        @Test
        @DisplayName("학생객체를 생성시, 과목이 Null인 경우, 객체가 생성되지 않는다")
        void studentConstructor_givenDepartmentAtIsNull_thenThrowsException() {
            // given
            LocalDate mockCommissionDate = LocalDate.of(2024, Month.AUGUST, 22);
            String mockProfessorName = "테스트";

            // when && then
            Assertions.assertThatThrownBy(() -> new Professor(null, GenderType.MAN, Department.COMPUTER_SCIENCE, mockCommissionDate))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .isInstanceOf(Exception.class) // 부모타입 체크가 가능함
                .hasMessage("필수 입력해야 하는 값이 입력되지 않았습니다.");
        }

        @Test
        @DisplayName("모든 값이 존재하는 경우 정상적으로 객체 생성이 가능하다.")
        void studentConstructor_givenAllValueSuccess_thenCreateObject() {
            // given
            LocalDate mockCommissionDate = LocalDate.of(2024, Month.AUGUST, 22);
            String mockProfessorName = "테스트";

            // when
            Professor professor = new Professor(mockProfessorName, GenderType.MAN, Department.COMPUTER_SCIENCE, mockCommissionDate);

            // then
            Assertions.assertThat(professor)
                .extracting(
                    Professor::getName,
                    Professor::getGender,
                    Professor::getDepartment,
                    Professor::getCommissionDate
                )
                .contains(
                    mockProfessorName,
                    GenderType.MAN,
                    Department.COMPUTER_SCIENCE,
                    mockCommissionDate
                );
        }
    }

    @Nested
    @DisplayName("최초 계정설정 테스트")
    class defaultSettingTest {

        private Professor mockProfessor;

        @BeforeEach
        void setUp() {
            String mockProfessorName = "테스트";
            GenderType mockGender = GenderType.MAN;
            LocalDate mockCommissionDate = LocalDate.of(2024, Month.AUGUST, 22);
            Department mockDepartment = Department.COMPUTER_SCIENCE;

            this.mockProfessor = new Professor(
                mockProfessorName,
                mockGender,
                mockDepartment,
                mockCommissionDate
            );
        }

        @Test
        @DisplayName("계정의 ID 및 패스워드를 최초 설정할 때, 총 학생수가 0명 이하로 파라미터 제공시, Exception이 발생한다.")
        void defaultSetting_givenTotalCountUnderZero_thenThrowsIllegalArgumentException() {
            // given
            int totalCount = -1;

            // when && then
            Assertions.assertThatThrownBy(() -> mockProfessor.defaultSetting(totalCount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("최소값이 0이하는 발생할 수 없습니다.");
        }

        @ParameterizedTest(name = "Count >>> {0}, 기대 ID값 >>> {1}")
        @CsvSource(value = {"0:202402005-001", "1:202402005-002", "2:202402005-003", "3:202402005-004"}, delimiter = ':')
        @DisplayName("계정의 ID 및 패스워드를 최초 설정할 때, 총 학생수가 0명 이상인 경우, 아이디는 카운트로, 패스워드는 초기값으로 생성된다.")
        void defaultSetting_givenTotalCountUpZero_thenThrowsIllegalArgumentException(
            int totalStudentCount, String expectedId
        ) {
            // given

            // when
            mockProfessor.defaultSetting(totalStudentCount);

            //then
            Assertions.assertThat(mockProfessor)
                .isNotNull()
                .extracting("userId", "password")
                .contains(expectedId, UserInitUtils.defaultPassword());
        }
    }

}