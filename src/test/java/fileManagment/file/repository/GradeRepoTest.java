package fileManagment.file.repository;

import fileManagment.file.domain.RequestContext;
import fileManagment.file.entity.GradeEntity;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class GradeRepoTest {
    @Autowired
    private  GradeRepo gradeRepo;
    @Test
    public void addTest(){
        RequestContext.setUserId(0L);
        var grade1 = GradeEntity.builder().grade(1).
                build();
        var garde2 = GradeEntity.builder().grade(2).build();
        var garde3 = GradeEntity.builder().grade(3).build();
        var garde4 = GradeEntity.builder().grade(4).build();
        var garde5 = GradeEntity.builder().grade(5).build();
        var garde6 = GradeEntity.builder().grade(6).build();
        var garde7 = GradeEntity.builder().grade(7).build();
        var garde8 = GradeEntity.builder().grade(8).build();
        var garde9 = GradeEntity.builder().grade(9).build();
        var garde10 = GradeEntity.builder().grade(10).build();
        var garde11= GradeEntity.builder().grade(11).build();
        var garde12= GradeEntity.builder().grade(12).build();

        Iterable<GradeEntity> grades = List.of(
                grade1,
                garde2,
                garde2,
                garde3,
                garde4,
                garde5,
                garde6,
                garde7,
                garde8,
                garde9,
                garde10,
                garde11,
                garde12
        );
           gradeRepo.saveAll(grades);

    }



}