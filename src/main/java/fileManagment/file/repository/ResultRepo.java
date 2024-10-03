package fileManagment.file.repository;

import fileManagment.file.entity.AssessmentEntity;
import fileManagment.file.entity.ResultEntity;
import fileManagment.file.responseDto.ResultResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;
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
            "AND r.status = :status " +
            "ORDER BY st.firstName ASC,as.wight ASC")
    Optional<List<ResultResponse>> findByAssessmentSubject(@Param("subName") String subName , @Param("ay") int academicYear,  @Param("status") String status);


    @Query("SELECT new fileManagment.file.responseDto.ResultResponse(st.firstName,st.lastName,st.userId,r.mark,r.id,as.id,as.assessmentName,as.wight) FROM ResultEntity r " +
            "JOIN r.assessment as " +
            "JOIN r.student st " +
            "JOIN as.subject sub " +
            "JOIN as.section sec " +
            "WHERE sub.subjectName =:subName " +
            "AND as.academicYear = :ay " +
            "AND r.status = :status " +
            "AND st.userId = :userId " +
            "AND FUNCTION('DATE_TRUNC','second', r.createdAt) != FUNCTION('DATE_TRUNC','second',r.updateAt)")
    Optional<List<ResultResponse>> findByAssessmentSubjectAndStudentId(@Param("subName") String subName , @Param("ay") int academicYear, @Param("status") String status,@Param("userId") String userId);

}


