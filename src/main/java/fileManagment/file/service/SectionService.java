package fileManagment.file.service;

import fileManagment.file.entity.SectionEntity;

import java.util.List;

public interface SectionService {
    SectionEntity createSection(Integer room, Integer block, Integer grade, Integer fieldCode);
    List<SectionEntity> findsSections();
    SectionEntity findSection(String room);

}
