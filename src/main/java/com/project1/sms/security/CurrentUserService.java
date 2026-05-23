package com.project1.sms.security;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
public class CurrentUserService {

    public String getUsername() {
        Jwt jwt = getJwt();
        return jwt.getSubject(); // "sub"
    }

    public Long getUserId() {
        Jwt jwt = getJwt();
        return jwt.getClaim("userId");
    }

    private Jwt getJwt() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null
                || !authentication.isAuthenticated()
                || authentication instanceof AnonymousAuthenticationToken) {
            throw new IllegalStateException("No authenticated user found in security context");
        }

        Object principal = authentication.getPrincipal();
        if (principal instanceof Jwt jwt) {
            return jwt;
        }

        throw new IllegalStateException("Authenticated principal is not a Jwt");
    }
}