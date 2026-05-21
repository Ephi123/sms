package com.project1.sms.enumeration.converter;
import com.project1.sms.enumeration.ProgramEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.stream.Stream;

@Converter(autoApply = false)
public class ProgramConverter implements AttributeConverter<ProgramEnum,String> {
    @Override
    public String convertToDatabaseColumn(ProgramEnum program) {
        return program != null? program.getLabel():null;
    }

    @Override
    public ProgramEnum convertToEntityAttribute(String s) {
        if (s == null || s.isEmpty()) {
            return null;
        }

        return Stream.of(ProgramEnum.values())
                .filter(authority -> authority.getLabel().equalsIgnoreCase(s))
                .findFirst()
                .orElseThrow(IllegalAccessError::new);

    }
    }

