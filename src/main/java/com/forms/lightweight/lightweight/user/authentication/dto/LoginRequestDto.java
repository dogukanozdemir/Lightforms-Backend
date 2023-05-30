package com.forms.lightweight.lightweight.user.authentication.dto;

import lombok.*;
import jakarta.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginRequestDto {

    @NotNull
    private String email;

    @NotNull
    private String password;
}
