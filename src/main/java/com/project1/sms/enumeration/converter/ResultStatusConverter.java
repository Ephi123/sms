package com.project1.sms.enumeration.converter;

import com.project1.sms.enumeration.ResultStatus;
import com.project1.sms.enumeration.StudentStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.stream.Stream;

@Converter
public class ResultStatusConverter implements AttributeConverter<ResultStatus,String> {
    @Override
    public String convertToDatabaseColumn(ResultStatus resultStatus) {
        return resultStatus != null? resultStatus.getLabel():null;
    }

    @Override
    public ResultStatus convertToEntityAttribute(String s) {
        if (s == null || s.isEmpty()) {
            return null;
        }

        return Stream.of(ResultStatus.values())
                .filter(authority -> authority.getLabel().equalsIgnoreCase(s))
                .findFirst()
                .orElseThrow(IllegalAccessError::new);
    }
}
