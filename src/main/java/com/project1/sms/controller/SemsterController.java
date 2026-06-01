package com.project1.sms.controller;

import com.project1.sms.Service.SemsterService;
import com.project1.sms.response.GlobalResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/semsters")
public class SemsterController {

    private final SemsterService semsterService;

    public SemsterController(SemsterService semsterService) {
        this.semsterService = semsterService;
    }

    @PatchMapping("/{id}")
    public ResponseEntity<GlobalResponse<Void>> updateSemster(
            @PathVariable Long id,
            @RequestParam int sem) {
        semsterService.updateSemster(id, sem);
        return ResponseEntity.ok(GlobalResponse.<Void>success("Semester updated successfully", null));
    }
}
