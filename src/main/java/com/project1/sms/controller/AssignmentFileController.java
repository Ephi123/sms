package com.project1.sms.controller;

import com.project1.sms.Service.AssignmentFileService;
import com.project1.sms.response.GlobalResponse;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/assignment-files")
@RequiredArgsConstructor
public class AssignmentFileController {

    private final AssignmentFileService assignmentFileService;

    @PostMapping("/upload/{assignmentId}")
    public  ResponseEntity<GlobalResponse<?>> uploadAssignment(
            @PathVariable Long assignmentId,
            @RequestParam("file") MultipartFile file
    ) throws IOException {

        assignmentFileService
                .uploadAssignment(
                        assignmentId,
                        file
                );

        return  ResponseEntity.ok(GlobalResponse.success("Assignment uploaded successfully",null)
        );
    }

    @GetMapping("/download-all/{assignmentId}")
    public void downloadAllFiles(
            @PathVariable Long assignmentId,
            HttpServletResponse response
    ) throws IOException {

        assignmentFileService
                .downloadAllFiles(
                        assignmentId,
                        response
                );
    }
}

//Frontend Upload Example
//
//Using JavaScript:
//
//        const formData = new FormData();
//
//formData.append("file", selectedFile);
//
//await axios.post(
//   "/api/assignment-files/upload/1",
//   formData
//   );