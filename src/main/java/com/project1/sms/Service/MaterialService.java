package com.project1.sms.Service;

import com.project1.sms.requestDTO.CreateMaterialRequest;
import com.project1.sms.requestDTO.UpdateMaterialRequest;
import com.project1.sms.responseDto.MaterialResponse;

import java.util.List;

public interface MaterialService {
    MaterialResponse postMaterial(CreateMaterialRequest request);
    List<MaterialResponse> getTeacherMaterials();
    List<MaterialResponse> getCourseOfferingMaterials(Long courseOfferingId);
    MaterialResponse updateMaterial(Long materialId , UpdateMaterialRequest request);
}
