package fileManagment.file.repository;
import fileManagment.file.responseDto.StudentResultDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class Result {
	private final EntityManager entityManager;
  public  List<StudentResultDto> studentAvgAndRankPerSem( String room,int academicYear,int sem){
       Query query =  entityManager.createNativeQuery("with std_avg as(select u.user_id uid, u.first_name fName," +
			   "u.last_name lName,a.academic_year ay, a.semester sems," +
			   "sec.room room, avg(r.mark) as average " +
			   "from results r " +
			   "join users u on r.student_id = u.id " +
			   "join assessments a on r.assessment_id = a.id " +
			   "join sections sec on a.section_id = sec.id " +
			   "where a.academic_year = :year " +
			   "group by(uid,fName,lName,ay,sems,room)), " +
			   "std_rank as(select uid,fName,lName,room,sems,ay,average,rank() " +
			   "over(partition by ay,sems,room order by average desc) as std_rank  " +
			   "from std_avg) select * from std_rank sr where sems = :sem and room = :room ");

			query.setParameter("year",academicYear);
	        query.setParameter("room",room);
	        query.setParameter("sem",sem);


            List<Object[]> result = query.getResultList();
          return result.stream().map(r -> new StudentResultDto( (String) r[0],
							(String) r[1], (String) r[2], (String) r[3],(Integer) r[4],
							(Integer) r[5],(Double) r[6],(Long) r[7],null,null,null ) ).toList();


    }

	public  List<StudentResultDto> TwoSemCumulative( String room,int academicYear){
		Query query =  entityManager.createNativeQuery("with std_avg as(select u.user_id uid, u.first_name fName," +
						"u.last_name lName,a.academic_year ay, a.semester sems," +
						"sec.room room, avg(r.mark) as average " +
						"from results r " +
						"join users u on r.student_id = u.id " +
						"join assessments a on r.assessment_id = a.id " +
						"join sections sec on a.section_id = sec.id " +
						"where a.academic_year = :year " +
						"group by(uid,fName,lName,ay,sems,room)), " +
						"std_rank as(select uid,fName,lName,room,sems,ay,average,rank() " +
						"over(partition by ay,sems,room order by average desc) as std_rank  " +
						"from std_avg) select * from std_rank sr where room = :room ");

		query.setParameter("room",room);
		query.setParameter("year",academicYear);
		List<Object[]> result = query.getResultList();
		return result.stream().map(r ->  new StudentResultDto ((String) r[0],(String) r[1], (String) r[2], (String) r[3],(Integer) r[4],
				(Integer) r[5],null,null,(Double) r[6],(Long) r[7],null )).toList();



	}


	


}