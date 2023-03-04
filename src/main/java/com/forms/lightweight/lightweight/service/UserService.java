package com.forms.lightweight.lightweight.service;

import com.forms.lightweight.lightweight.base.exception.InvalidCrendentailsException;
import com.forms.lightweight.lightweight.base.exception.UserAlreadyExistsException;
import com.forms.lightweight.lightweight.base.exception.UserNotExistException;
import com.forms.lightweight.lightweight.dto.ResponseDTO;
import com.forms.lightweight.lightweight.dto.SignInRequestDTO;
import com.forms.lightweight.lightweight.dto.SignupUserRequestDTO;
import com.forms.lightweight.lightweight.dto.UserDTO;
import com.forms.lightweight.lightweight.entity.UserEntity;
import com.forms.lightweight.lightweight.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;


    private final BCryptPasswordEncoder passwordEncoder;

    public ResponseEntity<ResponseDTO<UserDTO>> registerUser(SignupUserRequestDTO signupUserRequestDTO) {
        UserEntity foundUser = userRepository.findByEmail(signupUserRequestDTO.getEmail());
        try {
            if (foundUser != null) {
                throw new UserAlreadyExistsException("User already registered to the system.");
            }
            UserEntity user = UserEntity.builder()
                    .name(signupUserRequestDTO.getName())
                    .username(signupUserRequestDTO.getUsername())
                    .email(signupUserRequestDTO.getEmail())
                    .password(passwordEncoder.encode(signupUserRequestDTO.getPassword()))
                    .build();

            userRepository.save(user);

            return ResponseEntity.ok(ResponseDTO.<UserDTO>builder().data(UserDTO.from(user)).message("User successfully signed up.").build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseDTO.<UserDTO>builder().message(e.getMessage()).build());
        }
    }

    public ResponseEntity<ResponseDTO<UserDTO>> loginUser(SignInRequestDTO signInRequestDTO) {
        UserEntity user = userRepository.findByEmail(signInRequestDTO.getEmail());
        try {
            if (user == null) {
                throw new UserNotExistException("No user was found with the given email.");
            }
            if (!passwordEncoder.matches(signInRequestDTO.getPassword(), user.getPassword())) {
                throw new InvalidCrendentailsException("The provided email or password is incorrect.");
            }

            return ResponseEntity.ok(ResponseDTO.<UserDTO>builder().message("User successfully logged in").data(UserDTO.from(user)).build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ResponseDTO.<UserDTO>builder().message(e.getMessage()).build());
        }


    }
}
