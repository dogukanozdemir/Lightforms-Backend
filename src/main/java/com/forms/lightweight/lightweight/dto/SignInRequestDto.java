package com.forms.lightweight.lightweight.dto;

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
