package com.example.junitexample.domain.entity;

import com.example.junitexample.domain.BaseEntity;
import com.example.junitexample.domain.type.Department;
import com.example.junitexample.domain.type.GenderType;
import jakarta.persistence.CascadeType;
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

@Entity
@Getter
@Comment("교수, 강사")
@Table(name = "TBL_PROFESSOR")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Professor extends BaseEntity {
    @Id
    @Comment("교수ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("교수 번호(로그인ID)")
    private String userId;

    @Comment("패스워드")
    private String password;

    @Comment("근무 시작일")
    private LocalDate commissionDate;

    @Comment("이름")
    private String name;

    @Comment("성별")
    private GenderType gender;

    @Comment("담당 전공 과목")
    public Department department;



    // 강좌의 부모이기 떄문에 수정 허용한다. 고아객체 삭제는 하지 않음.
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = false)
    private List<Lecture> lectures = new ArrayList<>();


    /* ======== 생성자 ======== */

    /* ======== 비즈니스로직 ======== */

}
