package com.project1.sms.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.project1.sms.enumeration.Active;
import com.project1.sms.enumeration.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "user")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
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
    @Column(unique = true, nullable = false)
    private String password;

    private Role role;
    private Active isActive;

}
