package fileManagment.file.service.impl;

import fileManagment.file.apiException.ApiException;
import fileManagment.file.domain.Payment;
import fileManagment.file.entity.*;
import fileManagment.file.repository.*;
import fileManagment.file.service.PaymentService;
import fileManagment.file.util.RequestUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.joda.time.chrono.EthiopicChronology;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

import static fileManagment.file.constant.Constant.ACADEMIC_YEAR;
import static fileManagment.file.util.PaymentUtil.*;
import static fileManagment.file.util.RequestUtil.handleErrorResponse;
import static org.springframework.http.HttpStatus.BAD_REQUEST;


@Service
@Transactional(rollbackOn = Exception.class)
@RequiredArgsConstructor
@Slf4j
public class PaymentServiceImpl implements PaymentService {
    private final PaymentCollectionRepo paymentCollectionRepo;
    private final StudentFeeRepo studentFeeRepo;
    private final NextPaymentRepo nextPaymentRepo;
    private final PenaltyRepo penaltyRepo;
    private final HttpServletResponse response;
    private final HttpServletRequest request;
    private final EnrolRepository enrolRepository;
   private final RegistrationRepo registrationRepo;
   private final UserRepo userRepo;



    @Override
    @PreAuthorize("hasRole('USER')")
    public Map<?, ?> getTuition(String userId) {
        int garde = grade1.apply(userId, enrolRepository);
        int nextMonth = getNextMonth(userId);
        int monthPayment = getMonthTuition(garde);
        PenaltyEntity penalty = penaltyRepo.findByPenaltyId("penalty");
        DateTime currentDate = DateTime.now(EthiopicChronology.getInstance());
        return getTotalPayment(penalty, currentDate, nextMonth, monthPayment);

    }

    @Override
    @PreAuthorize("hasRole('USER')")
    public Map<?, ?> getTuitionByNumOfMonth(String userId, int numOfMonth) {
        var enrolEntity = enrolRepository.findByStudentId(userId, ACADEMIC_YEAR).orElseThrow(() -> new ApiException("user Is not found"));
        var student = enrolEntity.getStudents().getFirst();
        int garde = grade1.apply(userId, enrolRepository);
        int nextMonth = getNextMonth(userId);
        if(nextMonth == 10){
            RequestUtil.handleErrorResponse(request,response, new ApiException("all month fee tuition is already Payed"), BAD_REQUEST);
            throw new ApiException("ll month fee tuition is already Payed");
         }
        if(nextMonth + numOfMonth > 10){
            RequestUtil.handleErrorResponse(request,response, new ApiException(nextMonth + " already payed, The rest Payment is " + (10 - nextMonth) ), BAD_REQUEST);
            throw new ApiException(nextMonth + " Months already payed, The rest Payment is " + (10 - nextMonth));
        }

        int monthPayment = getMonthTuition(garde);
        PenaltyEntity penalty = penaltyRepo.findByPenaltyId("penalty");
        DateTime currentDate = DateTime.now(EthiopicChronology.getInstance());
        return getFeeByMonth(penalty, currentDate, numOfMonth, nextMonth, monthPayment,student );
    }

    @Override
    @PreAuthorize("hasRole('USER')")
    public Map<?,?> newStudentRegistrationPayment(String UserId) {
        var nextPayment = getNextMonthEntity(UserId);
             nextPayment.setMonth(1);
           var enrolEntity = enrolRepository.findByStudentId(UserId, ACADEMIC_YEAR).orElseThrow(() -> new ApiException("user Is not found"));
                 enrolEntity.setIsActive(true);
            int grade  = enrolEntity.getGrades().getFirst().getGrade();
            int fee = getMonthTuition(grade);
            var student = enrolEntity.getStudents().getFirst();
            int registrationFee = registrationFee();
            int total = fee + registrationFee;
         var paymentInfo = paymentCollectionRepo.save(paymentCollectionBuilder(student,total));
        nextPaymentRepo.save(nextPayment);
        enrolRepository.save(enrolEntity);

      return Map.of(
              "Date",paymentInfo.getCreatedAt(),
              "Id",student.getUserId(),
              "Name",student.getFirstName() + " "+ student.getLastName(),
              "MonthFee",fee,
              "RegistrationFee",registrationFee,
              "total",total
              );
    }

    @Override
    @PreAuthorize("hasRole('USER')")
    public Map<?, ?> makeStudentTuitionPyament(int monthNum, String userId) {
        var map = getTuitionByNumOfMonth(userId,monthNum);
        var nextPayment = getNextMonthEntity(userId);
           nextPayment.setMonth(monthNum + nextPayment.getMonth());
        var enrolEntity = enrolRepository.findByStudentId(userId, ACADEMIC_YEAR).orElseThrow(() -> new ApiException("user Is not found"));
        var student = enrolEntity.getStudents().getFirst();
        var paymentInfo = paymentCollectionRepo.save(paymentCollectionBuilder(student,(double)map.get("total")));

        List<Payment> list = (List<Payment>) map.get("payment");
        StringBuilder months = new StringBuilder();
        int i= 0;
        for(Payment payment:list){
            i++;
            months.append(payment.getMonth());
            if(i != list.size())
                 months.append(", ");
        }
        return Map.of(
                "Date",paymentInfo.getCreatedAt(),
                "Id",student.getUserId(),
                "Name",student.getFirstName() + " "+ student.getLastName(),
                "Months", months.toString(),
                "total",(double)map.get("total")
        );


    }

    @Override
    @PreAuthorize("hasRole('USER')")//fiance officer
    public Map<?, ?> sinorStudentRegistration(String userId) {
        return Map.of();
    }


    private static final BiFunction<String, EnrolRepository, Integer> grade1 = (userId, enrolRepository1) -> enrolRepository1.findGradeByStudentId(userId, ACADEMIC_YEAR).orElseThrow(() -> new ApiException("grade is not found"));

    private int getNextMonth(String userId) {
        return nextPaymentRepo.findByStudentId(userId).orElseThrow(() -> {
            handleErrorResponse(request, response, new ApiException("student is not registered"), BAD_REQUEST);
            return new ApiException("student is not found");
        });


    }

    private int getMonthTuition(int grade) {
        return studentFeeRepo.findFeeByGrade(grade, ACADEMIC_YEAR).orElseThrow(() -> {
            handleErrorResponse(request, response, new ApiException("payment  is not defined for this grade"), BAD_REQUEST);
            return new ApiException("student is not found");
        });
    }

    private int registrationFee(){
        RegistrationEntity  registration = registrationRepo.findById("reg").orElseThrow(() -> new ApiException("registration fee is not found"));
     return registration.getRegistrationFee();
    }

    private NextPaymentEntity getNextMonthEntity(String userId) {
        return nextPaymentRepo.findNextPaymentByStudentId(userId).orElseThrow(() -> {
            handleErrorResponse(request, response, new ApiException("student is not registered"), BAD_REQUEST);
            return new ApiException("student is not found");
        });

    }
    private UserEntity getStudent(String userId){
        return userRepo.findByUserId(userId).orElseThrow(() -> new ApiException("student is not found"));

    }

}