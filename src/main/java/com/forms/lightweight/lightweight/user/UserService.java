package com.forms.lightweight.lightweight.user;

import com.forms.lightweight.lightweight.authentication.jwt.JwtService;
import com.forms.lightweight.lightweight.authentication.util.AuthUtil;
import com.forms.lightweight.lightweight.user.authentication.repository.UserConfirmationRepository;
import com.forms.lightweight.lightweight.user.dto.UserProfileResponseDto;
import com.forms.lightweight.lightweight.user.entity.UserEntity;
import com.forms.lightweight.lightweight.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserConfirmationRepository userConfirmationRepository;
    private final AuthUtil authUtil;


    public UserProfileResponseDto getUserProfile(){
        UserEntity currentUser = authUtil.getCurrentUser();
        return UserProfileResponseDto.builder()
                .name(currentUser.getName())
                .email(currentUser.getEmail())
                .build();
    }
}
