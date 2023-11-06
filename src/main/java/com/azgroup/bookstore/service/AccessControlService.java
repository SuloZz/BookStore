package com.azgroup.bookstore.service;

import org.springframework.security.core.Authentication;

public interface AccessControlService {
    boolean hasAccessAuthor(Authentication authentication, Long authorId);
    boolean hasAccessForBookId(Authentication authentication,Long bookId);
    boolean hasAccessStudent(Authentication authentication,Long studentId);
}
