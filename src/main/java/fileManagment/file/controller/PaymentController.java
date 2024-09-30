package fileManagment.file.controller;
import fileManagment.file.domain.Response;
import fileManagment.file.requestDto.CreatePaymentRequest;
import fileManagment.file.requestDto.PaymentRequest;
import fileManagment.file.service.PaymentService;
import fileManagment.file.service.StudentFeeService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;
import static fileManagment.file.util.RequestUtil.getResponse;
import static fileManagment.file.util.RequestUtil.getUrRI;
import static java.util.Collections.emptyMap;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping(path = {"/fee"})
@RequiredArgsConstructor
public class PaymentController{
    private final PaymentService paymentService;
    private final StudentFeeService studentFeeService;

    @GetMapping("/unpaid/all")
    public ResponseEntity<Response> getAllFee(@RequestParam("userId") String  id, HttpServletRequest request){
            var fee = paymentService.getTuition(id);
        return ResponseEntity.created(getUrRI("section/id")).body(getResponse(request, fee, "", OK));
    }

    @GetMapping("/get")
    public ResponseEntity<Response> getFeeByMonthNum(@RequestParam("userId") String  id, @RequestParam("monthNum")Integer month, HttpServletRequest request){
        var fee = paymentService.getTuitionByNumOfMonth(id,month);
        return ResponseEntity.created(getUrRI("section/id")).body(getResponse(request, fee, "", OK));
    }


    @PostMapping("/create")
    public ResponseEntity<Response> createPayment(@RequestBody CreatePaymentRequest createPaymentRequest, HttpServletRequest request) {
        studentFeeService.definePayment(createPaymentRequest.getGrade(),createPaymentRequest.getFee());
        return ResponseEntity.created(getUrRI("section/id")).body(getResponse(request, emptyMap(), "payment is created ", CREATED));

    }

    @PostMapping("/define")
    public ResponseEntity<Response> definePayment(@RequestBody CreatePaymentRequest createPaymentRequest, HttpServletRequest request) {
        studentFeeService.definePayment(createPaymentRequest.getGrade(),createPaymentRequest.getFee());
        return ResponseEntity.created(getUrRI("section/id")).body(getResponse(request, emptyMap(), "payment is created ", CREATED));

    }

    @PostMapping("/register")
    public ResponseEntity<Response> registrationPayment(@RequestBody PaymentRequest paymentDto, HttpServletRequest request) {
       var payment = paymentService.newStudentRegistrationPayment(paymentDto.getUserId());
        return ResponseEntity.created(getUrRI("section/id")).body(getResponse(request, Map.of("data",payment), "you are registered ", CREATED));

    }

    @PostMapping("/tuition/payment")
    public ResponseEntity<Response> monthTuitionPayment(@RequestBody PaymentRequest paymentDto, HttpServletRequest request) {
        var payment = paymentService.makeStudentTuitionPyament(paymentDto.getMonthNum(),paymentDto.getUserId());
        return ResponseEntity.created(getUrRI("section/id")).body(getResponse(request, Map.of("data",payment), "you are registered ", CREATED));

    }




}
