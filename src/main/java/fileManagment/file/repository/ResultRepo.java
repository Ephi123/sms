package fileManagment.file.repository;
import fileManagment.file.entity.AssessmentEntity;
import fileManagment.file.entity.ResultEntity;
import fileManagment.file.responseDto.ResultResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public interface ResultRepo extends JpaRepository<ResultEntity,Long> {

    List<ResultEntity> findByAssessment(AssessmentEntity assessment);
    @Query("SELECT new fileManagment.file.responseDto.ResultResponse(st.firstName,st.lastName,st.userId,r.mark,r.id,as.id,as.assessmentName,as.wight) FROM ResultEntity r " +
            "JOIN r.assessment as " +
            "JOIN r.student st " +
            "JOIN as.subject sub " +
            "JOIN as.section sec " +
            "WHERE sub.subjectName =:subName " +
            "AND as.academicYear = :ay " +
            "AND as.semester = :sem " +
            "ORDER BY st.firstName ASC,as.wight ASC")
    Optional<List<ResultResponse>> findByAssessmentSubject(@Param("subName") String subName , @Param("ay") int academicYear,@Param("sem") int sem);


    @Query("SELECT new fileManagment.file.responseDto.ResultResponse(st.firstName,st.lastName,st.userId,r.mark,r.id,as.id,as.assessmentName,as.wight) FROM ResultEntity r " +
            "JOIN r.assessment as " +
            "JOIN r.student st " +
            "JOIN as.subject sub " +
            "JOIN as.section sec " +
            "WHERE sub.subjectName =:subName " +
            "AND as.academicYear = :ay " +
            "AND st.userId = :userId " +
            "AND as.semester = :sem " +
            "AND FUNCTION('DATE_TRUNC','second', r.createdAt) != FUNCTION('DATE_TRUNC','second',r.updateAt)")
    Optional<List<ResultResponse>> findByAssessmentSubjectAndStudentId(@Param("subName") String subName , @Param("ay") int academicYear,@Param("userId") String userId,@Param("sem") int sem);
  @Query(value = "select s.subject_name  from results r " +
          "join assessments a on r.assessment_id = a.id " +
          "join subjects s on a.subject_id = s.id " +
          "join users u on r.student_id = u.id " +
          "where r.status = 'approved' and a.academic_year = :acYear and u.user_id = :stdId" +
          "group by(s.subject_name,a.semester, u.user_id)",nativeQuery = true)
     List<Map<String, Objects>> getaProvedSubject(@Param("stdId") String stdId,int acYear);




}


