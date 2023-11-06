package com.azgroup.bookstore.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;



import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@DiscriminatorValue("author")
public class Author extends User {

    @Column(name = "full_name")
    private String fullName;

    @JsonFormat(pattern = "dd-MM-yyyy")
    @Column(name = "age")
    private LocalDate age;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
    @ToString.Exclude
    private List<Book> books = new ArrayList<>();

    @ManyToMany(mappedBy = "subscriptions")
    @ToString.Exclude
    private Set<Student> subscribers;

    public Author(String username, String email, String encode) {
        super(username, email, encode);
    }
}
