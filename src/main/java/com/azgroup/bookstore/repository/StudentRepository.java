package com.azgroup.bookstore.repository;

import com.azgroup.bookstore.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student,Long> {
    List<Student> findAllByIsDeletedFalse();
    List<Student> findAllByIsDeletedTrue();
}
