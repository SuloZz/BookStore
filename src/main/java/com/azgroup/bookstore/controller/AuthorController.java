package com.azgroup.bookstore.controller;

import com.azgroup.bookstore.entity.request.BookRequest;
import com.azgroup.bookstore.entity.response.AuthorBooksResponse;
import com.azgroup.bookstore.entity.response.ReaderResponse;

import com.azgroup.bookstore.service.AuthorService;
import com.azgroup.bookstore.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/author/")
@RequiredArgsConstructor
public class AuthorController {
    private final AuthorService authorService;
    private final BookService bookService;

    @PostMapping("/add/book")
    @PreAuthorize("@accessControlService.hasAccessAuthor(authentication,#bookRequest.authorId)")
    public ResponseEntity<Void> add(@RequestBody BookRequest bookRequest) {
        bookService.addBook(bookRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/books/{authorId}")
    @PreAuthorize("@accessControlService.hasAccessAuthor(authentication,#authorId)")
    public ResponseEntity<List<AuthorBooksResponse>> getBooks(@PathVariable Long authorId) {
        List<AuthorBooksResponse> authorBooksResponse = authorService.authorsAllBooks(authorId);
        return new ResponseEntity<>(authorBooksResponse, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{bookId}")
    @PreAuthorize("@accessControlService.hasAccessForBookId(authentication,#bookId)")
    public ResponseEntity<Void> delete(@PathVariable Long bookId){
        bookService.delete(bookId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PreAuthorize("@accessControlService.hasAccessAuthor(authentication,#authorId)")
    @DeleteMapping("/deActive/account/author/{authorId}")
    ResponseEntity<Void> deleteAuthor(@PathVariable Long authorId){
        authorService.delete(authorId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/{bookId}/readers")
    @PreAuthorize("@accessControlService.hasAccessForBookId(authentication,#bookId)")
    public ResponseEntity<List<ReaderResponse>> getReadersForBook(@PathVariable Long bookId) {
        List<ReaderResponse> readers = authorService.getReadersForBook(bookId);
        return ResponseEntity.ok(readers);
    }
}
