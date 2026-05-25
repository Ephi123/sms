package com.project1.sms.enumeration;

public enum Role {
    OWNER,
    ADMIN,
    FINANCE_OFFICER,
    TEACHER,
    STUDENT,
    REGISTRAR,
    REGISTRAR_HEAD,
    DEPARTMENT_HEAD,
    FINANCE_HEAD,
    ACADEMIC_DEAN,
    REGISTRAR_OFFICER,
    DEAN,
    VICEDEAN,
    HR;

    public String getAuthority() {
        return "ROLE_" + name();
    }
}
