package com.project1.sms.requestDTO;

import com.project1.sms.enumeration.Role;

import java.util.Set;

public record RoleRequest(
        Set<Role> roles
) {
}
