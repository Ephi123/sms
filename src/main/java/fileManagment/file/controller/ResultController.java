package fileManagment.file.controller;

import fileManagment.file.domain.Response;
import fileManagment.file.service.ResultService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static fileManagment.file.util.RequestUtil.getResponse;
import static fileManagment.file.util.RequestUtil.getUrRI;
import static java.util.Collections.emptyMap;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping(path = {"/result"})
@RequiredArgsConstructor
public class ResultController {
   private final ResultService resultService;


   @GetMapping
   public ResponseEntity<Response>  getResultByAssessment(@RequestParam("sub") String subName,@RequestParam("sec") String sec, HttpServletRequest request){
          var result = resultService.getResultBySubject(subName,sec);
       return ResponseEntity.created(getUrRI("")).body(getResponse(request, result,"Student is enrolled",CREATED));

   }

   @PutMapping
   public ResponseEntity<Response>  updateResult(@RequestParam("id") Long resId, @RequestParam("wight") Double wight, HttpServletRequest request){
       resultService.updateResult(resId,wight);
       return ResponseEntity.created(getUrRI("")).body(getResponse(request, emptyMap(),"result is update",CREATED));

   }

   @GetMapping("/student")
   public ResponseEntity<Response>  getResult(@RequestParam("id") String studentId, @RequestParam("subject") String subjectName, HttpServletRequest request){
          var result = resultService.getResultById(subjectName,studentId);
       return ResponseEntity.created(getUrRI("")).body(getResponse(request, Map.of("result",result),"result is update",CREATED));

   }
   @GetMapping("/myResult")
   public ResponseEntity<Response>  getAvdAndRank(@RequestParam("id") String studentId ,HttpServletRequest request){
       var result = resultService.getAvgAndRank(studentId);
       return ResponseEntity.created(getUrRI("")).body(getResponse(request, Map.of("result",result),"result is update",CREATED));

   }

}
