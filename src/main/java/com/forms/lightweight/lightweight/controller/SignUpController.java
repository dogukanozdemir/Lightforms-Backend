package com.forms.lightweight.lightweight.controller;

import com.forms.lightweight.lightweight.dto.SignupUserRequestDTO;
import com.forms.lightweight.lightweight.dto.UserDTO;
import com.forms.lightweight.lightweight.entity.UserEntity;
import com.forms.lightweight.lightweight.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class SignUpController {

    private final UserRepository userRepository;

    @PostMapping(value = "/signup")
    public UserDTO signup(){
        log.info("Agarfuin entered");
        UserEntity user = userRepository.findById(1L).get();
        return UserDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .password(user.getPassword())
                .username(user.getUsername()).build();
    }
}
