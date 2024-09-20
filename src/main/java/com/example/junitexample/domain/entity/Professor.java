package com.example.junitexample.domain.entity;

import com.example.junitexample.domain.BaseEntity;
import com.example.junitexample.domain.type.Department;
import com.example.junitexample.domain.type.GenderType;
import com.example.junitexample.domain.type.UserType;
import com.example.junitexample.utils.UserInitUtils;
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
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

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

    @Comment("이름")
    private String name;

    @Comment("성별")
    private GenderType gender;

    @Comment("담당 전공 과목")
    private Department department;

    @Comment("근무 시작일")
    private LocalDate commissionDate;

    // 강좌의 부모이기 떄문에 수정 허용한다. 고아객체 삭제는 하지 않음.
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = false)
    private List<Lecture> lectures = new ArrayList<>();

    /* ======== 생성자 ======== */

    public Professor(
        String name,
        GenderType gender,
        Department department,
        LocalDate commissionDate
    ) {
        this.name = name;
        this.gender = gender;
        this.department = department;
        this.commissionDate = commissionDate;
        validate();
    }

    /* ======== 비즈니스로직 ======== */
    // 최초 회원가입시 계정 설정
    public void defaultSetting(int totalProfessorCount) {
        if (totalProfessorCount < 0) {
            throw new IllegalArgumentException("최소값이 0이하는 발생할 수 없습니다.");
        }

        // 쿼리질의 결과가 0부터 오기 때문에, 1부터 시작하기 위한 카운트증가
        totalProfessorCount++;

        // 202402005-001, 002 등으로 변환이 됨
        this.userId = String.format("%s%03d", UserInitUtils.ofLoginNumber(
            UserType.PROFESSOR,
            this.department,
            this.commissionDate
        ), totalProfessorCount);

        this.password = UserInitUtils.defaultPassword();
    }

    // 생성자 값 검증
    private void validate() {
        if (!StringUtils.hasText(this.name) ||
            ObjectUtils.isEmpty(this.gender) ||
            ObjectUtils.isEmpty(this.department) ||
            ObjectUtils.isEmpty(this.commissionDate)
        ) {
            throw new IllegalArgumentException("필수 입력해야 하는 값이 입력되지 않았습니다.");
        }
    }
}
