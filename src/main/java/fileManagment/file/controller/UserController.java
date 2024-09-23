package fileManagment.file.controller;

import fileManagment.file.domain.ApiAuthenticationToken;
import fileManagment.file.domain.Response;
import fileManagment.file.enumeration.Authority;
import fileManagment.file.enumeration.TypeOfUser;
import fileManagment.file.requestDto.UserRequest;
import fileManagment.file.service.IdGeneratorService;
import fileManagment.file.service.UserService;
import fileManagment.file.util.RequestUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.Map;

import static fileManagment.file.enumeration.TypeOfUser.*;
import static fileManagment.file.util.RequestUtil.*;
import static fileManagment.file.util.RequestUtil.getResponse;
import static fileManagment.file.util.RequestUtil.getUrRI;
import static java.util.Collections.emptyMap;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping(path = {"/user"})
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private  final AuthenticationManager manager;
    private final IdGeneratorService idGeneratorService;

    @PostMapping("/teacher/register")
    public ResponseEntity<Response> saveUser(@RequestBody @Valid UserRequest user, HttpServletRequest request){
      userService.createUser(user.getFirstName(),user.getLastName(),user.getEmail(),Authority.TEACHER.name(), user.getAge(),user.getAddress(),user.getGender(),idGeneratorService.getUserId(EMPLOYEE.getValue()));
      return ResponseEntity.created(getUrRI("")).body(getResponse(request,emptyMap(),"Accounted created check your email to enable your account",CREATED));


    }


    @GetMapping("/verify/account")
    public ResponseEntity<Response> verify(@RequestParam("token") String token,HttpServletRequest request){
        userService.verifyUser(token);
        return ResponseEntity.ok().body(getResponse(request,emptyMap(),"you are verified",OK));
    }




    }
