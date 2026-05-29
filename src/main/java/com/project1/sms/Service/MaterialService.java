package com.project1.sms.Service;

import com.project1.sms.model.Material;
import com.project1.sms.requestDTO.CreateMaterialRequest;
import com.project1.sms.requestDTO.UpdateMaterialRequest;
import com.project1.sms.responseDto.MaterialResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface MaterialService {
    List<MaterialResponse> getTeacherMaterials();
    List<MaterialResponse> getCourseOfferingMaterials(Long courseOfferingId);
    MaterialResponse updateMaterial(Long materialId , UpdateMaterialRequest request);

    void uploadMaterial(
            Long offeringId,
            String title,
            String description,
            MultipartFile file
    ) throws IOException;

    MaterialResponse getMaterial(Long materialId);

}
