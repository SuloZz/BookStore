package com.azgroup.bookstore.service;

import com.azgroup.bookstore.entity.request.AuthorRequest;
import com.azgroup.bookstore.entity.request.BookRequest;
import com.azgroup.bookstore.entity.response.BookResponse;

import java.util.List;

public interface BookService {
    List<BookResponse> getAllActiveBook();
    List<BookResponse> getAllDeletedBook();
    void addBook(BookRequest bookRequest);
    void updateBook(Long id, BookRequest bookRequest);
    BookResponse getById(Long id);
    void delete(Long id);
}
