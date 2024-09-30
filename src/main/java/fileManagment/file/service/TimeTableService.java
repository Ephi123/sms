package fileManagment.file.service;

import fileManagment.file.entity.UserEntity;

import java.util.List;

public interface TimeTableService {
     void addToTimeTable(String day,int period,String teacherId,String sec);
     List<UserEntity> freeTeachers(String sec);
}
