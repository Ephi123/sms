package fileManagment.file.controller;
import fileManagment.file.domain.Response;
import fileManagment.file.entity.SectionEntity;
import fileManagment.file.entity.SubjectEntity;
import fileManagment.file.requestDto.SectionRequest;
import fileManagment.file.service.SectionService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import static fileManagment.file.util.RequestUtil.getResponse;
import static fileManagment.file.util.RequestUtil.getUrRI;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping(path = {"/section"})
@RequiredArgsConstructor
public class SectionController {
    private final SectionService sectionService;

    @PostMapping
    public ResponseEntity<Response> sectionRegister(@RequestBody SectionRequest section, HttpServletRequest request) {

        var sec = sectionService.createSection(section.getRoom(), section.getBlock(), section.getGrade(), section.getFieldCode(),section.getStudentNumber());
        return ResponseEntity.created(getUrRI("section/id")).body(getResponse(request, Map.of("section", sec), "section is created ", CREATED));

    }

    @GetMapping
    public ResponseEntity<Response> getAllSection(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size, HttpServletRequest request){
        Page<SectionEntity> section = sectionService.findsSections(page,size);
            List<SectionEntity> sections = section.stream().toList();
        return ResponseEntity.created(getUrRI("section/id")).body(getResponse(request, Map.of("section",sections), "sections", OK));
    }
}