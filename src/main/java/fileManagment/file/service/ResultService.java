package fileManagment.file.service;
import fileManagment.file.entity.SubjectStatusEntity;
import fileManagment.file.responseDto.ResultResponse;
import fileManagment.file.responseDto.StudentResultDto;

import java.util.List;
import java.util.Map;

public interface ResultService {
      Map<String,?> getResultBySubject(String assessmentId,String sec);
      void  updateResult(Long resultId,double res);
      List<ResultResponse> getResultById(String subjectName,String userId);
      Map<String,?> getAvgAndRank(String userId);
      void teacherChangeStatus(String subName,String sec);
      void unapprovedStatus(String subName,String sec);
      void approvedStatus(String subName,String sec,Long teacherId);
     List<SubjectStatusEntity> getSubjectWthWaitingStatus();

}
