package com.forms.lightweight.lightweight.user;

import com.forms.lightweight.lightweight.authentication.jwt.JwtService;
import com.forms.lightweight.lightweight.authentication.util.AuthUtil;
import com.forms.lightweight.lightweight.user.authentication.AuthenticationResponseDto;
import com.forms.lightweight.lightweight.user.authentication.SignInRequestDto;
import com.forms.lightweight.lightweight.user.authentication.SignupUserRequestDto;
import com.forms.lightweight.lightweight.user.dto.UserProfileResponseDto;
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

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    private final AuthUtil authUtil;

    public AuthenticationResponseDto registerUser(SignupUserRequestDto signupUserRequestDTO) {
        if(userRepository.findByEmail(signupUserRequestDTO.getEmail()).isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    String.format("User with '%s' email already exists", signupUserRequestDTO.getEmail()));
        }

        UserEntity user = UserEntity.builder()
                .name(signupUserRequestDTO.getName())
                .email(signupUserRequestDTO.getEmail())
                .password(passwordEncoder.encode(signupUserRequestDTO.getPassword()))
                .role(Role.USER)
                .isValidated(false)
                .build();
        userRepository.save(user);
        String token = jwtService.generateToken(signupUserRequestDTO.getEmail());
        return AuthenticationResponseDto.builder()
                .token(token)
                .build();

    }

    public AuthenticationResponseDto loginUser(SignInRequestDto signInRequestDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signInRequestDTO.getEmail(),signInRequestDTO.getPassword()));

        if(authentication.isAuthenticated()){
            String token = jwtService.generateToken(signInRequestDTO.getEmail());
            return AuthenticationResponseDto.builder()
                    .token(token)
                    .build();
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid email or password entered");
        }
    }

    public UserProfileResponseDto getUserProfile(){
        UserEntity currentUser = authUtil.getCurrentUser();
        return UserProfileResponseDto.builder()
                .name(currentUser.getName())
                .email(currentUser.getEmail())
                .build();
    }
}
