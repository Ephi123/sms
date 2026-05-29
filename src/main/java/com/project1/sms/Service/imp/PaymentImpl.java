package com.project1.sms.Service.imp;

import com.project1.sms.Service.PaymentService;

import com.project1.sms.apiException.ApiException;
import com.project1.sms.domain.EthiopianCalendar;
import com.project1.sms.dto.MonthlyPaymentReportDTO;
import com.project1.sms.enumeration.FinanceOfficerStatus;
import com.project1.sms.enumeration.StudentStatus;
import com.project1.sms.model.*;
import com.project1.sms.repository.*;
import com.project1.sms.responseDto.FinanceOfficerPaymentSummaryResponse;
import com.project1.sms.responseDto.MonthPaymentResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@RequiredArgsConstructor
@Transactional(rollbackOn = Exception.class)
@Service
public class PaymentImpl implements PaymentService {
    private final PaymentRepo paymentRepo;
    private final StudentRepo studentRepo;
    private final EnrollRepo enrollRepo;
    private final PaymentScallRepo paymentScallRepo;
    private final CourseOfferingRepo courseOfferingRepo;
    private final CurrentSemRepo currentSemRepo;
    private final AssessmentRepo assessmentRepo;
    private final AssessmentResultRepo assessmentResultRepo;
    private final GradeRepo gradeRepo;

    @Override
    public Map<String, Object> getPaymentDetailOfStudent(String userId) {

        Student  student = studentRepo.findByUserUserId(userId).
                orElseThrow(()-> new ApiException("Student Not Found"));
        CurrentSem currentSem =currentSemRepo.findTopByOrderByIdDesc().orElseThrow(() -> new ApiException("sem is not defined"));


    Enrollment enrollment =enrollRepo.getEnrolledStudent(
                EthiopianCalendar.ethiopianYear(),
                currentSem.getCurrentSem(),
                student.getDepartment().getDepName(),
                student.getCurrentYear(),
                student.getProgram().getName(),
                student.getSection().getSection(),
                userId
        ).orElse(null);

    if(enrollment != null){
            List<Payment> payments= paymentRepo.findByStudentAndSemAndAcademicYearOrderByMonthAsc(student,student.getCurrentSem(),2018);
            List<MonthPaymentResponse> paymentDetails =payments.stream().map(MonthPaymentResponse::from).toList();

        return Map.ofEntries( Map.entry("fullName", enrollment.getStudent().getUser().getFirstName()+enrollment.getStudent().getUser().getMidlName()),
               Map.entry( "department",enrollment.getStudent().getDepartment().getDepName()),
              Map.entry(  "Year",enrollment.getStudent().getCurrentYear().toString()),
                Map.entry("program",enrollment.getCourseOffering().getProgram().getName().getLabel()),
                Map.entry("academicYear",enrollment.getCourseOffering().getAcademicYear().toString()),
                Map.entry("paymentDetail",paymentDetails),
                Map.entry("UnpaidMonths",getUnpaidMonths(student) == 0?"Completed":getUnpaidMonths(student)),
                Map.entry("mothPayment",getUnpaidMonths(student) == 0?"":calculateMonthPayment(student)),
               Map.entry( "totalUnpaidFee",getUnpaidMonths(student) == 0? "" : getUnpaidMonths(student) * calculateMonthPayment(student)),
                Map.entry("url",getUnpaidMonths(student) == 0?"" :"url to pay with student Id")



        );


    }



        return Map.ofEntries( Map.entry("fullName", student.getUser().getFirstName()+ " " + student.getUser().getMidlName()),
                Map.entry("department",student.getDepartment().getDepName()),
              Map.entry(  "Year",student.getCurrentYear()+""),
               Map.entry( "program",student.getProgram().getName().getLabel()),
                Map.entry("academicYear", EthiopianCalendar.ethiopianYear()),
              Map.entry(  "paymentDetail"," "),
                Map.entry("UnpaidMonths",""),
                Map.entry("mothPayment",calculateMonthPayment(student)),
                Map.entry("totalUnpaidFee",""),
                Map.entry("url","url to pay with student Id to enroll student"),
                Map.entry("Message","UnEnrolled")

        );

    }

    @Override
    public Map<String, Object> makeFirstMonthFeeAndEnrollment(String studentId) {

         boolean isStudentEnrol = enrollRepo.existsByStudentUserUserId(studentId);
        Student student = studentRepo.findByUserUserId(studentId).
                orElseThrow(() -> new ApiException("student is not found"));

        int currentSem = currentSemRepo.findAll().get(0).getCurrentSem();
           if(isStudentEnrol && currentSem==1){
               student.setCurrentYear(student.getCurrentYear()+1);
               student.setCurrentSem(currentSem);
               student = studentRepo.save(student);

           } else if (isStudentEnrol && currentSem>1) {
               student.setCurrentSem(currentSem);
               student = studentRepo.save(student);
           }
           if(!isStudentEnrol){
               student.setStudentStatus(StudentStatus.ACTIVE);
               student = studentRepo.save(student);
           }


        List<CourseOffering> offerings = courseOfferingRepo.findByAcademicYearAndStudyYearAndSemAndSectionAndProgramAndDepartment(
                EthiopianCalendar.ethiopianYear(),
                student.getCurrentYear(),
                student.getCurrentSem(),
                student.getSection(),
                student.getProgram(),
                student.getDepartment()
        );
        if(offerings == null)
            throw new ApiException("course not found");
        Student finalStudent = student;
        offerings.forEach(courseOffering -> {
            Enrollment enrollment = new Enrollment();
            enrollment.setStudent(finalStudent);
            enrollment.setCourseOffering(courseOffering);
             enrollRepo.save(enrollment);
            assessmentRepo.findByCourseOffering(courseOffering).forEach(assessment ->{
                assessmentResultRepo.save(new AssessmentResult(finalStudent,assessment,null));

                    }

                    );
            gradeRepo.save(new Grade(null,finalStudent,courseOffering));



        });

        PaymentScall scall = paymentScallRepo.findByDepartment(student.getDepartment()).orElseThrow(() -> new ApiException("scall not found"));
        Payment payment = new Payment();
        payment.setPayment(calculateMonthPayment(student)+scall.getRegistrationFee());
        payment.setMonth(1);
        payment.setStudent(student);
        payment.setAcademicYear(EthiopianCalendar.ethiopianYear());
        payment.setSem(student.getCurrentSem());
        payment.setOfficerStatus(FinanceOfficerStatus.PENDING);


        Payment savedPayment =paymentRepo.save(payment);




        return Map.of(
                "paymentDetail",savedPayment
        );
    }

    @Override
    public Map<String, Object> makePaymentAfterEnroll(String studentId) {

        Student student = studentRepo.findByUserUserId(studentId).
                orElseThrow(() -> new ApiException("student is not found"));
        CurrentSem currentSem = currentSemRepo.findTopByOrderByIdDesc().orElseThrow(() -> new ApiException("sem is not defined"));
          List<Payment>  payments = paymentRepo.findByStudentAndSemAndAcademicYearOrderByMonthAsc(student,student.getCurrentSem(),EthiopianCalendar.ethiopianYear());
          int month = 0;
          for(Payment payment: payments){
              if(month<payment.getMonth())
                  month=payment.getMonth();
          }


          Payment payment =Payment.builder().student(student).academicYear(EthiopianCalendar.ethiopianYear()).sem(currentSem.getCurrentSem()).month(month+1).payment(calculateMonthPayment(student)).officerStatus(FinanceOfficerStatus.PENDING).build();

          Payment savedPayment =paymentRepo.save(payment);


        return Map.of("paymentDetail",savedPayment);

    }

    @Override
    public Map<String,Object> monthlyReport() {

        List<CurrentSem> currentSem =currentSemRepo.findAll();

        CurrentSem sem=currentSem.get(0);
        if(sem.getCurrentSem()==1){
            List<MonthlyPaymentReportDTO>  reports = paymentRepo.getMonthlyReport(EthiopianCalendar.ethiopianYear(),1);
            return Map.of(
                 "firstSemester",   reports,
                    "secondSemster", List.of(),
                    "thirdSemster", List.of()
                    );




        }
        else if(sem.getCurrentSem()==2){
            List<MonthlyPaymentReportDTO>  reportsSem1= paymentRepo.getMonthlyReport(EthiopianCalendar.ethiopianYear(),1);
            List<MonthlyPaymentReportDTO>  reportsSem2= paymentRepo.getMonthlyReport(EthiopianCalendar.ethiopianYear(),1);

            return Map.of(
                    "firstSemester",   reportsSem1,
                    "secondSemster", reportsSem2,
                    "thirdSemster", List.of()
            );




        }

        else {

            List<MonthlyPaymentReportDTO>  reportsSem1= paymentRepo.getMonthlyReport(EthiopianCalendar.ethiopianYear(),1);
            List<MonthlyPaymentReportDTO>  reportsSem2= paymentRepo.getMonthlyReport(EthiopianCalendar.ethiopianYear(),2);
            List<MonthlyPaymentReportDTO>  reportsSem3= paymentRepo.getMonthlyReport(EthiopianCalendar.ethiopianYear(),3);

            return Map.of(
                    "firstSemester",   reportsSem1,
                    "secondSemster", reportsSem2,
                    "thirdSemster", reportsSem3

            );


        }



    }

    //finance Admin
    @Override
    public void updateFinanceOfficerStatus(Long officerId) {
       int x = paymentRepo.updateOfficerStatusToChecked(officerId);
        if(x==0)
         throw new ApiException("not updated");

        paymentRepo.updateOfficerStatusToChecked(officerId);
    }

    //finance admin and owner
    @Override
    public List<FinanceOfficerPaymentSummaryResponse> getFinanceOfficerWithPendingStatus() {
        return paymentRepo.getPendingPaymentSummary();
    }

    private int getUnpaidMonths(Student student){


       List<Payment> payments = paymentRepo.findByStudentAndSemAndAcademicYearOrderByMonthAsc(student,student.getCurrentSem(),2018);
        int month = 0;
        for(Payment payment: payments){
            if(month<payment.getMonth())
                month=payment.getMonth();
        }
       if(student.getCurrentSem() == 3){
            return 2-month;
        }
        return 4-month;
    }

    private int calculateMonthPayment(Student student){
 PaymentScall  scall = paymentScallRepo.findByDepartment(student.getDepartment()).orElseThrow(() -> new ApiException("payment not defined"));
int months;
int totalCHR = 0;
 if(student.getCurrentSem() == 3){
     months = 2;

 }
 else {
     months = 4;
 }
     List<CourseOffering> courseOfferings = courseOfferingRepo.findByAcademicYearAndStudyYearAndSemAndSectionAndProgramAndDepartment(
             2018,
             student.getCurrentYear(),
             student.getCurrentSem(),
             student.getSection(),
             student.getProgram(),
             student.getDepartment()
             );

     for(CourseOffering offering: courseOfferings){
         totalCHR = offering.getCourse().getCreditHour()+totalCHR;
     }

     return totalCHR*scall.getTuitionPerChr()/months;

    }



}


