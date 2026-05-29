package com.project1.sms.responseDto;

import com.project1.sms.model.Categories;

public record CategoryResponse(
        String category,
        Long categoryId
) {

    public static CategoryResponse from(Categories categories){
        return new CategoryResponse(
                categories.getBookCategory(),
                categories.getId()
        );
    }
}
