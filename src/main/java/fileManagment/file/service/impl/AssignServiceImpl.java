package fileManagment.file.service.impl;

import fileManagment.file.entity.AssignsEntity;
import fileManagment.file.repository.AssignTeacherRepo;
import fileManagment.file.service.AssignService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AssignServiceImpl implements AssignService {
    private final AssignTeacherRepo assignTeacherRepo;

    @Override
    public AssignsEntity assignTeacher() {
        return null;
    }
}
