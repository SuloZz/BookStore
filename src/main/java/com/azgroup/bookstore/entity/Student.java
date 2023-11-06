package com.azgroup.bookstore.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;


import javax.persistence.*;
import java.time.LocalDate;

import java.util.HashSet;
import java.util.Set;


@Entity
@Getter
@Setter
@RequiredArgsConstructor
@DiscriminatorValue("student")
public class Student extends User {

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @JsonFormat(pattern = "dd-MM-yyyy")
    @Column(name = "age")
    private LocalDate age;

    @ManyToMany
    @JoinTable(
            name = "student_books",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    private Set<Book> booksRead = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "student_subscription",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "subscription_id")
    )
    private Set<Author> subscriptions;

    public Student(String username, String email, String encode) {
        super(username, email, encode);
    }


}
