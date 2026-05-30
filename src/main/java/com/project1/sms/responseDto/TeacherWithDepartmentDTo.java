package com.project1.sms.responseDto;

import com.project1.sms.enumeration.Active;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public record TeacherWithDepartmentDTo(
        Long id,
        String name,
        String phoneNum,
        String depName,
        Active status

) {
}
