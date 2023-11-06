package com.azgroup.bookstore.entity;

import lombok.*;


import javax.persistence.*;


@Entity
@Table(name = "roles")
@RequiredArgsConstructor
@Getter
@Setter
@ToString
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "name", length = 20)
    private ERole name;
}
