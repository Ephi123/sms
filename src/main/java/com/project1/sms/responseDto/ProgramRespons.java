package com.project1.sms.responseDto;

import com.project1.sms.enumeration.ProgramEnum;
import com.project1.sms.model.Program;
import jakarta.validation.constraints.NotNull;

public record ProgramRespons(
        @NotNull Long id,
        @NotNull ProgramEnum program,
        @NotNull Integer semPerYear
) {
    public static ProgramRespons from(Program program){
        return new ProgramRespons(
               program.getId(),
               program.getName(),
               program.getSemesterPerYear()
        );
    }
}
