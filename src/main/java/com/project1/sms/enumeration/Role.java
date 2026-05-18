package com.project1.sms.enumeration;

public enum Role {
    ADMIN,
    FINANCE_OFFICER,
    TEACHER,
    STUDENT,
    REGISTRAR,
    REGISTRAR_HEAD,
    DEPARTMENT_HEAD,
    FINANCE_HEAD,
    ACADEMIC_DEAN;

    public String getAuthority() {
        return "ROLE_" + name();
    }
}
