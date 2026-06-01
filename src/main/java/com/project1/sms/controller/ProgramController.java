package com.project1.sms.controller;

import com.project1.sms.Service.ProgramService;
import com.project1.sms.requestDTO.ProgramRequest;
import com.project1.sms.response.GlobalResponse;
import com.project1.sms.responseDto.ProgramRespons;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/programs")
public class ProgramController {

    private final ProgramService programService;

    public ProgramController(ProgramService programService) {
        this.programService = programService;
    }

    @PostMapping
    public ResponseEntity<GlobalResponse<?>> createProgram(@Valid @RequestBody ProgramRequest request) {
        programService.createProgram(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(GlobalResponse.success(HttpStatus.CREATED, "Program created successfully", null));
    }

    @GetMapping
    public ResponseEntity<GlobalResponse<List<ProgramRespons>>> getAllProgram() {
        List<ProgramRespons> programs = programService.getAllProgram();
        return ResponseEntity.ok(GlobalResponse.success("Programs fetched successfully", programs));
    }
}
