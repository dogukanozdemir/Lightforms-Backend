package com.forms.lightweight.lightweight.dto;

import com.forms.lightweight.lightweight.entity.UserEntity;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDTO {

    private Long id;
    private String name;
    private String email;
    private String username;
    private String password;

    public static UserDTO from(UserEntity user){
        return UserDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .username(user.getUsername())
                .email(user.getEmail())
                .password(user.getPassword())
                .build();
    }
}
