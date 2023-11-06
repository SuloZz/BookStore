package com.azgroup.bookstore.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Table(name = "student_books")
@Getter
@Setter
public class StudentBook {
    @EmbeddedId
    private StudentBookId id;

    @ManyToOne
    @MapsId("studentId")
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @MapsId("bookId")
    @JoinColumn(name = "book_id")
    private Book book;
    private Boolean isReading;

}