package com.forms.lightweight.lightweight.user.authentication;

import com.forms.lightweight.lightweight.authentication.jwt.JwtService;
import com.forms.lightweight.lightweight.email.EmailContentService;
import com.forms.lightweight.lightweight.email.EmailService;
import com.forms.lightweight.lightweight.user.authentication.dto.LoginResponseDto;
import com.forms.lightweight.lightweight.user.authentication.dto.LoginRequestDto;
import com.forms.lightweight.lightweight.user.authentication.dto.RegisterUserRequestDto;
import com.forms.lightweight.lightweight.user.authentication.entity.UserConfirmation;
import com.forms.lightweight.lightweight.user.authentication.repository.UserConfirmationRepository;
import com.forms.lightweight.lightweight.user.entity.UserEntity;
import com.forms.lightweight.lightweight.user.enums.Role;
import com.forms.lightweight.lightweight.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private static final int CONFIRMATION_EXPIRATION_HOURS = 24;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final UserConfirmationRepository userConfirmationRepository;
    private final EmailService emailService;
    private final JwtService jwtService;

    public void registerUser(RegisterUserRequestDto registerUserRequestDTO) {
        if (userRepository.findByEmail(registerUserRequestDTO.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    String.format("User with '%s' email already exists", registerUserRequestDTO.getEmail()));
        }

        UserEntity user = UserEntity.builder()
                .name(registerUserRequestDTO.getName())
                .email(registerUserRequestDTO.getEmail())
                .password(passwordEncoder.encode(registerUserRequestDTO.getPassword()))
                .role(Role.USER)
                .isValidated(false)
                .build();

        userRepository.save(user);

        UserConfirmation confirmation = UserConfirmation.builder()
                .token(UUID.randomUUID().toString())
                .userId(user.getId())
                .expirationDate(Date.from(Instant.now().plus(CONFIRMATION_EXPIRATION_HOURS, ChronoUnit.HOURS)))
                .build();

        userConfirmationRepository.save(confirmation);

        String mailContent = EmailContentService.getConfirmationMailContent(user.getName(), confirmation.getToken());
        emailService.sendHTMLEmail(user.getEmail(), "Lightforms Email Verification", mailContent);
    }

    public LoginResponseDto loginUser(LoginRequestDto loginRequestDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDTO.getEmail(), loginRequestDTO.getPassword()));

        if (!authentication.isAuthenticated()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid email or password entered");
        }

        UserEntity currentUser = userRepository.findByEmail(loginRequestDTO.getEmail())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        if (!currentUser.getIsValidated()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Please verify your email to login");
        }

        return LoginResponseDto.builder()
                .token(jwtService.generateToken(loginRequestDTO.getEmail()))
                .build();
    }

    public void confirmEmail(String confirmationToken){
        UserConfirmation confirmation = userConfirmationRepository.findByToken(confirmationToken)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Provided token is invalid"));
        UserEntity user = userRepository.findUserByConfirmationToken(confirmationToken)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "User not found with the provided token"));
        Date currentDate = new Date();
        if(currentDate.after(confirmation.getExpirationDate())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Token expired");
        }

        user.setIsValidated(true);
        userRepository.save(user);
    }
}
