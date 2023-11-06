package com.azgroup.bookstore.service.impl;

import com.azgroup.bookstore.entity.Author;
import com.azgroup.bookstore.entity.Book;
import com.azgroup.bookstore.entity.StudentBook;
import com.azgroup.bookstore.entity.request.AuthorRequest;
import com.azgroup.bookstore.entity.response.*;
import com.azgroup.bookstore.exception.NotFoundException;
import com.azgroup.bookstore.repository.AuthorRepository;
import com.azgroup.bookstore.repository.BookRepository;
import com.azgroup.bookstore.repository.StudentBookRepository;
import com.azgroup.bookstore.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;
    private final ModelMapper modelMapper;
    private static final String NOT_FOUND_ERROR="Not found this given id";
    private final BookRepository bookRepository;
    private final StudentBookRepository studentBookRepository;
    @Override
    public List<AuthorResponseForAdmin> getAllActiveAuthors() {
        List<Author> authors=authorRepository.findAllByIsDeletedFalse();
        return authors.stream().map(author -> modelMapper.map(author, AuthorResponseForAdmin.class)).toList();
    }
    @Override
    public List<AuthorResponse> getAllAuthors() {
        List<Author> authors=authorRepository.findAllByIsDeletedFalse();
        return authors.stream().map(author -> modelMapper.map(author, AuthorResponse.class)).toList();
    }
    @Override
    public List<AuthorResponseForAdmin> getAllDeletedAuthors() {
        List<Author> authors=authorRepository.findAllByIsDeletedTrue();
        return authors.stream().map(author -> modelMapper.map(author, AuthorResponseForAdmin.class)).toList();
    }

    @Override
    public void addAuthor(AuthorRequest authorRequest) {
        Author author=modelMapper.map(authorRequest,Author.class);
        authorRepository.save(author);
    }

    @Override
    public void update(Long id, AuthorRequest authorRequest) {
        Author existingAuthor = authorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_ERROR));


        modelMapper.map(authorRequest, existingAuthor);


        authorRepository.save(existingAuthor);
    }


    @Override
    public AuthorResponseForAdmin getById(Long id) {
        Author author=authorRepository.findById(id).orElseThrow(()->new NotFoundException(NOT_FOUND_ERROR+" "+id));
        return modelMapper.map(author, AuthorResponseForAdmin.class);
    }

    @Override
    public void delete(Long id) {
    Author author=authorRepository.findById(id).orElseThrow(()->new NotFoundException(NOT_FOUND_ERROR+" "+id));
    author.setIsDeleted(true);
    authorRepository.save(author);
    }

    @Override
    public List<AuthorBooksResponse> authorsAllBooks(Long authorId) {
        Author author = authorRepository.findById(authorId).orElseThrow(() -> new NotFoundException(NOT_FOUND_ERROR + " " + authorId));

        List<BookResponse> bookResponses = author.getBooks().stream()
                .map(auBook -> modelMapper.map(auBook, BookResponse.class))
                .toList();

        AuthorBooksResponse authorBooksResponse = modelMapper.map(author, AuthorBooksResponse.class);
        authorBooksResponse.setBooks(bookResponses);

        List<AuthorBooksResponse> result = new ArrayList<>();
        result.add(authorBooksResponse);

        return result;
    }

    public List<ReaderResponse> getReadersForBook(Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new NotFoundException("Book not found with ID: " + bookId));

        List<StudentBook> studentBooks = studentBookRepository.findByBook(book);

        List<ReaderResponse> readers = new ArrayList<>();

        for (StudentBook studentBook : studentBooks) {
            ReaderResponse readerResponse = modelMapper.map(studentBook.getStudent(), ReaderResponse.class);
            readers.add(readerResponse);
        }

        return readers;
    }
    }
