package com.project1.sms.enumeration.converter;

import com.project1.sms.enumeration.Role;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class RoleConverter implements AttributeConverter<Role, String> {


    @Override
    public String convertToDatabaseColumn(Role role) {
        return role != null ? role.getCode(): null;
    }

    @Override
    public Role convertToEntityAttribute(String dbData) {
        return dbData != null? Role.fromCode(dbData): null;
    }

}
