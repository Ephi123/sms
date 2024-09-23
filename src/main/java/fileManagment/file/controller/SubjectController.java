package fileManagment.file.controller;


import fileManagment.file.domain.Response;
import fileManagment.file.entity.SubjectEntity;

import fileManagment.file.requestDto.SubjectRequest;
import fileManagment.file.service.SubjectService;
import jakarta.servlet.http.HttpServletRequest;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

import static fileManagment.file.util.RequestUtil.getResponse;
import static fileManagment.file.util.RequestUtil.getUrRI;
import static org.springframework.http.HttpStatus.CREATED;
@RestController
@RequestMapping(path = {"/subject"})
@RequiredArgsConstructor
public class SubjectController {
    private final SubjectService subjectService;
    @PostMapping("/register")
    public ResponseEntity<Response> sectionRegister(@RequestBody  SubjectRequest subjectRequest, HttpServletRequest request) {
        subjectService.saveSubject(subjectRequest.getSubject(),subjectRequest.getGrade(),subjectRequest.getField());
        return ResponseEntity.created(getUrRI("section/id")).body(getResponse(request, Map.of(), "Subject is created ", CREATED));

    }

    @GetMapping
    public ResponseEntity<Response> getAllSubject(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size, HttpServletRequest request){
           Page<SubjectEntity> subjects =subjectService.getAllSubject(page,size);
        return ResponseEntity.created(getUrRI("section/id")).body(getResponse(request, Map.of("subjects",subjects), "Subject is created ", CREATED));
    }
}

