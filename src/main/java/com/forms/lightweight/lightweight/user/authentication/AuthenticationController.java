package com.forms.lightweight.lightweight.user.authentication;

import com.forms.lightweight.lightweight.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


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

    @GetMapping(value = "/confirm")
    public ResponseEntity<Boolean> confirmEmail(@RequestParam String token) {
        if(token == null)
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Provided token is invalid");
        }


    }
}
