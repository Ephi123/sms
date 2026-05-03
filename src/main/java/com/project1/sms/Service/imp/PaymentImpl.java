package com.project1.sms.Service.imp;

import com.project1.sms.Service.PaymentService;
import com.project1.sms.apiException.ApiException;
import com.project1.sms.domain.EthiopianCalendar;
import com.project1.sms.dto.MonthlyPaymentReportDTO;
import com.project1.sms.model.*;
import com.project1.sms.repository.*;
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

    @Override
    public Map<String, Object> getPaymentDetailOfStudent(String userId) {

        Student  student = studentRepo.findByUserUserId(userId).
                orElseThrow(()-> new ApiException("Student Not Found"));

    Enrollment enrollment =enrollRepo.getEnrolledStudent(
                2018,
                student.getCurrentSem(),
                student.getDepartment().getDepName(),
                student.getCurrentYear(),
                student.getProgram().getName(),
                student.getSection().getSection(),
                userId
        ).orElse(null);

    if(enrollment != null){
            Payment payment = paymentRepo.findByStudentAndSemAndAcademicYear(student,student.getCurrentSem(),2018).orElseThrow(() -> new ApiException("student not paid"));

        return Map.ofEntries( Map.entry("fullName", enrollment.getStudent().getFirstName()+enrollment.getStudent().getMidlName()),
               Map.entry( "department",enrollment.getStudent().getDepartment().getDepName()),
              Map.entry(  "Year",enrollment.getStudent().getCurrentYear().toString()),
                Map.entry("program",enrollment.getCourseOffering().getProgram().getName().getLabel()),
                Map.entry("academicYear",enrollment.getCourseOffering().getAcademicYear().toString()),
                Map.entry("paymentDetail",payment),
                Map.entry("UnpaidMonths",getUnpaidMonths(student) == 0?"Completed":getUnpaidMonths(student)),
                Map.entry("mothPayment",getUnpaidMonths(student) == 0?"":calculateMonthPayment(student)),
               Map.entry( "totalUnpaidFee",getUnpaidMonths(student) == 0? "" : getUnpaidMonths(student) * calculateMonthPayment(student)),
                Map.entry("url",getUnpaidMonths(student) == 0?"" :"url to pay with student Id")



        );


    }



        return Map.ofEntries( Map.entry("fullName", student.getFirstName()+ " " + student.getMidlName()),
                Map.entry("department",student.getDepartment().getDepName()),
              Map.entry(  "Year",student.getCurrentYear().toString()),
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
        Student student = studentRepo.findByUserUserId(studentId).
                orElseThrow(() -> new ApiException("student is not found"));
        List<CourseOffering> offerings =courseOfferingRepo.findByAcademicYearAndStudyYearAndSemAndSectionAndProgramAndDepartment(
                EthiopianCalendar.ethiopianYear(),
                student.getCurrentYear(),
                student.getCurrentSem(),
                student.getSection(),
                student.getProgram(),
                student.getDepartment()
        );
        if(offerings == null)
            throw new ApiException("course not found");
        offerings.forEach(courseOffering -> {
            Enrollment enrollment = new Enrollment();
            enrollment.setStudent(student);
            enrollment.setCourseOffering(courseOffering);
             enrollRepo.save(enrollment);

        });

        PaymentScall scall = paymentScallRepo.findByDepartment(student.getDepartment()).orElseThrow(() -> new ApiException("scall not found"));
        Payment payment = new Payment();
        payment.setPayment(calculateMonthPayment(student)+scall.getRegistrationFee());
        payment.setMonth(1);
        payment.setStudent(student);
        payment.setAcademicYear(EthiopianCalendar.ethiopianYear());
        payment.setSem(student.getCurrentSem());

        Payment savedPayment =paymentRepo.save(payment);




        return Map.of(
                "paymentDetail",savedPayment
        );
    }

    @Override
    public Map<String, Object> makePaymentAfterEnroll(String studentId) {

        Student student = studentRepo.findByUserUserId(studentId).
                orElseThrow(() -> new ApiException("student is not found"));
          Payment  payment = paymentRepo.findByStudentAndSemAndAcademicYear(student,student.getCurrentSem(),2018).orElseThrow(() -> new ApiException("mot paid"));

          payment.setMonth(payment.getMonth()+1);

          Payment savedPayment =paymentRepo.save(payment);


        return Map.of("paymentDetail",savedPayment);

    }

    @Override
    public Map<String,Object> monthlyReport() {

        List<CurrentSem> currentSem =currentSemRepo.findAll();

        CurrentSem sem=currentSem.getFirst();
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

    private int getUnpaidMonths(Student student){


       Payment payment = paymentRepo.findByStudentAndSemAndAcademicYear(student,student.getCurrentSem(),2018).orElseThrow(()->new ApiException("student is not paid"));
        if(student.getCurrentSem() == 3){
            return 3-payment.getMonth();
        }
        return 4-payment.getMonth();
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


