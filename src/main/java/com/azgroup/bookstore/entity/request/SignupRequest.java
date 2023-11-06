package com.azgroup.bookstore.entity.request;

import com.azgroup.bookstore.entity.ERole;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Set;

@Data
@RequiredArgsConstructor
public class SignupRequest {
    private String fullName;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate age;
    @NotBlank
    @Size(min = 3, max = 20)
    private String username;
    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    private Set<ERole> role;

    @NotBlank
    @Size(min = 6, max = 40)
    private String password;


}
