package fileManagment.file.controller;

import fileManagment.file.domain.Response;
import fileManagment.file.entity.UserEntity;
import fileManagment.file.requestDto.AssignRequest;
import fileManagment.file.service.AssignService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

import static fileManagment.file.util.RequestUtil.getResponse;
import static fileManagment.file.util.RequestUtil.getUrRI;
import static java.util.Collections.emptyMap;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping(path = {"/assign"})
@RequiredArgsConstructor
public class AssignController {
   private final AssignService assignService;
    @PostMapping
    public ResponseEntity<Response> assignTeachers(@RequestBody AssignRequest assignRequest,  HttpServletRequest request){
       assignService.assignsTeacher(assignRequest.getTeacher(),assignRequest.getSection(),assignRequest.getSubject());
        return ResponseEntity.created(getUrRI("")).body(getResponse(request,emptyMap(),"Teacher is assigned",CREATED));


    }
    @GetMapping("/{room}")
    public ResponseEntity<Response> freeTeacher(@PathVariable("room") String section ,HttpServletRequest request){
        List<UserEntity> teachers = assignService.freeTeachers(section);
        return ResponseEntity.created(getUrRI("")).body(getResponse(request, Map.of("teachers",teachers),"Teacher is assigned",CREATED));


    }
}
