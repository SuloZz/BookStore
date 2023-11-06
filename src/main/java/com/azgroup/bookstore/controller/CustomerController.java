package com.azgroup.bookstore.controller;

import com.azgroup.bookstore.entity.response.AuthorBooksResponse;
import com.azgroup.bookstore.entity.response.AuthorResponse;
import com.azgroup.bookstore.entity.response.BookResponse;
import com.azgroup.bookstore.service.AuthorService;
import com.azgroup.bookstore.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer/")
@RequiredArgsConstructor
public class CustomerController {
    private final BookService bookService;
    private final AuthorService authorService;
    @GetMapping("/all/books")
    public ResponseEntity <List<BookResponse>> getAll(){
        return new ResponseEntity<>(bookService.getAllActiveBook(),HttpStatus.OK);
    }
    @GetMapping("/books/{authorId}")
    public ResponseEntity<List<AuthorBooksResponse>> getBooks(@PathVariable Long authorId) {
        List<AuthorBooksResponse> authorBooksList = authorService.authorsAllBooks(authorId);
        return new ResponseEntity<>(authorBooksList, HttpStatus.OK);
    }

    @GetMapping("/our/authors")
    ResponseEntity<List<AuthorResponse>> getAllAuthors(){
        return new ResponseEntity<>(authorService.getAllAuthors(),HttpStatus.OK);
    }


}
