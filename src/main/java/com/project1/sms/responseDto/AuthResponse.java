package com.project1.sms.responseDto;

import com.project1.sms.enumeration.Role;
import java.time.Instant;

public record AuthResponse(
        String tokenType,
        String accessToken,
        Instant expiresAt,
        String userName,
        Role role
) {
}
