package com.azgroup.bookstore.repository;

import com.azgroup.bookstore.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book,Long > {
    List<Book> findAllByIsDeletedFalse();
    List<Book> findAllByIsDeletedTrue();
}
