package com.azgroup.bookstore.service;

import com.azgroup.bookstore.entity.request.StudentRequest;
import com.azgroup.bookstore.entity.request.StudentBookDto;

import com.azgroup.bookstore.entity.response.StudentReadsBook;
import com.azgroup.bookstore.entity.response.StudentResponse;

import java.util.List;

public interface StudentService {
    List<StudentResponse> getAllActiveStudent();
    List<StudentResponse> getAllDeletedStudent();
    void add(StudentRequest studentRequest);
    void update(Long id,StudentRequest studentRequest);
    StudentResponse getById(Long id);
    void delete(Long id);
    void startReadBook(StudentBookDto studentBookDto,Long Id);
    StudentReadsBook getStudentReadBooks(Long studentId);
    void subscribeToAuthor(Long studentId, Long authorId);


}
