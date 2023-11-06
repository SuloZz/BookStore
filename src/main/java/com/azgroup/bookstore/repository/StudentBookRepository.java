package com.azgroup.bookstore.repository;

import com.azgroup.bookstore.entity.Book;
import com.azgroup.bookstore.entity.StudentBook;
import com.azgroup.bookstore.entity.StudentBookId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface StudentBookRepository extends JpaRepository<StudentBook, StudentBookId> {
    List<StudentBook> findByBook(Book book);
}