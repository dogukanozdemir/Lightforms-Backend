package com.forms.lightweight.lightweight.user.authentication.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterUserResponseDto {

    private String token;
}
