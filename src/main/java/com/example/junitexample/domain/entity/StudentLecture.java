package com.example.junitexample.domain.entity;

import com.example.junitexample.domain.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Entity
@Getter
@Comment("수강신청 목록")
@Table(name = "TBL_STUDENT_LECTURE")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StudentLecture extends BaseEntity {

    @Id
    @Comment("ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STUDENT_ID")
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LECTURE_ID")
    private Lecture lecture;

    public StudentLecture(Student student, Lecture lecture) {
        this.student = student;
        this.lecture = lecture;
    }
}
