package fileManagment.file.service;


import fileManagment.file.entity.AssignsEntity;
import fileManagment.file.entity.UserEntity;

import java.util.Iterator;
import java.util.List;

public interface AssignService {

    AssignsEntity assignTeacher(String userId,String sec,String sub);
    List<UserEntity> freeTeachers(String sec);
}
