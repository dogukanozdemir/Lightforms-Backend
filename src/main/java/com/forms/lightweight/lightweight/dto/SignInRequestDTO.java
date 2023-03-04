package com.forms.lightweight.lightweight.dto;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignInRequestDTO {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;
}
