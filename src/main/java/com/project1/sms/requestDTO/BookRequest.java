package com.project1.sms.requestDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record BookRequest(
        @NotBlank String title,
                  String description,
        @NotBlank String url,
                  String imageCover,
        @NotNull Long CategoryId
) {

}
