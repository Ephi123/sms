package com.project1.sms.controller;

import com.project1.sms.Service.SectionService;
import com.project1.sms.response.GlobalResponse;
import com.project1.sms.responseDto.SectionResponse;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sections")
public class SectionController {

    private final SectionService sectionService;

    public SectionController(SectionService sectionService) {
        this.sectionService = sectionService;
    }

    @PostMapping("/departments/{departmentId}/programs/{programId}")
    public ResponseEntity<GlobalResponse<?>> createSection(
            @PathVariable Long departmentId,
            @PathVariable Long programId) {
        sectionService.createSection(departmentId, programId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(GlobalResponse.success(HttpStatus.CREATED, "Section created successfully", null));
    }

    @GetMapping("/departments/{departmentId}/programs/{programId}")
    public ResponseEntity<GlobalResponse<List<SectionResponse>>> getSectionsByDePAndPro(
            @PathVariable Long departmentId,
            @PathVariable Long programId) {
        List<SectionResponse> sections = sectionService.getSectionsByDePAndPro(departmentId, programId);
        return ResponseEntity.ok(GlobalResponse.success("Sections fetched successfully", sections));
    }
}

