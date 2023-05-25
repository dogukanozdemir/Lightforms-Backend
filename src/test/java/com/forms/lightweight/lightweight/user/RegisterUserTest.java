package com.forms.lightweight.lightweight.user;

import com.forms.lightweight.lightweight.authentication.jwt.JwtService;
import com.forms.lightweight.lightweight.user.dto.AuthenticationResponseDto;
import com.forms.lightweight.lightweight.user.dto.SignupUserRequestDto;
import com.forms.lightweight.lightweight.user.entity.UserEntity;
import com.forms.lightweight.lightweight.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@MockitoSettings(strictness = Strictness.LENIENT)
class RegisterUserTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    private SignupUserRequestDto signupUserRequestDto;

    @BeforeEach
    void setup(){
        signupUserRequestDto = SignupUserRequestDto.builder()
                .name("name")
                .email("test@example.com")
                .password("testPassword")
                .build();

    }

    @Test
    void when_user_register_success(){
        when(userRepository.findByEmail(any())).thenReturn(Optional.empty());
        when(jwtService.generateToken(any())).thenReturn("dummytoken");

        AuthenticationResponseDto responseDto = userService.registerUser(signupUserRequestDto);
        assertEquals(responseDto.getToken(), "dummytoken");
    }

    @Test
    void when_user_not_exist(){
        UserEntity user = UserEntity.builder().id(1L).build();
        when(userRepository.findByEmail(any())).thenReturn(Optional.of(user));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> userService.registerUser(signupUserRequestDto));

        assertEquals(String.format("User with '%s' email already exists", signupUserRequestDto.getEmail()),
                exception.getReason());
        assertEquals(exception.getStatusCode(), HttpStatus.BAD_REQUEST);
    }
}
