package com.example.junitexample.domain.entity;

import com.example.junitexample.domain.BaseEntity;
import com.example.junitexample.domain.type.Department;
import com.example.junitexample.domain.type.GenderType;
import com.example.junitexample.domain.type.UserType;
import com.example.junitexample.utils.UserInitUtils;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

@Entity
@Getter
@Comment("학생")
@Table(name = "TBL_STUDENT")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Student extends BaseEntity {

    @Id
    @Comment("학번")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("로그인용 학생 번호")
    private String userId;

    @Comment("패스워드")
    private String password;

    @Comment("이름")
    private String name;

    @Comment("성별")
    private GenderType gender;

    @Comment("과목")
    private Department department;

    @Comment("입학 일자")
    private LocalDate entranceAt;

    @Comment("수강 내역")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "student")
    private List<StudentLecture> studentLectures = new ArrayList<>();

    /* ======== 생성자 ======== */
    public Student(
        String name,
        GenderType gender,
        LocalDate entranceAt,
        Department department
    ) {
        this.name = name;
        this.gender = gender;
        this.entranceAt = entranceAt;
        this.department = department;
        validate();
    }

    /* ======== 비즈니스로직 ======== */
    // 최초 회원가입시 계정 설정
    public void defaultSetting(int totalStudentCount) {
        if (totalStudentCount < 0) {
            throw new IllegalArgumentException("최소값이 0이하는 발생할 수 없습니다.");
        }

        // 쿼리질의 결과가 0부터 오기 때문에, 1부터 시작하기 위한 카운트증가
        totalStudentCount++;

        // 202401005-001, 002 등으로 변환이 됨
        this.userId = String.format("%s%03d", UserInitUtils.ofLoginNumber(
            UserType.STUDENT,
            this.department,
            this.entranceAt
        ), totalStudentCount);

        this.password = UserInitUtils.defaultPassword();
    }


    public void addStudentLecture(Lecture lecture) {
        StudentLecture studentLecture = new StudentLecture(this, lecture);
        this.studentLectures.add(studentLecture);
    }
    // TODO : 수강신청 취소(삭제)는 안만듬

    // 생성자 값 검증
    private void validate() {
        if (!StringUtils.hasText(this.name) ||
            ObjectUtils.isEmpty(this.gender) ||
            ObjectUtils.isEmpty(this.entranceAt) ||
            ObjectUtils.isEmpty(this.department)
        ) {
            throw new IllegalArgumentException("필수 입력해야 하는 값이 입력되지 않았습니다.");
        }
    }
}
