package com.project1.sms.responseDto;

import com.project1.sms.model.Section;

public record SectionResponse(
         Long id,
        String section
) {
    public static SectionResponse from(Section section){
        return new SectionResponse(
                section.getId(),
                "Section-1"+section.getSection()
        );
    }
}
