package com.project1.sms.security;

import com.project1.sms.enumeration.Active;
import com.project1.sms.enumeration.Role;
import com.project1.sms.model.UserEntity;
import com.project1.sms.repository.UserRepo;

import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(@NonNull String username) throws UsernameNotFoundException {
        UserEntity user = userRepo.findByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        boolean active = user.getIsActive() == Active.ACTIVE;

        return new User(
                user.getUserName(),
                user.getPassword(),
                active, // enabled
                true,   // accountNonExpired
                true,   // credentialsNonExpired
                active, // accountNonLocked
                user.getRoles().stream()
                        .map(Role::getAuthority)
                        .map(org.springframework.security.core.authority.SimpleGrantedAuthority::new)
                        .collect(Collectors.toUnmodifiableSet())
        );
    }
}
