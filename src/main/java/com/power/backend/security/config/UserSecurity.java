package com.power.backend.security.config;

import com.power.backend.security.model.UsuarioPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component("userSecurity")
public class UserSecurity {

    public boolean isCurrentUser(Authentication authentication, Integer userId) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }

        Object principal = authentication.getPrincipal();
        if (principal instanceof UsuarioPrincipal userPrincipal) {
            return userPrincipal.getUsuario().getId().equals(userId);
        }

        return false;
    }
}
