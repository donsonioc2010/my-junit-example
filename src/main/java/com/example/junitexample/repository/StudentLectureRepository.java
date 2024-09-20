package com.example.junitexample.repository;


import com.example.junitexample.domain.entity.StudentLecture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentLectureRepository extends JpaRepository<StudentLecture, Long> {

}
