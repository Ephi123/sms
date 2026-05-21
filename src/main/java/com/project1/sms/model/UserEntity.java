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

    @NotNull
    @Builder.Default
    @Convert(converter = RolesConverter.class)
    @Column(name = "roles", nullable = false, length = 512)
    private Set<Role> roles = EnumSet.noneOf(Role.class);

    @Convert(converter = ActiveConverter.class)
    private Active isActive;


    public static class RolesConverter implements jakarta.persistence.AttributeConverter<Set<Role>, String> {
        private static final String DELIMITER = ",";

        @Override
        public String convertToDatabaseColumn(Set<Role> attribute) {
            if (attribute == null || attribute.isEmpty()) {
                return "";
            }
            return attribute.stream()
                    .map(Role::name)
                    .sorted()
                    .collect(Collectors.joining(DELIMITER));
        }

        @Override
        public Set<Role> convertToEntityAttribute(String dbData) {
            if (dbData == null || dbData.isBlank()) {
                return EnumSet.noneOf(Role.class);
            }
            return Arrays.stream(dbData.split(DELIMITER))
                    .map(String::trim)
                    .filter(value -> !value.isEmpty())
                    .map(Role::valueOf)
                    .collect(Collectors.collectingAndThen(Collectors.toSet(), roles ->
                            roles.isEmpty() ? EnumSet.noneOf(Role.class) : EnumSet.copyOf(roles)));
        }
    }


}
