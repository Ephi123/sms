package fileManagment.file.repository;

import fileManagment.file.apiException.ApiException;
import fileManagment.file.domain.RequestContext;
import fileManagment.file.entity.SectionEntity;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Transactional(rollbackOn = Exception.class )
class SectionRepoTest {
    @Autowired
    private  SectionRepo sectionRepo;
    @Autowired
    private GradeRepo gradeRepo;
    @Autowired
    private  FieldRepo fieldRepo;
    @Test
    public void addSection(){
        RequestContext.setUserId(0L);
        var gradeEntity = gradeRepo.findByGrade(11).orElseThrow(() -> new ApiException(" grade not find "));
        var naturalScience = fieldRepo.findByFieldCode(1).orElseThrow(() -> new ApiException("field not found"));

        var section1 = SectionEntity.builder().block("BLOCK-1").room("ROOM-1").grade(gradeEntity).field(naturalScience).build();
        var section =sectionRepo.save(section1);
        System.out.println(section);

//       var section3 = SectionEntity.builder().block("BLOCK-1").section("ROOM-3").build();
//       var section4 = SectionEntity.builder().block("BLOCK-1").section("ROOM-4").build();
//       var section5 = SectionEntity.builder().block("BLOCK-2").section("ROOM-5").build();
//       var section6 = SectionEntity.builder().block("BLOCK-3").section("ROOM-6").build();
//       var section7 = SectionEntity.builder().block("BLOCK-4").section("ROOM-7").build();
//       var section8 = SectionEntity.builder().block("BLOCK-5").section("ROOM-8").build();

      // List<SectionEntity> sections = List.of(section1,section2,section3,section4,section5,section6,section7,section8);

    //sectionRepo.saveAll(sections);
    }

}