package com.forms.lightweight.lightweight.user;

import com.forms.lightweight.lightweight.authentication.jwt.JwtService;
import com.forms.lightweight.lightweight.user.authentication.AuthenticationResponseDto;
import com.forms.lightweight.lightweight.user.authentication.SignInRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@MockitoSettings(strictness = Strictness.LENIENT)
public class LoginUserTest {
    @InjectMocks
    private UserService userService;
    @Mock
    private JwtService jwtService;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private Authentication authentication;

    private SignInRequestDto signInRequestDto;

    @BeforeEach
    void setup(){
        signInRequestDto = SignInRequestDto.builder()
                .email("test@example.com")
                .password("testPassword")
                .build();

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
    }

    @Test
    void when_user_login_success(){
        when(authentication.isAuthenticated()).thenReturn(true);
        when(jwtService.generateToken(any())).thenReturn("token");
        AuthenticationResponseDto responseDto = userService.loginUser(signInRequestDto);
        assertEquals(responseDto.getToken(), "token");
    }

    @Test
    void when_user_not_authenticated(){
        when(authentication.isAuthenticated()).thenReturn(false);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> userService.loginUser(signInRequestDto));

        assertEquals("Invalid email or password entered",
                exception.getReason());
        assertEquals(exception.getStatusCode(), HttpStatus.UNAUTHORIZED);
    }

}
