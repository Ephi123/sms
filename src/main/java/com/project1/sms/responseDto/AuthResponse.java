package com.project1.sms.responseDto;

import com.project1.sms.enumeration.Role;
import java.time.Instant;
import java.util.Set;

public record AuthResponse(
        String tokenType,
        String accessToken,
        Instant expiresAt,
        String userName,
        Set<Role> roles
) {
}
