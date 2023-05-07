package com.forms.lightweight.lightweight.service;

import com.forms.lightweight.lightweight.authentication.jwt.JwtService;
import com.forms.lightweight.lightweight.dto.AuthenticationResponseDto;
import com.forms.lightweight.lightweight.dto.SignInRequestDto;
import com.forms.lightweight.lightweight.dto.SignupUserRequestDto;
import com.forms.lightweight.lightweight.entity.UserEntity;
import com.forms.lightweight.lightweight.enums.Role;
import com.forms.lightweight.lightweight.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponseDto registerUser(SignupUserRequestDto signupUserRequestDTO) {
        if(userRepository.findByEmail(signupUserRequestDTO.getEmail()).isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    String.format("User with '%s' email already exists", signupUserRequestDTO.getEmail()));
        }

        UserEntity user = UserEntity.builder()
                .name(signupUserRequestDTO.getName())
                .email(signupUserRequestDTO.getEmail())
                .password(passwordEncoder.encode(signupUserRequestDTO.getPassword()))
                .role(Role.USER)
                .build();
        userRepository.save(user);
        String token = jwtService.generateToken(signupUserRequestDTO.getEmail());
        return AuthenticationResponseDto.builder()
                .token(token)
                .build();

    }

    public AuthenticationResponseDto loginUser(SignInRequestDto signInRequestDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signInRequestDTO.getEmail(),signInRequestDTO.getPassword()));

        if(authentication.isAuthenticated()){
            String token = jwtService.generateToken(signInRequestDTO.getEmail());
            return AuthenticationResponseDto.builder()
                    .token(token)
                    .build();
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid email or password entered");
        }
    }
}
