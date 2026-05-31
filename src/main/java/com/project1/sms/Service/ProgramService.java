package com.project1.sms.Service;

import com.project1.sms.requestDTO.ProgramRequest;
import com.project1.sms.responseDto.ProgramRespons;

import java.util.List;

public interface ProgramService {
    void createProgram(ProgramRequest request);
    List<ProgramRespons> getAllProgram();
}
