package com.project1.sms.enumeration;

import lombok.Getter;

@Getter
public enum ProgramEnum {
    REGULAR("Regular"),
    WEEKEND("Weekend"),
    NIGHT("Night");
    private final String label;

    ProgramEnum(String label){
        this.label = label;
    }

}
