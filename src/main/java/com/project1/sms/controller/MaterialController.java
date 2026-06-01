package com.project1.sms.controller;

import com.project1.sms.Service.MaterialService;
import com.project1.sms.response.GlobalResponse;
import com.project1.sms.responseDto.MaterialResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/materials")
@RequiredArgsConstructor
public class MaterialController {

    private final MaterialService materialService;

    @PostMapping("/upload/{offeringId}")
    public ResponseEntity<GlobalResponse<?>> uploadMaterial(
            @PathVariable Long offeringId,
            @RequestParam String title,
            @RequestParam String description,
            @RequestParam MultipartFile file
    ) throws IOException {

        materialService.uploadMaterial(
                offeringId,
                title,
                description,
                file
        );

        return ResponseEntity.ok( GlobalResponse.success("Material uploaded successfully",null));
    }

    @GetMapping("/offering/{offeringId}")
    public ResponseEntity<GlobalResponse<List<MaterialResponse>>>
    getMaterialsByOffering(
            @PathVariable Long offeringId
    ) {
       List<MaterialResponse> materials= materialService.getCourseOfferingMaterials(offeringId);
        return ResponseEntity.ok(GlobalResponse.success("material is sent", materials));
    }



    @GetMapping("/download/{materialId}")
    public ResponseEntity<Resource>
    downloadMaterial(
            @PathVariable Long materialId
    ) {

        MaterialResponse material =
                materialService.getMaterial(materialId);

        FileSystemResource resource =
                new FileSystemResource(
                        material.fileUrl()
                );

        return ResponseEntity.ok()
                .contentType(
                        MediaType.APPLICATION_OCTET_STREAM
                )
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\""
                                + material.fileName()
                                + "\""
                )
                .body(resource);
    }
}