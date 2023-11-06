package com.azgroup.bookstore.service.impl;

import com.azgroup.bookstore.entity.*;
import com.azgroup.bookstore.entity.request.StudentRequest;
import com.azgroup.bookstore.entity.request.StudentBookDto;
import com.azgroup.bookstore.entity.response.BookResponse;
import com.azgroup.bookstore.entity.response.StudentReadsBook;
import com.azgroup.bookstore.entity.response.StudentResponse;
import com.azgroup.bookstore.exception.NotFoundException;
import com.azgroup.bookstore.repository.AuthorRepository;
import com.azgroup.bookstore.repository.BookRepository;
import com.azgroup.bookstore.repository.StudentBookRepository;
import com.azgroup.bookstore.repository.StudentRepository;
import com.azgroup.bookstore.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;


@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final ModelMapper modelMapper;
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final StudentBookRepository studentBookRepository;
    private static final String NOT_FOUND_ERROR="Not found this given id";

    @Override
    public List<StudentResponse> getAllActiveStudent() {
        List<Student> students=studentRepository.findAllByIsDeletedFalse();
        return students.stream().map(student -> modelMapper.map(student,StudentResponse.class)).toList();

    }

    @Override
    public List<StudentResponse> getAllDeletedStudent() {
        List<Student> students=studentRepository.findAllByIsDeletedTrue();
        return students.stream().map(student -> modelMapper.map(student,StudentResponse.class)).toList();
    }

    @Override
    public void add(StudentRequest studentRequest) {
        Student student=modelMapper.map(studentRequest,Student.class);
        studentRepository.save(student);
    }

    @Override
    public void update(Long id, StudentRequest studentRequest) {
        Student existingStudent = studentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_ERROR));
        modelMapper.map(studentRequest, existingStudent);
        studentRepository.save(existingStudent);
    }


    @Override
    public StudentResponse getById(Long id) {
        Student student=studentRepository.findById(id).orElseThrow(()->new NotFoundException(NOT_FOUND_ERROR+" "+id));
        return modelMapper.map(student,StudentResponse.class);
    }

    @Override
    public void delete(Long id) {
        Student student=studentRepository.findById(id).orElseThrow(()->new NotFoundException(NOT_FOUND_ERROR+" "+id));
        student.setIsDeleted(true);
        studentRepository.save(student);
    }

    @Override
    public void startReadBook(StudentBookDto studentBookDto,Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_ERROR + " " + studentId));

        Book book = bookRepository.findById(studentBookDto.getBookId())
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_ERROR + " " + studentBookDto.getBookId()));
        StudentBook studentBook = new StudentBook();
        studentBook.setStudent(student);
        studentBook.setBook(book);
        studentBook.setIsReading(true);
        StudentBookId id = new StudentBookId(student.getId(), book.getId());
        studentBook.setId(id);


        studentBookRepository.save(studentBook);

    }




    @Override
    public StudentReadsBook getStudentReadBooks(Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_ERROR + " " + studentId));

        StudentReadsBook studentReadsBook = modelMapper.map(student, StudentReadsBook.class);

        Set<Book> readBooks = student.getBooksRead();

        List<BookResponse> bookResponses = readBooks.stream()
                .map(book -> modelMapper.map(book, BookResponse.class))
                .toList();

        studentReadsBook.setReadBooks(bookResponses);

        return studentReadsBook;
    }

    @Override
    public void subscribeToAuthor(Long studentId, Long authorId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_ERROR));
        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_ERROR));

        student.getSubscriptions().add(author);
        studentRepository.save(student);
    }
}
