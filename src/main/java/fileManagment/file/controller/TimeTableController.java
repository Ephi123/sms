package fileManagment.file.controller;

import fileManagment.file.domain.Response;
import fileManagment.file.requestDto.AssessmentRequest;
import fileManagment.file.requestDto.TimeTableRequest;
import fileManagment.file.service.TimeTableService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Map;

import static fileManagment.file.util.RequestUtil.getResponse;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping(path = {"/timetable"})
@RequiredArgsConstructor
public class TimeTableController {
    private final TimeTableService timeTableService;
    @GetMapping
    public ResponseEntity<Response> getAssessment(@RequestParam("sec") String section, HttpServletRequest request){
        var teacher = timeTableService.freeTeachers(section);
        return ResponseEntity.ok().body(getResponse(request, Map.of("Teachers",teacher),"",OK));


    }
    @PostMapping
    public ResponseEntity<Response> addAssessment(@RequestBody TimeTableRequest timeTableRequest, HttpServletRequest request){
         timeTableService.addToTimeTable(timeTableRequest.getDay(),timeTableRequest.getPeriod(),timeTableRequest.getTeacher(),timeTableRequest.getSection());
        return ResponseEntity.created(URI.create("")).body(getResponse(request, Map.of(),"Assessment is created", HttpStatus.OK));


    }
}
