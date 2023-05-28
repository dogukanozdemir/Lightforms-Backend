package com.forms.lightweight.lightweight.user.authentication.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class LoginResponseDto {

    private String token;
}
