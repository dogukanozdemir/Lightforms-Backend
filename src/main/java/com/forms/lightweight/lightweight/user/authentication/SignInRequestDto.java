package com.forms.lightweight.lightweight.user.authentication;

import lombok.*;
import jakarta.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignInRequestDto {

    @NotNull
    private String email;

    @NotNull
    private String password;
}
