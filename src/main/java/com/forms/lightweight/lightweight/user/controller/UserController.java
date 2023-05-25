package com.forms.lightweight.lightweight.user.controller;

import com.forms.lightweight.lightweight.user.UserService;
import com.forms.lightweight.lightweight.user.dto.UserProfileResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/services/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<UserProfileResponseDto> getUserProfile(){
        return ResponseEntity.ok(userService.getUserProfile());
    }
}
