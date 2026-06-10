package com.project1.sms.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.project1.sms.enumeration.Active;
import com.project1.sms.enumeration.Role;
import com.project1.sms.enumeration.converter.ActiveConverter;
import com.project1.sms.enumeration.converter.ProgramConverter;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "user")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@Builder
public class UserEntity extends Auditable {
    @NotNull
    @Column(unique = true, nullable = false)
    private String userId;
    @NotNull
    private String firstName;
    @NotNull
    private String midlName;
    @NotNull
    private String lastName;
    @Column(unique = true)
    private String phone;

    @Column(unique = true)
    private String email;

    private String imageUrl;
    @NotNull
    @Column(unique = true, nullable = false)
    private String userName;
    @NotNull
    @Column(nullable = false)
    private String password;

    @Builder.Default
    private Boolean firstLogin = true;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id")
    )
    @Column(name = "role")
    private Set<Role> roles;

    @Convert(converter = ActiveConverter.class)
    @Builder.Default
    private Active isActive = Active.ACTIVE;




}
