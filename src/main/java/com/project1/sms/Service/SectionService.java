package com.project1.sms.Service;

import com.project1.sms.responseDto.SectionResponse;

import java.util.List;

public interface SectionService {
    void createSection(Long departmentId,Long programId);
    List<SectionResponse> getSectionsByDePAndPro(Long departmentId, Long ProgramId);
}
