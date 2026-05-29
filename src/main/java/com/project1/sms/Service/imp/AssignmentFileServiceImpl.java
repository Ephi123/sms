package com.project1.sms.Service.imp;

import com.project1.sms.Service.AssignmentFileService;
import com.project1.sms.apiException.ApiException;
import com.project1.sms.model.Assignment;
import com.project1.sms.model.AssignmentFile;
import com.project1.sms.model.Student;
import com.project1.sms.repository.AssignmentFileRepo;
import com.project1.sms.repository.AssignmentRepo;
import com.project1.sms.repository.StudentRepo;
import com.project1.sms.security.CurrentUserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
@RequiredArgsConstructor
public class AssignmentFileServiceImpl
        implements AssignmentFileService {

    private final AssignmentFileRepo assignmentFileRepo;
    private final AssignmentRepo assignmentRepo;
    private final StudentRepo studentRepo;

    private final CurrentUserService currentUserService;

    private final String UPLOAD_DIR =
            "uploads/assignments/";

    @Override
    public void uploadAssignment(
            Long assignmentId,
            MultipartFile file
    ) throws IOException {
        long MAX_SIZE = 1024 * 1024;

        if (file.getSize() > MAX_SIZE) {
            throw  new ApiException(
                    "File size must not exceed 1 MB"
            );
        }

        Long userId = currentUserService.getUserId();

        Student student = studentRepo
                .findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("Student not found"));

        Assignment assignment = assignmentRepo
                .findById(assignmentId)
                .orElseThrow(() ->
                        new RuntimeException("Assignment not found"));

        Files.createDirectories(Paths.get(UPLOAD_DIR));

        String fileName =
                System.currentTimeMillis()
                        + "_"
                        + file.getOriginalFilename();

        Path filePath =
                Paths.get(UPLOAD_DIR, fileName);

        Files.copy(
                file.getInputStream(),
                filePath,
                StandardCopyOption.REPLACE_EXISTING
        );

        AssignmentFile assignmentFile =
                new AssignmentFile();

        assignmentFile.setUrl(filePath.toString());

        assignmentFile.setFileName(
                file.getOriginalFilename()
        );

        assignmentFile.setFileType(
                file.getContentType()
        );

        assignmentFile.setStudent(student);

        assignmentFile.setAssignment(assignment);

        assignmentFileRepo.save(assignmentFile);
    }

    @Override
    public void downloadAllFiles(
            Long assignmentId,
            HttpServletResponse response
    ) throws IOException {

        List<AssignmentFile> files =
                assignmentFileRepo
                        .findByAssignmentId(assignmentId);
        String assignmentTitle = "assignment_" + assignmentId;
        if (!files.isEmpty()) {
            // Replace spaces with underscores so the filename doesn't break in some browsers
            assignmentTitle =assignmentId+"_"+files.get(0).getAssignment().getOffering().getDepartment().getDepName().replaceAll("\\s+", "_")+

            "_"+files.get(0).getAssignment().getOffering().getProgram().getName()+"_"+"sec"+
            files.get(0).getAssignment().getOffering().getSection().getSection();

        }

        response.setContentType("application/zip");

        response.setHeader(
                HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename" + assignmentTitle + "=Submission.zip"
        );

        ZipOutputStream zipOut =
                new ZipOutputStream(
                        response.getOutputStream()
                );

        for (AssignmentFile file : files) {

            FileSystemResource resource =
                    new FileSystemResource(
                            file.getUrl()
                    );

            String zipFileName =
                    file.getStudent().getUser().getFirstName()
                            + "_"
                            + file.getFileName();

            ZipEntry zipEntry =
                    new ZipEntry(zipFileName);

            zipOut.putNextEntry(zipEntry);

            Files.copy(
                    resource.getFile().toPath(),
                    zipOut
            );

            zipOut.closeEntry();
        }

        zipOut.finish();
        zipOut.close();
    }
}