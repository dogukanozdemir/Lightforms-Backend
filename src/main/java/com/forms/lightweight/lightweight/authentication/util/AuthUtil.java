package com.forms.lightweight.lightweight.authentication.util;

import com.forms.lightweight.lightweight.authentication.dto.AuthenticatedUser;
import com.forms.lightweight.lightweight.entity.UserEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthUtil {

    private Authentication getAuthentication(){
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public UserEntity getCurrentCustomer(){
        Object principal = getAuthentication().getPrincipal();
        assert principal instanceof AuthenticatedUser;
        return ((AuthenticatedUser) principal).getUser();
    }
}
