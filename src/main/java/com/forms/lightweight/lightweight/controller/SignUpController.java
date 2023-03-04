package com.forms.lightweight.lightweight.controller;

import com.forms.lightweight.lightweight.dto.ResponseDTO;
import com.forms.lightweight.lightweight.dto.SignInRequestDTO;
import com.forms.lightweight.lightweight.dto.SignupUserRequestDTO;
import com.forms.lightweight.lightweight.dto.UserDTO;
import com.forms.lightweight.lightweight.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class SignUpController {

    private final UserService userService;


    @PostMapping(value = "/signup")
    public ResponseEntity<ResponseDTO<UserDTO>> signup(@Validated @RequestBody SignupUserRequestDTO signupUserRequestDTO) {
        return userService.registerUser(signupUserRequestDTO);

    }

    @PostMapping(value = "/login")
    public ResponseEntity<ResponseDTO<UserDTO>> login(@Validated @RequestBody SignInRequestDTO signInRequestDTO) {
        return userService.loginUser(signInRequestDTO);

    }
}
