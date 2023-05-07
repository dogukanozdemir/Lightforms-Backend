package com.forms.lightweight.lightweight.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignupUserRequestDto {

    @NotNull
    @Size(max = 50)
    private String name;

    @NotNull
    private String email;

    @NotNull
    private String password;

}
