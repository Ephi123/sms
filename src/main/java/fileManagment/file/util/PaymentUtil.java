package fileManagment.file.util;

import fileManagment.file.constant.Constant;
import fileManagment.file.domain.Payment;
import fileManagment.file.entity.*;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PaymentUtil {
    public static StudentFeeEntity generateStudentFee(GradeEntity grade, int payment) {
        return StudentFeeEntity.builder()
                .academicYear(Constant.ACADEMIC_YEAR)
                .monthFee(payment)
                .grade(grade)
                .build();
    }

    public static Payment paymentBuilder(int month, double charge, int payment) {


        switch (month) {
            case 1 -> {
                return Payment.builder().payment(payment).charge(charge).month("Meskrem").total(charge + payment).build();
            }

            case 2 -> {
                return Payment.builder().payment(payment).charge(charge).month("Tikmit").total(charge + payment).build();
            }
            case 3 -> {
                return Payment.builder().payment(payment).charge(charge).month("Hidar").total(charge + payment).build();
            }
            case 4 -> {
                return Payment.builder().payment(payment).charge(charge).month("Tahsas").total(charge + payment).build();
            }
            case 5 -> {
                return Payment.builder().payment(payment).charge(charge).month("Tir").total(charge + payment).build();
            }
            case 6 -> {
                return Payment.builder().payment(payment).charge(charge).month("Yekatit").total(charge + payment).build();
            }
            case 7 -> {
                return Payment.builder().payment(payment).charge(charge).month("Megabit").total(charge + payment).build();
            }
            case 8 -> {
                return Payment.builder().payment(payment).charge(charge).month("Miyazya").total(charge + payment).build();
            }
            case 9 -> {
                return Payment.builder().payment(payment).charge(charge).month("Ginbot").total(charge + payment).build();
            }
            case 10 -> {
                return Payment.builder().payment(payment).charge(charge).month("Senea").total(charge + payment).build();
            }
            default -> {
                return null;
            }

        }
    }


    public static Map<?,?> getFeeByMonth(PenaltyEntity penalty, DateTime currentDate, int month, int nextMonth, int monthPayment, UserEntity student){

        double charge = 0;
        double chargePerMonth=0;
        double chargePerDay = 0;
        int i = nextMonth;

        List<Payment> list = new ArrayList<>();

        for(; i < month+nextMonth; i++){
              if(i < currentDate.getMonthOfYear()) {
                  chargePerMonth = penalty.getPenalty() / 100 * monthPayment * (30 - penalty.getPenalty_after());
              }else if(i==currentDate.getMonthOfYear()){
                  int reamingDay = currentDate.getDayOfMonth() - penalty.getPenalty_after();
                  if(reamingDay>0) {
                      chargePerDay = reamingDay * penalty.getPenalty() / 100 * monthPayment;
                      list.add(paymentBuilder(i,chargePerDay,monthPayment));
                  }
              }
              else {
                  chargePerMonth = 0;
                  list.add(paymentBuilder(i,chargePerMonth,monthPayment));
                  charge+=chargePerMonth;
              }


        }

        charge += chargePerDay;
        double total = month*monthPayment + charge;

        return Map.of("payment",list,"total",total,"studentFullName",student.getFirstName() +" "+ student.getLastName(),"studentId",student.getUserId());

    }

    public static Map<?,?> getTotalPayment(PenaltyEntity penalty, DateTime currentDate,int nextMonth, int monthPayment){
        double charge = 0;
        double chargePerMonth=0;
        double chargePerDay = 0;
        int i = nextMonth;

        List<Payment> list = new ArrayList<>();

        for(; i < currentDate.getMonthOfYear(); i++){
            chargePerMonth = penalty.getPenalty()/100 * monthPayment * (30-penalty.getPenalty_after());
            list.add(paymentBuilder(i,chargePerMonth,monthPayment));
            charge+=chargePerMonth;
        }
        int reamingDay = currentDate.getDayOfMonth() - penalty.getPenalty_after();
        if(reamingDay>0) {
            chargePerDay = reamingDay * penalty.getPenalty() / 100 * monthPayment;
            list.add(paymentBuilder(i,chargePerDay,monthPayment));
        }
        charge += chargePerDay;
        double total = i*monthPayment + charge;

        return Map.of("payment",list,"total",total,"monthNum",list.size());

    }

   public static   PaymentCollection paymentCollectionBuilder(UserEntity student, double total) {
         var paymentCollection = PaymentCollection.builder()
                .isChecked(false)
                .academicYear(Constant.ACADEMIC_YEAR)
                .payment(total)
                .build();
            paymentCollection.addToStudent(student);

            return paymentCollection;
    }
}