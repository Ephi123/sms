package com.project1.sms.enumeration.converter;

import com.project1.sms.enumeration.StudentStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.stream.Stream;

@Converter(autoApply = true)
public class StatusConverter implements AttributeConverter<StudentStatus,String> {
    @Override
    public String convertToDatabaseColumn(StudentStatus studentStatus) {
        return studentStatus != null? studentStatus.getLabel():null;
    }

    @Override
    public StudentStatus convertToEntityAttribute(String s) {
        if (s == null || s.isEmpty()) {
            return null;
        }

        return Stream.of(StudentStatus.values())
                .filter(authority -> authority.getLabel().equalsIgnoreCase(s))
                .findFirst()
                .orElseThrow(IllegalAccessError::new);
    }
}
