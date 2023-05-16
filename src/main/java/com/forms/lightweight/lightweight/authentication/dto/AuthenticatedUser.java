package com.forms.lightweight.lightweight.authentication.dto;

import com.forms.lightweight.lightweight.user.entity.UserEntity;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
public class AuthenticatedUser extends User {

    private final UserEntity user;

    public AuthenticatedUser(String username, String password, Collection<? extends GrantedAuthority> authorities, UserEntity user) {
        super(username, password, authorities);
        this.user = user;
    }
}
