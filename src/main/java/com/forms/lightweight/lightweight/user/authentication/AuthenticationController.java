package com.forms.lightweight.lightweight.user.authentication;

import com.forms.lightweight.lightweight.user.authentication.dto.LoginResponseDto;
import com.forms.lightweight.lightweight.user.authentication.dto.LoginRequestDto;
import com.forms.lightweight.lightweight.user.authentication.dto.RegisterUserRequestDto;
import com.forms.lightweight.lightweight.user.authentication.dto.RegisterUserResponseDto;
import com.forms.lightweight.lightweight.user.entity.UserEntity;
import com.forms.lightweight.lightweight.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping(value = "/signup")
    public ResponseEntity<RegisterUserResponseDto> signup(@Validated @RequestBody RegisterUserRequestDto registerUserRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authenticationService.registerUser(registerUserRequestDTO));

    }

    @PostMapping(value = "/login")
    public ResponseEntity<LoginResponseDto> login(@Validated @RequestBody LoginRequestDto loginRequestDTO) {
        return ResponseEntity.ok(authenticationService.loginUser(loginRequestDTO));

    }

    @GetMapping(value = "/confirm")
    @ResponseStatus(HttpStatus.OK)
    public void confirmEmail(@RequestParam String token) {
        authenticationService.confirmEmail(token);
    }
}
