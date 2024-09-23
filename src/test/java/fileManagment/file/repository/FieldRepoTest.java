package fileManagment.file.repository;

import fileManagment.file.domain.RequestContext;
import fileManagment.file.entity.FieldEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class FieldRepoTest {
    @Autowired
    private FieldRepo fieldRepo;
    @Test
    void addField(){
        RequestContext.setUserId(0L);
        var socialScience = FieldEntity.builder().fieldCode(2).fieldName("Social Science").build();
        var naturalScience = FieldEntity.builder().fieldCode(1).fieldName("Natural Science").build();
        var notSelectedYet = FieldEntity.builder().fieldCode(0).fieldName("Not selected yet").build();

        List<FieldEntity> fields = List.of(socialScience,naturalScience,notSelectedYet);
        fieldRepo.saveAll(fields);

    }

}