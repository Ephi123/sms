package fileManagment.file.service;
import fileManagment.file.responseDto.ResultResponse;
import fileManagment.file.responseDto.StudentResultDto;

import java.util.List;
import java.util.Map;

public interface ResultService {
      Map<String,?> getResultBySubject(String assessmentId,String sec);
      void  updateResult(Long resultId,double res);
      List<ResultResponse> getResultById(String subjectName,String userId);
      List<StudentResultDto> getAvgAndRank(String userId);


}
