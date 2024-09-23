package fileManagment.file.service;


import fileManagment.file.entity.AssignsEntity;

public interface AssignService {

    AssignsEntity assignTeacher(String userId,String sec,String sub);
}
