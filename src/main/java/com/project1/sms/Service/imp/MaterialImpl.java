package com.project1.sms.Service.imp;

import com.project1.sms.Service.MaterialService;
import com.project1.sms.apiException.ApiException;
import com.project1.sms.model.CourseOffering;
import com.project1.sms.model.Material;
import com.project1.sms.model.Teacher;
import com.project1.sms.repository.CourseOfferingRepo;
import com.project1.sms.repository.MaterialRepo;
import com.project1.sms.repository.TeacherRepo;
import com.project1.sms.requestDTO.UpdateMaterialRequest;
import com.project1.sms.responseDto.MaterialResponse;
import com.project1.sms.security.CurrentUserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;
@Service
@Transactional
@RequiredArgsConstructor
public class MaterialImpl implements MaterialService {

    private final CurrentUserService userService;
    private final CourseOfferingRepo offeringRepo;
    private final MaterialRepo materialRepo;
    private final CurrentUserService currentUserService;
    private final TeacherRepo teacherRepo;


    @Override
    public List<MaterialResponse> getTeacherMaterials() {
        Long teacherId =userService.getUserId();
       return
               materialRepo.findByTeacherIdOrderByPostedAtDesc(teacherId).
                       stream().map(MaterialResponse::from).toList();

    }

    @Override
    public List<MaterialResponse> getCourseOfferingMaterials(Long courseOfferingId) {
        return  materialRepo.findByCourseOfferingIdOrderByPostedAtDesc(courseOfferingId).
                stream().map(MaterialResponse::from).toList();

    }

    @Override
    public MaterialResponse updateMaterial(Long materialId, UpdateMaterialRequest request) {
      Material material = materialRepo.findById(materialId).orElseThrow(() -> new ApiException("material not found"));
      material.setDescription(request.getDescription());
      material.setTitle(request.getTitle());
      Material savedMaterial = materialRepo.save(material);
        return MaterialResponse.from(savedMaterial);
    }

    @Override
    public void uploadMaterial(Long offeringId, String title, String description, MultipartFile file) throws IOException {
        long MAX_SIZE = 1024 * 1024;

        if (file.getSize() > MAX_SIZE) {
            throw new ApiException(
                    "File size must not exceed 1 MB"
            );
        }

        Long userId = currentUserService.getUserId();

        Teacher teacher = teacherRepo
                .findByUserId(userId)
                .orElseThrow(() ->
                        new ApiException("Teacher not found"));

        CourseOffering offering =
                offeringRepo.findById(offeringId)
                        .orElseThrow(() ->
                                new ApiException(
                                        "Course offering not found"
                                ));

        String UPLOAD_DIR = "uploads/materials/";
        Files.createDirectories(
                Paths.get(UPLOAD_DIR)
        );

        String savedFileName =
                System.currentTimeMillis()
                        + "_"
                        + file.getOriginalFilename();

        Path filePath =
                Paths.get(
                        UPLOAD_DIR,
                        savedFileName
                );

        Files.copy(
                file.getInputStream(),
                filePath,
                StandardCopyOption.REPLACE_EXISTING
        );

        Material material =
                Material.builder()
                        .title(title)
                        .description(description)
                        .fileName(
                                file.getOriginalFilename()
                        )
                        .fileType(
                                file.getContentType()
                        )
                        .fileUrl(
                                filePath.toString()
                        )
                        .postedAt(LocalDateTime.now())
                        .teacher(teacher)
                        .courseOffering(offering)
                        .build();

        materialRepo.save(material);
    }

    @Override
    public MaterialResponse getMaterial(Long materialId) {
        Material material = materialRepo.findById(materialId).orElseThrow(() -> new ApiException(",material not found"));

        return MaterialResponse.from(material);
    }


}
