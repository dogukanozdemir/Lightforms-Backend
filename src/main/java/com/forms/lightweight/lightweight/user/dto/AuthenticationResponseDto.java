package com.forms.lightweight.lightweight.user.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AuthenticationResponseDto {

    private String token;
}
