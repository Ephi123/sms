package com.project1.sms.Service;

import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface AssignmentFileService {

    void uploadAssignment(
            Long assignmentId,
            MultipartFile file
    ) throws IOException;

    void downloadAllFiles(
            Long assignmentId,
            HttpServletResponse response
    ) throws IOException;
}