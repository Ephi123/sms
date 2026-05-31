package com.project1.sms.Service.imp;
import com.project1.sms.Service.SectionService;
import com.project1.sms.apiException.ApiException;
import com.project1.sms.model.Department;
import com.project1.sms.model.Program;
import com.project1.sms.model.Section;
import com.project1.sms.repository.DepartmentRepo;
import com.project1.sms.repository.ProgramRepo;
import com.project1.sms.repository.SectionRepo;
import com.project1.sms.responseDto.SectionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SectionImpl implements SectionService {
    private final SectionRepo sectionRepo;
    private final DepartmentRepo departmentRepo;
    private final ProgramRepo programRepo;
    @Override
    public void createSection(Long departmentId, Long programId) {
        Department department = departmentRepo.findById(departmentId).orElseThrow(() -> new ApiException("department is not found "));
        Program program = programRepo.findById(programId).orElseThrow(() -> new ApiException("program is not found"));
        List<Section> sections =sectionRepo.findByDepartmentAndProgramOrderBySectionDesc(department,program);
        if(!sections.isEmpty()){
        sectionRepo.save(new Section(
                sections.get(0).getSection()+1,
                department,
                program
        ));
    }else{
            sectionRepo.save(new Section(
                    1,
                    department,
                    program ));
        }

    }

    @Override
    public List<SectionResponse> getSectionsByDePAndPro(Long departmentId, Long programId) {
        Department department = departmentRepo.findById(departmentId).orElseThrow(() -> new ApiException("department is not found "));
        Program program = programRepo.findById(programId).orElseThrow(() -> new ApiException("program is not found"));
        List<Section> sections = sectionRepo.findByDepartmentAndProgramOrderBySectionDesc(department,program);

        return sections.stream().map(SectionResponse::from).toList();

    }


}
