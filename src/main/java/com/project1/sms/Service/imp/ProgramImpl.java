package com.project1.sms.Service.imp;

import com.project1.sms.Service.ProgramService;
import com.project1.sms.model.Program;
import com.project1.sms.repository.ProgramRepo;
import com.project1.sms.requestDTO.ProgramRequest;
import com.project1.sms.responseDto.ProgramRespons;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProgramImpl implements ProgramService {
    private final ProgramRepo programRepo;
    @Override
    public void createProgram(ProgramRequest request) {
        Program program =Program.builder().name(request.program()).
                semesterPerYear(request.semesterPerYear()).build();
        programRepo.save(program);

    }

    @Override
    public List<ProgramRespons> getAllProgram() {
        List<Program> programs = programRepo.findAll();
        return programs.stream().
                map(ProgramRespons::from).toList();

    }

}
