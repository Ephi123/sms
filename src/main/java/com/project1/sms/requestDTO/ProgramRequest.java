package com.project1.sms.requestDTO;

import com.project1.sms.enumeration.ProgramEnum;

public record ProgramRequest(
      ProgramEnum program,
      Integer semesterPerYear
) {
}
