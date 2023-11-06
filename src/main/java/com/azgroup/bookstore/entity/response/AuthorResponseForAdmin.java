package com.azgroup.bookstore.entity.response;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
@Data
public class AuthorResponseForAdmin extends AuthorResponse {

    private String username;
    private String email;
    private String password;

}
