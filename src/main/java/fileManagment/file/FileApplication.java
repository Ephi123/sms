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
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@EnableJpaAuditing
@Transactional(rollbackOn = Exception.class)
public class FileApplication {

	public static void main(String[] args) {
		SpringApplication.run(FileApplication.class, args);
	}

	@Bean
	CommandLineRunner  commandLineRunner(SectionRepo sectionRepo, GradeRepo gradeRepo, FieldRepo fieldRepo){
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


		};

	}

}
