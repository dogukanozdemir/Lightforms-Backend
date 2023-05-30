package com.forms.lightweight.lightweight.user;

import com.forms.lightweight.lightweight.authentication.jwt.JwtService;
import com.forms.lightweight.lightweight.email.EmailContentService;
import com.forms.lightweight.lightweight.email.EmailService;
import com.forms.lightweight.lightweight.user.authentication.AuthenticationService;
import com.forms.lightweight.lightweight.user.authentication.dto.LoginResponseDto;
import com.forms.lightweight.lightweight.user.authentication.dto.RegisterUserRequestDto;
import com.forms.lightweight.lightweight.user.authentication.entity.UserConfirmation;
import com.forms.lightweight.lightweight.user.authentication.repository.UserConfirmationRepository;
import com.forms.lightweight.lightweight.user.entity.UserEntity;
import com.forms.lightweight.lightweight.user.enums.Role;
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

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@MockitoSettings(strictness = Strictness.LENIENT)
class RegisterUserTest {

    @InjectMocks
    private AuthenticationService authenticationService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private UserConfirmationRepository userConfirmationRepository;
    @Mock
    private EmailService emailService;
    private RegisterUserRequestDto registerUserRequestDto;

    @BeforeEach
    void setup(){

        registerUserRequestDto = RegisterUserRequestDto.builder()
                .name("name")
                .email("test@example.com")
                .password("testPassword")
                .build();
    }
    @Test
    void when_user_register_success(){
        UserEntity userEntity = UserEntity.builder()
                .name(registerUserRequestDto.getName())
                .email(registerUserRequestDto.getEmail())
                .password("encodedPassword")
                .role(Role.USER)
                .isValidated(false)
                .build();
        UserConfirmation confirmation = UserConfirmation.builder()
                .token("confirmationToken")
                .userId(userEntity.getId())
                .expirationDate(Date.from(Instant.now().plus(24, ChronoUnit.HOURS)))
                .build();

        when(userRepository.findByEmail(registerUserRequestDto.getEmail())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(registerUserRequestDto.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(userEntity)).thenReturn(userEntity);
        when(userConfirmationRepository.save(confirmation)).thenReturn(confirmation);

        authenticationService.registerUser(registerUserRequestDto);

        verify(emailService, times(1)).
                sendHTMLEmail(eq(registerUserRequestDto.getEmail()), eq("Lightforms Email Verification"), anyString());

    }

    @Test
    void when_user_not_exist(){
        UserEntity user = UserEntity.builder().id(1L).build();
        when(userRepository.findByEmail(any())).thenReturn(Optional.of(user));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> authenticationService.registerUser(registerUserRequestDto));

        assertEquals(String.format("User with '%s' email already exists", registerUserRequestDto.getEmail()),
                exception.getReason());
        assertEquals(exception.getStatusCode(), HttpStatus.BAD_REQUEST);
    }
}
