package fileManagment.file.domain;

import org.joda.time.DateTime;
import org.joda.time.chrono.EthiopicChronology;

public class EthiopianCalendar {
    public static int ethiopianYear(){

        DateTime dateTime =  DateTime.now();
        DateTime   ethDate =dateTime.withChronology(EthiopicChronology.getInstance());

        return ethDate.getYear();
    }

    public static  String ethiopianDate(){
        DateTime dateTime =  DateTime.now();
        DateTime   ethDate =dateTime.withChronology(EthiopicChronology.getInstance());

        return ethDate.getYear()+"-"+ethDate.getMonthOfYear()+"-"+ethDate.getDayOfMonth();

    }
}
