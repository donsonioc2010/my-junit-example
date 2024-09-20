package com.example.junitexample.repository;


import com.example.junitexample.domain.entity.LectureTime;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LectureTimeRepository extends JpaRepository<LectureTime, Long> {

}
