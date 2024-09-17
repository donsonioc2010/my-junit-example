package com.example.junitexample.domain.entity;

import com.example.junitexample.domain.BaseEntity;
import com.example.junitexample.domain.type.Department;
import com.example.junitexample.domain.type.GenderType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

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
    public Department department;

    @Comment("입학 일자")
    private LocalDate entranceAt;

    @Comment("수강 내역")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "student")
    private List<StudentLecture> studentLectures = new ArrayList<>();

    /* ======== 생성자 ======== */
    public Student(
        @NotBlank String name,
        @NotNull GenderType gender,
        @NotNull LocalDate entranceAt
    ) {
        this.name = name;
        this.gender = gender;
        this.entranceAt = entranceAt;
    }

    /* ======== 비즈니스로직 ======== */
    public void addStudentLecture(Lecture lecture) {
        StudentLecture studentLecture = new StudentLecture(this, lecture);
        this.studentLectures.add(studentLecture);
    }
    // TODO : 수강신청 취소(삭제)는 안만듬
}
