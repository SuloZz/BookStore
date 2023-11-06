package com.azgroup.bookstore.service.impl;

import com.azgroup.bookstore.entity.Book;
import com.azgroup.bookstore.repository.BookRepository;
import com.azgroup.bookstore.service.AccessControlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service

public class AccessControlServiceImpl implements AccessControlService {
    @Autowired
    private BookRepository bookRepository;




    @Override
    public boolean hasAccessAuthor(Authentication authentication, Long authorId) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Long userAuthorId = userDetails.getId();
        return userAuthorId.equals(authorId);
    }

    @Override
    public boolean hasAccessForBookId(Authentication authentication, Long bookId) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Long userAuthorId = userDetails.getId();
        Optional<Book> bookOptional = bookRepository.findById(bookId);
        if (bookOptional.isPresent()) {
            Book book = bookOptional.get();
            Long bookAuthorId = book.getAuthor().getId();

            if (userAuthorId.equals(bookAuthorId)) {
                return true;
            }
        }
        throw new AccessDeniedException("403 Forbidden");

    }

    @Override
    public boolean hasAccessStudent(Authentication authentication, Long studentId) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Long userStudentId=userDetails.getId();
        return userStudentId.equals(studentId);
    }
}