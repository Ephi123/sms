package com.project1.sms.requestDTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateMaterialRequest {

    @NotBlank
    private String title;

    private String description;

    @NotBlank
    private String fileName;

    @NotBlank
    private String fileType;

    @NotBlank
    private String fileUrl;
}
