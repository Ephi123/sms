package com.project1.sms.config;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
public class ApplicationAuditAware implements AuditorAware<Long> {

    @Override
    public Optional<Long> getCurrentAuditor() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null ||
                !authentication.isAuthenticated()) {

            return Optional.of(0L);
        }

        Object principal = authentication.getPrincipal();

        if (principal instanceof Jwt jwt) {


                Long userId = jwt.getClaim("userId");

                return Optional.ofNullable(userId);
            }


        return Optional.of(0L);
    }
}