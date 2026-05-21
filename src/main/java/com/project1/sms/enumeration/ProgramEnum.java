package com.project1.sms.enumeration;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.util.stream.Stream;

@Getter
public enum ProgramEnum {
    REGULAR("Regular"),
    WEEKEND("Weekend"),
    NIGHT("Night");
    private final String label;

    ProgramEnum(String label)
    {
        this.label = label;
    }
    @JsonValue
    public String getLabel() {
        return label;
    }

    @JsonCreator
    public static ProgramEnum fromLabel(String value) {

        return Stream.of(ProgramEnum.values())
                .filter(program -> program.label.equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() ->
                        new IllegalArgumentException("Invalid program: " + value));
    }
}
