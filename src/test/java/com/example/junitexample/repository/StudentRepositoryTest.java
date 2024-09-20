package com.example.junitexample.repository;

import com.example.junitexample.domain.entity.Student;
import com.example.junitexample.domain.type.Department;
import com.example.junitexample.domain.type.GenderType;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import lombok.Getter;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@DisplayName("Student Entity JPA 테스트")
class StudentRepositoryTest {

    @Autowired
    private StudentRepository studentRepository;

    @AfterEach
    void tearDown() {
        studentRepository.deleteAll();
    }

    @Test
    @DisplayName("학생 엔티티를 저장하고 조회할 수 있다.")
    void saveAndFindStudent() {
        // given
        String name = "John Doe";
        LocalDate entranceAt = LocalDate.of(2024, Month.AUGUST, 22);
        Student student = new Student(name, GenderType.MAN, entranceAt, Department.COMPUTER_SCIENCE);

        // when
        Student savedStudent = studentRepository.save(student);

        // then
        Assertions.assertThat(savedStudent.getId())
            .isNotNull();
        Assertions.assertThat(savedStudent)
            .extracting(Student::getName, Student::getGender, Student::getEntranceAt, Student::getDepartment)
            .contains(name, GenderType.MAN, entranceAt, Department.COMPUTER_SCIENCE);
    }

    @Test
    @DisplayName("전체 학생의 조회")
    void saveAndFindAllStudent() {
        // given
        List<StudentTemp> studentsTemp = List.of(
            new StudentTemp("Alice", Department.KOREAN_LANGUAGE),
            new StudentTemp("Back", Department.JAPAN_LANGUAGE),
            new StudentTemp("Crawling", Department.DESIGN_NORMAL),
            new StudentTemp("Jong1", Department.NURSE),
            new StudentTemp("BBH", Department.COMPUTER_SCIENCE)
        );
        List<Student> studentList = studentsTemp.stream().map(
            student -> {
                // 0인 경우 성별 여자케이스로
                if (student.getName().length() % 2 == 0) {
                    return ofStudent(student.getName(), GenderType.WOMAN, student.getDepartment());
                }
                return ofStudent(student.getName(), GenderType.MAN, student.getDepartment());
            }
        ).toList();

        // when
        studentRepository.saveAll(studentList);
        long count = studentRepository.count();
        List<Student> result = studentRepository.findAll();

        // then
        Assertions.assertThat(result)
            .extracting(Student::getName)
            .contains("Alice", "Back", "Crawling", "Jong1", "BBH");
        Assertions.assertThat(count).isEqualTo(5L);
    }

    @Test
    @DisplayName("총 학생 수에 따라 계정 설정을 할 수 있다.")
    void defaultSettingWithStudentCount() {
        // given
        Student student = new Student("Alice", GenderType.WOMAN, LocalDate.of(2023, 9, 1), Department.COMPUTER_SCIENCE);

        // when
        long totalStudentCount = studentRepository.count();
        student.defaultSetting(totalStudentCount);

        // then
        Assertions.assertThat(student.getUserId()).startsWith("202301005-001");
        Assertions.assertThat(student.getPassword()).isNotBlank();
    }

    @Test
    @DisplayName("수강 내역 추가 기능을 테스트한다.")
    void addStudentLecture() {
        //TODO : 수강내역 추가 기능 테스트 추가 필요
        org.junit.jupiter.api.Assertions.assertTrue(true);
    }

    private Student ofStudent(String name, GenderType gender, Department department) {
        LocalDate mockEntranceAt = LocalDate.of(2024, Month.AUGUST, 22);
        return new Student(name, gender, mockEntranceAt, department);
    }

    @Getter
    static class StudentTemp {
        String name;
        Department department;

        StudentTemp(String name, Department department) {
            this.name = name;
            this.department = department;
        }
    }
}