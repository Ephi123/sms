package fileManagment.file.controller;

import fileManagment.file.domain.Response;
import fileManagment.file.enumeration.Authority;
import fileManagment.file.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static fileManagment.file.util.RequestUtil.getResponse;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping(path = {"/teacher"})
@RequiredArgsConstructor
public class TeacherController {
    private final UserService userService;
    @GetMapping
    public ResponseEntity<Response> allTeacher(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size,  HttpServletRequest request){
           var teacher = userService.allUsers(Authority.TEACHER.name(), page,size).get().toList();
        return ResponseEntity.ok().body(getResponse(request, Map.of("teachers",teacher),"you are verified",OK));
    }

}
