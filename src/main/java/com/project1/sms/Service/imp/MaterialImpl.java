package com.project1.sms.Service.imp;

import com.project1.sms.Service.MaterialService;
import com.project1.sms.apiException.ApiException;
import com.project1.sms.model.CourseOffering;
import com.project1.sms.model.Material;
import com.project1.sms.model.Teacher;
import com.project1.sms.repository.CourseOfferingRepo;
import com.project1.sms.repository.MaterialRepo;
import com.project1.sms.repository.TeacherRepo;
import com.project1.sms.requestDTO.CreateMaterialRequest;
import com.project1.sms.requestDTO.UpdateMaterialRequest;
import com.project1.sms.responseDto.MaterialResponse;
import com.project1.sms.security.CurrentUserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
@Service
@Transactional
@RequiredArgsConstructor
public class MaterialImpl implements MaterialService {

    private final CurrentUserService userService;
    private final CourseOfferingRepo offeringRepo;
    private final MaterialRepo materialRepo;
    private TeacherRepo teacherRepo;
    @Override
    public MaterialResponse postMaterial(CreateMaterialRequest request) {
       Long teacherId = userService.getUserId();
       Teacher teacher = teacherRepo.findById(teacherId).orElseThrow(() -> new ApiException("teacher is not found"));
        CourseOffering offering = offeringRepo.findById(request.getCourseOfferingId()).orElseThrow(() -> new ApiException("course offering not found "));

       Material material = Material.builder().title(request.getTitle())
                .description(request.getDescription()).
                fileName(request.getFileName()).
                fileType(request.getFileType()).
                fileUrl(request.getFileUrl()).
                postedAt(LocalDateTime.now()).
                teacher(teacher).
                courseOffering(offering).
                build();

        Material savedMaterial=materialRepo.save(material);

        return MaterialResponse.from(savedMaterial);
    }

    @Override
    public List<MaterialResponse> getTeacherMaterials() {
        Long teacherId =userService.getUserId();
        Teacher teacher = teacherRepo.findById(teacherId).orElseThrow(() -> new ApiException("teacher is not found"));
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
      material.setFileUrl(request.getFileUrl());
      material.setFileType(request.getFileType());
      material.setFileName(request.getFileName());
      Material savedMaterial = materialRepo.save(material);
        return MaterialResponse.from(savedMaterial);
    }


}
