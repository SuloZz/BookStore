package com.azgroup.bookstore.service.impl;

import com.azgroup.bookstore.entity.Author;
import com.azgroup.bookstore.entity.Book;
import com.azgroup.bookstore.entity.Student;
import com.azgroup.bookstore.entity.request.BookRequest;
import com.azgroup.bookstore.entity.response.BookResponse;
import com.azgroup.bookstore.exception.NotFoundException;
import com.azgroup.bookstore.repository.AuthorRepository;
import com.azgroup.bookstore.repository.BookRepository;
import com.azgroup.bookstore.repository.StudentRepository;
import com.azgroup.bookstore.service.BookService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


import javax.mail.MessagingException;
import java.util.List;
@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final StudentRepository studentRepository;
    private final ModelMapper modelMapper;
    private final EmailServiceImpl emailService;
    private static final String NOT_FOUND_ERROR="Not found this given id";


    @Override
    public List<BookResponse> getAllActiveBook() {
        List<Book> books=bookRepository.findAllByIsDeletedFalse();
        return books.stream().map(book -> modelMapper.map(book,BookResponse.class)).toList();
    }

    @Override
    public List<BookResponse> getAllDeletedBook() {
        List<Book> books=bookRepository.findAllByIsDeletedTrue();
        return books.stream().map(book -> modelMapper.map(book,BookResponse.class)).toList();
    }

    @Override
    public void addBook(BookRequest bookRequest) {
        Long authorId = bookRequest.getAuthorId();
        Author author = authorRepository.findById(authorId).orElseThrow(() -> new NotFoundException(NOT_FOUND_ERROR + " " + authorId));

        Book newBook = new Book();
        newBook.setName(bookRequest.getName());
        newBook.setIsDeleted(false);
        newBook.setAuthor(author);
        bookRepository.save(newBook);


        List<Student> students = studentRepository.findAllByIsDeletedFalse();

        String subject = "Added new Book " + newBook.getName() + ", Author: " + newBook.getAuthor().getFullName();
        String message = "Added new book in our system: " + newBook.getName() + ".\n" +
                "If you want to read this book, you can visit our site";

        String emailMessage = "Dear %s,\n\n%s";

        students.forEach(student -> {

            if (student.getSubscriptions().contains(author)) {
                String formattedMessage = String.format(emailMessage, student.getFullName(), message);
                try {
                    emailService.sendEmail(student.getEmail(), subject, formattedMessage);
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void updateBook(Long id, BookRequest bookRequest) {
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_ERROR));


        modelMapper.map(bookRequest, existingBook);


        Author author = authorRepository.findById(bookRequest.getAuthorId())
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_ERROR));


        existingBook.setAuthor(author);


        bookRepository.save(existingBook);
    }


    @Override
    public BookResponse getById(Long id) {
        Book book=bookRepository.findById(id).orElseThrow(()->new NotFoundException(NOT_FOUND_ERROR+" "+id));
        return modelMapper.map(book,BookResponse.class);
    }

    @Override
    public void delete(Long id) {
        Book book=bookRepository.findById(id).orElseThrow(()->new NotFoundException(NOT_FOUND_ERROR+" "+id));
        book.setIsDeleted(true);
        bookRepository.save(book);
    }
}
