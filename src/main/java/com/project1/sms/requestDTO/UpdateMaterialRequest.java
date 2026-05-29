package com.project1.sms.requestDTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateMaterialRequest {

    @NotBlank
    private String title;

    private String description;

}
