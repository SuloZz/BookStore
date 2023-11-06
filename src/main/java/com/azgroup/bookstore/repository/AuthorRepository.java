package com.azgroup.bookstore.repository;

import com.azgroup.bookstore.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author,Long> {
    List<Author> findAllByIsDeletedFalse();
    List<Author> findAllByIsDeletedTrue();
}

