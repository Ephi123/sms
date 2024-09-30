package fileManagment.file.controller;
import fileManagment.file.domain.Response;
import fileManagment.file.entity.EnrolEntity;
import fileManagment.file.enumeration.Authority;
import fileManagment.file.requestDto.EnrolRequest;
import fileManagment.file.service.EnrolService;
import fileManagment.file.service.IdGeneratorService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static fileManagment.file.enumeration.TypeOfUser.STUDENT;
import static fileManagment.file.util.RequestUtil.getResponse;
import static fileManagment.file.util.RequestUtil.getUrRI;
import static java.util.Collections.emptyMap;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping(path = {"/student"})
@RequiredArgsConstructor
public class EnrolController {
    private final EnrolService enrolService;
    private final IdGeneratorService idGeneratorService;
    @PostMapping
    public ResponseEntity<Response> saveStudent(@RequestBody  EnrolRequest enrol, HttpServletRequest request){
        enrolService.register(enrol.getFirstName(),enrol.getLastName(),enrol.getEmail(),Authority.STUDENT.name(),enrol.getAge(),enrol.getAddress(),enrol.getGender(),idGeneratorService.getUserId(STUDENT.getValue()),enrol.getGrade(),enrol.getField());
        return ResponseEntity.created(getUrRI("")).body(getResponse(request,emptyMap(),"Student is enrolled",CREATED));

    }

    @GetMapping
    public ResponseEntity<Response> saveStudent(HttpServletRequest request){
       // List<EnrolEntity> enrolEntities =enrolService.registeredStudents();
        return ResponseEntity.created(getUrRI("")).body(getResponse(request, Map.of(),"Student is enrolled",CREATED));

    }

    @GetMapping("/new")
    public ResponseEntity<Response> getNewStudentRegistrationFee(HttpServletRequest request){
                 var newStudent = enrolService.getRegistrationFee();
        return ResponseEntity.created(getUrRI("")).body(getResponse(request, Map.of("New Student",newStudent),"Student is enrolled",CREATED));

    }


}
