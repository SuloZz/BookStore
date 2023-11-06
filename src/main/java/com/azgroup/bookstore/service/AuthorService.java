package com.azgroup.bookstore.service;


import com.azgroup.bookstore.entity.request.AuthorRequest;
import com.azgroup.bookstore.entity.response.AuthorBooksResponse;
import com.azgroup.bookstore.entity.response.AuthorResponse;
import com.azgroup.bookstore.entity.response.AuthorResponseForAdmin;
import com.azgroup.bookstore.entity.response.ReaderResponse;


import java.util.List;

public interface AuthorService {
   List<AuthorResponseForAdmin> getAllActiveAuthors();
   List<AuthorResponseForAdmin> getAllDeletedAuthors();
   List<AuthorResponse> getAllAuthors();
   void addAuthor(AuthorRequest authorRequest);
   void update(Long id,AuthorRequest authorRequest);
   AuthorResponseForAdmin getById(Long id);
   void delete(Long id);
   List<AuthorBooksResponse> authorsAllBooks(Long authorId);
   List<ReaderResponse> getReadersForBook(Long bookId);

}
