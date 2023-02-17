package com.forms.lightweight.lightweight.dto;

import lombok.*;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignupUserRequestDTO {

    private String name;
    private String email;
    private String password;
    private String username;

}
