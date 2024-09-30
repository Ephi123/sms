package fileManagment.file.service;


import fileManagment.file.entity.UserEntity;

import java.util.List;

public interface AssignService {

    void assignsTeacher(String userId, String sec, String sub);
    List<UserEntity> freeTeachers(String sec);
}
