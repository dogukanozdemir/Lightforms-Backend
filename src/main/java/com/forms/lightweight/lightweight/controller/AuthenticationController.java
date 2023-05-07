package com.forms.lightweight.lightweight.controller;

import com.forms.lightweight.lightweight.dto.AuthenticationResponseDto;
import com.forms.lightweight.lightweight.dto.SignInRequestDto;
import com.forms.lightweight.lightweight.dto.SignupUserRequestDto;
import com.forms.lightweight.lightweight.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final UserService userService;

    @PostMapping(value = "/signup")
    public ResponseEntity<AuthenticationResponseDto> signup(@Validated @RequestBody SignupUserRequestDto signupUserRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userService.registerUser(signupUserRequestDTO));

    }

    @PostMapping(value = "/login")
    public ResponseEntity<AuthenticationResponseDto> login(@Validated @RequestBody SignInRequestDto signInRequestDTO) {
        return ResponseEntity.ok(userService.loginUser(signInRequestDTO));

    }
}
