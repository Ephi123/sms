package fileManagment.file;

import fileManagment.file.domain.EthiopianCalendar;
import fileManagment.file.domain.Payment;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.joda.time.DateTime;
import org.joda.time.chrono.EthiopicChronology;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
@RequiredArgsConstructor
public class Demo {
    private final EntityManager entityManager;
         public static void main(String [] args) {

//             Query query =  entityManager.createNativeQuery("with std_avg as(" +
//                     "select u.user_id uid, u.first_name fName, u.last_name lName," +
//                     "a.academic_year ay, a.semester sem, sec.room room, avg(r.mark) as average " +
//                     "from results r " +
//                     "join users u on r.student_id = u.id " +
//                     "join assessments a on r.assessment_id = a.id " +
//                     "join sections sec on a.section_id = sec.id " +
//                     "where a.academic_year = :year and r.status != 'active' " +
//                     "group by(uid,fName,lName,ay,sem,room,r.status) " +
//                     "), " +
//                     "std_rank as(" +
//                     "select uid,fName,lName,room,average," +
//                     "rank() over(partition by ay,sem,room order by average desc) as std_rank,sem " +
//                     "from std_avg) " +
//                     "select * from std_rank sr where sr.uid = :userId ");
//
//             query.setParameter("userId","DA-STU-2017-28");
//             query.setParameter("year",2017);
//             var result = query.getResultList();

             }
     }




