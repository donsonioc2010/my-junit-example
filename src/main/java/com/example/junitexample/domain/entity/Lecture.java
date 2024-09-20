package com.example.junitexample.domain.entity;

import com.example.junitexample.domain.type.Week;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalTime;
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
@Comment("강좌")
@Table(name = "TBL_LECTURE")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Lecture {

    @Id
    @Comment("강좌ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("강좌 명")
    private String lectureName;

    // 자식에서 수정허용 안함
    @Comment("담당 교수명")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "PROFESSOR_ID", insertable = false, updatable = false)
    private Professor professor;

    // 고아객체가 되는 경우 삭제처리 및 강좌가 부모객체기에 수정이 발생하면 적용함.
    @Comment("강좌 일정")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "lecture", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LectureTime> lectureTimes = new ArrayList<>();

    // 여기에서 수강생을 관리하지는 않고, 무조건 조회만 한다.
    @Comment("수강생")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "lecture", orphanRemoval = false)
    private List<StudentLecture> studentLectures = new ArrayList<>();


    /* ======== 생성자 ======== */
    public Lecture(String lectureName, Professor professor) {
        this.lectureName = lectureName;
        this.professor = professor;
        validate();
    }

    /* ======== 비즈니스로직 ======== */
    // 강좌 일정 추가
    public void addLectureTime(
        @NotBlank Week week,
        @NotBlank LocalTime startTime,
        @NotBlank LocalTime endTime,
        @NotBlank String lecturePlace
    ) {
        // 일단 동일 요일 존재시 시간 업데이트, 없으면 삽입
        this.lectureTimes.stream().filter(lecture -> lecture.getWeek().equals(week))
            .findFirst()
            .ifPresentOrElse(
                lecture -> {
                    lecture.setStartTime(startTime);
                    lecture.setEndTime(endTime);
                    lecture.setLecturePlace(lecturePlace);
                },
                () -> this.lectureTimes.add(
                    new LectureTime(
                        this,
                        week,
                        startTime,
                        endTime,
                        lecturePlace
                    )
                )
            );
    }

    // 강좌 일정 삭제
    public void removeLectureTime(@NotNull LectureTime lectureTime) {
        if (lectureTime.getId() == null) {
            throw new RuntimeException("저장 후 수정가능합니다.");
        }
        this.lectureTimes
            .stream()
            .filter(lecture -> lectureTime.getId().equals(lecture.getId()))
            .findFirst()
            .ifPresent(lecture -> this.lectureTimes.remove(lecture));
    }

    private void validate() {
        if (!StringUtils.hasText(this.lectureName) || ObjectUtils.isEmpty(this.professor)) {
            throw new IllegalArgumentException("강좌명과 담임교수는 필수입니다.");
        }
    }
}
