package com.example.junitexample.domain.entity;

import com.example.junitexample.domain.BaseEntity;
import com.example.junitexample.domain.type.Week;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.DayOfWeek;
import java.time.LocalTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;

@Entity
@Getter
@Comment("강좌 일정")
@Table(name = "TBL_LECTURE_TIME")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LectureTime extends BaseEntity {
    @Id
    @Comment("ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("강의 정보")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "LECTURE_ID", insertable = false, updatable = false)
    private Lecture lecture;

    @Setter
    @Comment("강의 요일")
    private Week week;

    @Setter
    @Comment("강의 시작 시간")
    private LocalTime startTime;

    @Setter
    @Comment("강의 종료 시간")
    private LocalTime endTime;

    @Setter
    @Comment("강의 장소")
    private String lecturePlace;

    /* ======== 생성자 ======== */
    public LectureTime(
        @NotNull Lecture lecture,
        @NotNull Week week,
        @NotNull LocalTime startTime,
        @NotNull LocalTime endTime,
        @NotBlank String lecturePlace
    ) {
        this.lecture = lecture;
        this.week = week;
        this.startTime = startTime;
        this.endTime = endTime;
        this.lecturePlace = lecturePlace;
    }


}
