package fileManagment.file.repository;
import fileManagment.file.responseDto.StudentResultDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
@RequiredArgsConstructor
public class Result {

    EntityManager entityManager;
  public  List<StudentResultDto> studentAvgAndRank( String userId,int academicYear){
       Query query =  entityManager.createNativeQuery("with std_avg as(" +
					"select u.user_id uid, u.first_name fName, u.last_name lName," +
					"a.academic_year ay, a.semester sem, sec.room room, avg(r.mark) as average " +
					"from results r " +
					"join users u on r.student_id = u.id " +
					"join assessments a on r.assessment_id = a.id " +
					"join sections sec on a.section_id = sec.id " +
					"where a.academic_year = :year and r.status = 'active' " +
					"group by(uid,fName,lName,ay,sem,room,r.status) " +
					"), " +
					"std_rank as(" +
					"select uid,fName,lName,room,sem,ay,average," +
					"rank() over(partition by ay,sem,room order by average desc) as std_rank  " +
					"from std_avg) " +
					"select * from std_rank sr where sr.uid = :userId ");

			query.setParameter("userId",userId);
			query.setParameter("year",academicYear);
            List<Object[]> result = query.getResultList();
          return result.stream().map(r -> new StudentResultDto( (String) r[0],
							(String) r[1], (String) r[2], (String) r[3],
							(Integer) r[4],(Integer) r[5],(Double) r[6],(Long) r[7] ) ).toList();


    }

}