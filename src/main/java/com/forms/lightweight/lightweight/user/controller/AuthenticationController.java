package com.forms.lightweight.lightweight.user.controller;

import com.forms.lightweight.lightweight.user.UserService;
import com.forms.lightweight.lightweight.user.dto.AuthenticationResponseDto;
import com.forms.lightweight.lightweight.user.dto.SignInRequestDto;
import com.forms.lightweight.lightweight.user.dto.SignupUserRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


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
