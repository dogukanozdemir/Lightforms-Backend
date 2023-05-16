package com.forms.lightweight.lightweight.authentication;

import com.forms.lightweight.lightweight.authentication.dto.AuthenticatedUser;
import com.forms.lightweight.lightweight.user.entity.UserEntity;
import com.forms.lightweight.lightweight.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(
                        () -> new UsernameNotFoundException("Invalid email: " + email)
                );

        return new AuthenticatedUser(
                userEntity.getEmail(),
                userEntity.getPassword(),
                List.of((GrantedAuthority) () -> userEntity.getRole().name()),
                userEntity
        );
    }
}
