package fileManagment.file.controller;

import fileManagment.file.domain.Response;
import fileManagment.file.entity.UserEntity;
import fileManagment.file.requestDto.AssessmentRequest;
import fileManagment.file.requestDto.AssignRequest;
import fileManagment.file.service.AssessmentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Map;

import static fileManagment.file.util.RequestUtil.getResponse;
import static java.util.Collections.emptyMap;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping(path = {"/assessment"})
@RequiredArgsConstructor
public class AssessmentController {
    private final AssessmentService assessmentService;

    @PutMapping
    public ResponseEntity<Response> updateAssessment(@RequestBody AssessmentRequest assessmentRequest,HttpServletRequest request){
        assessmentService.updateAssessment(assessmentRequest, assessmentRequest.getSubject(), assessmentRequest.getSection());
        return ResponseEntity.ok().body(getResponse(request,emptyMap(),"Assessment is updated",CREATED));


    }
    @GetMapping
    public ResponseEntity<Response> getAssessment(@RequestParam("sec") String section, @RequestParam("sub") String subject ,HttpServletRequest request){
               var assessments = assessmentService.getAssessments(section,subject);
        return ResponseEntity.ok().body(getResponse(request, Map.of("assessment",assessments),"",OK));


    }

    @DeleteMapping
    public ResponseEntity<Response> delete(@RequestParam("id") Long id ,HttpServletRequest request){
             assessmentService.deleteAssessment(id);
        return ResponseEntity.ok().body(getResponse(request, Map.of(),"Assessment is deleted", HttpStatus.OK));


    }

    @PostMapping
    public ResponseEntity<Response> addAssessment(@RequestBody AssessmentRequest assessmentRequest,HttpServletRequest request){
           assessmentService.addAssessment(assessmentRequest);
        return ResponseEntity.created(URI.create("")).body(getResponse(request, Map.of(),"Assessment is created", HttpStatus.OK));


    }



}
