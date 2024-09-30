package fileManagment.file;

import fileManagment.file.domain.EthiopianCalendar;
import fileManagment.file.domain.Payment;
import org.joda.time.DateTime;
import org.joda.time.chrono.EthiopicChronology;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class Demo {
         public static void main(String [] args) {
             int nextMonth = 2;
             int chargeAfter = 10;
             int year = EthiopianCalendar.ethiopianYear();

           //  System.out.println(DateTime.now().withChronology(EthiopicChronology.getInstance()));

         DateTime currentDate = DateTime.parse(year+"-7"+"-11");
         double charge = 0;
         double chargePerMonth=0;
         double chargePerDay = 0;
         int month = nextMonth;

         List<Payment> list = new ArrayList<>();
         for(; month<currentDate.getMonthOfYear(); month++){
             chargePerMonth = 0.5/100 * 1000 * 20;
             list.add(meth(month,chargePerMonth));
             charge+=chargePerMonth;
         }

         int reamingDay = currentDate.getDayOfMonth() - chargeAfter;
         if(reamingDay>0) {
             chargePerDay = reamingDay * 0.5 / 100 * 1000;

             list.add(meth(month,chargePerDay));
         }
         charge += chargePerDay;

             System.out.println(DateTime.now(EthiopicChronology.getInstance()));

         }

     private static Payment meth(int month, double charge){
             switch (month){
                 case 1-> {
                     return Payment.builder().payment(1000.0).charge(charge).month("1").total(charge+ 1000.0).build();
                 }

                 case 2-> {
                     return Payment.builder().payment(1000.0).charge(charge).month("2").total(charge+ 1000.0).build();
                 }
                 case 3-> {
                     return Payment.builder().payment(1000.0).charge(charge).month("3").total(charge+ 1000.0).build();
                 }
                 case 4-> {
                     return Payment.builder().payment(1000.0).charge(charge).month("4").total(charge+ 1000.0).build();
                 }
                 case 5-> {
                     return Payment.builder().payment(1000.0).charge(charge).month("5").total(charge+ 1000.0).build();
                 }
                 case 6-> {
                     return Payment.builder().payment(1000.0).charge(charge).month("6").total(charge+ 1000.0).build();
                 }
                 case 7-> {
                     return Payment.builder().payment(1000.0).charge(charge).month("7").total(charge+ 1000.0).build();
                 }
                 case 8-> {
                     return Payment.builder().payment(1000.0).charge(charge).month("8").total(charge+ 1000.0).build();
                 }
                 case 9-> {
                     return Payment.builder().payment(1000.0).charge(charge).month("9").total(charge+ 1000.0).build();
                 }
                 default -> {
                     return null;
                 }

             }
     }
}



