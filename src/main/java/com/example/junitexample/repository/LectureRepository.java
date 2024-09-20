package com.example.junitexample.repository;

import com.example.junitexample.domain.entity.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;


public interface LectureRepository extends JpaRepository<Lecture, Long> {

}
