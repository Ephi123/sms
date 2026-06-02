package com.project1.sms.enumeration;

public enum Role {
    CEO,
    ADMIN,
    FINANCE_OFFICER,
    TEACHER,
    STUDENT,
    REGISTRAR_HEAD,
    DEPARTMENT_HEAD,
    FINANCE_HEAD,
    ACADEMIC_DEAN,
    REGISTRAR_OFFICER,
    DEAN,
    VICE_DEAN,
    HR;

    public String getAuthority() {
        return "ROLE_" + name();
    }
}
