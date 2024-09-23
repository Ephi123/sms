package fileManagment.file.service;

import fileManagment.file.entity.SectionEntity;
import org.springframework.data.domain.Page;

import java.util.List;

public interface SectionService {
    SectionEntity createSection(Integer room, Integer block, Integer grade, Integer fieldCode);
    Page<SectionEntity> findsSections(int page,int size);
    SectionEntity findSection(String room);

}
