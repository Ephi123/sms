package com.project1.sms.enumeration;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

public enum Role {
    ADMIN("ADM","Admin"),
    FINANCE_OFFICER("FOF","Finance officer"),
    TEACHER("TCH","Teacher"),
    STUDENT("STD","Student"),
    REGISTRAR ("REG","Registrar"),
    REGISTRAR_HEAD("RHD","Registrar Head"),
    DEPARTMENT_HEAD("DHD","department head"),
    FINANCE_HEAD("FAD","Finance Admin"),
    ACADEMIC_DEAN("ADN","Academic Dean");

    @Getter
    private final  String code;
    private final String label;

    Role(String code, String label){
        this.code = code;
        this.label = label;
    }

    @JsonValue
    public String getLabel(){
        return label;
    }

    @JsonCreator
    public static Role fromCode(String code){
        for(Role role : Role.values()){
            if(role.code.equalsIgnoreCase(code))
                return role;
        }
        throw new IllegalArgumentException("Invalid Role code: "+ code);
    }
//spring security
    public String getAuthority(){
        return "ROLE_"+this.name();
    }
}
