package com.project1.sms.enumeration.converter;

import com.project1.sms.enumeration.Active;
import com.project1.sms.enumeration.Role;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.stream.Stream;

@Converter(autoApply = true)
public class ActiveConverter implements AttributeConverter<Active, String> {
    @Override
    public String convertToDatabaseColumn(Active active) {
        return active != null? active.getLabel():null;
    }

    @Override
    public Active convertToEntityAttribute(String s) {
        if (s == null || s.isEmpty()) {
            return null;
        }

        return Stream.of(Active.values())
                .filter(authority -> authority.getLabel().equalsIgnoreCase(s))
                .findFirst()
                .orElseThrow(IllegalAccessError::new);

    }
}
