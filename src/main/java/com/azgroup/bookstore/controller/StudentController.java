package com.azgroup.bookstore.controller;

import com.azgroup.bookstore.entity.request.StudentBookDto;
import com.azgroup.bookstore.entity.response.StudentReadsBook;
import com.azgroup.bookstore.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/student/")
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;

    @PostMapping("/start-read-book/{studentId}")
    @PreAuthorize("@accessControlService.hasAccessStudent(authentication,#studentId)")
    public ResponseEntity<StudentBookDto> startReadBook(
            @RequestBody StudentBookDto studentBookDto, @PathVariable Long studentId
    ) {
        studentService.startReadBook(studentBookDto, studentId);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @GetMapping("reading-book/{studentId}")
    @PreAuthorize("@accessControlService.hasAccessStudent(authentication,#studentId)")
    ResponseEntity<List<StudentReadsBook>> readsBook(@PathVariable Long studentId) {
        return new ResponseEntity(studentService.getStudentReadBooks(studentId), HttpStatus.OK);
    }

    @PostMapping("/subscribe-to-author/{studentId}/{authorId}")
    @PreAuthorize("@accessControlService.hasAccessStudent(authentication, #studentId)")
    public ResponseEntity<String> subscribeToAuthor(
            @PathVariable Long studentId,
            @PathVariable Long authorId
    ) {
        studentService.subscribeToAuthor(studentId, authorId);
        return new ResponseEntity<>("Subscribe is success", HttpStatus.OK);
    }

    @DeleteMapping("deActive/account/student/{studentId}")
    @PreAuthorize("@accessControlService.hasAccessStudent(authentication,#studentId)")
    ResponseEntity<Void> deleteStudent(@PathVariable Long studentId) {
        studentService.delete(studentId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
