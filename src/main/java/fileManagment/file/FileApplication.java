package fileManagment.file;

import fileManagment.file.apiException.ApiException;
import fileManagment.file.domain.RequestContext;
import fileManagment.file.entity.RoleEntity;
import fileManagment.file.entity.SectionEntity;
import fileManagment.file.enumeration.Authority;
import fileManagment.file.repository.FieldRepo;
import fileManagment.file.repository.GradeRepo;
import fileManagment.file.repository.RoleRepo;
import fileManagment.file.repository.SectionRepo;
import fileManagment.file.responseDto.StudentResultDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
@EnableAsync
@EnableJpaAuditing
@Transactional(rollbackOn = Exception.class)
public class FileApplication {

	public static void main(String[] args) {
		SpringApplication.run(FileApplication.class, args);
	}

	@Bean
	CommandLineRunner  commandLineRunner(SectionRepo sectionRepo, GradeRepo gradeRepo, FieldRepo fieldRepo, EntityManager entityManager){
		return args -> {
//			RequestContext.setUserId(0L);
//			var gradeEntity = gradeRepo.findByGrade(10).orElseThrow(() -> new ApiException(" grade not find "));
//			var filed= fieldRepo.findByFieldCode(0).orElseThrow(() -> new ApiException("field not found"));
//			var section1 = SectionEntity.builder().block("BLOCK-2").room("ROOM-3").grade(gradeEntity)
//					.field(filed).build();
//			var section =sectionRepo.save(section1);
//			System.out.println(section);
//		RequestContext.setUserId(0L);
//			var userRole = new RoleEntity();
			//userRole.setName(Authority.USER.name());
//			userRole.setAuthority(Authority.USER);
//			roleRepo.save(userRole);

//			var adminRole = new RoleEntity();
//			adminRole.setName(Authority.ADMIN.name());
//			adminRole.setAuthority(Authority.ADMIN);
//			roleRepo.save(adminRole);

//
//			var superAdminRole = new RoleEntity();
//			superAdminRole.setName(Authority.SUPER_ADMIN.name());
//			superAdminRole.setAuthority(Authority.SUPER_ADMIN);
//			roleRepo.save(superAdminRole);
//
//			var managerRole = new RoleEntity();
//			managerRole.setName(Authority.MANAGER.name());
//			managerRole.setAuthority(Authority.MANAGER);
//			roleRepo.save(managerRole);
//			RequestContext.start();
//
//			Query query =  entityManager.createNativeQuery("with std_avg as(" +
//					"select u.user_id uid, u.first_name fName, u.last_name lName," +
//					"a.academic_year ay, a.semester sem, sec.room room, avg(r.mark) as average " +
//					"from results r " +
//					"join users u on r.student_id = u.id " +
//					"join assessments a on r.assessment_id = a.id " +
//					"join sections sec on a.section_id = sec.id " +
//					"where a.academic_year = :year and r.status = 'active' " +
//					"group by(uid,fName,lName,ay,sem,room,r.status) " +
//					"), " +
//					"std_rank as(" +
//					"select uid,fName,lName,room,sem,ay,average," +
//					"rank() over(partition by ay,sem,room order by average desc) as std_rank  " +
//					"from std_avg) " +
//					"select * from std_rank sr where sr.uid = :userId ");
//
//			query.setParameter("userId","DA-STU-2017-28");
//			query.setParameter("year",2017);
//            List<Object[]> result = query.getResultList();
//            List<StudentResultDto> list =result.stream().map(r -> new StudentResultDto( (String) r[0],
//							(String) r[1], (String) r[2], (String) r[3],
//							(Integer) r[4],(Integer) r[5],(Double) r[6],(Long) r[7] ) ).toList();
//
//			System.out.println(list);


		};

	}

}
