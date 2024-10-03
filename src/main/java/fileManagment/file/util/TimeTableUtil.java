package fileManagment.file.util;
import fileManagment.file.entity.*;

import static fileManagment.file.constant.Constant.ACADEMIC_YEAR;

public class TimeTableUtil {
   public static TimeTableEntity timeTableGenerator(DayEntity day, PeriodEntity period, UserEntity teacher, SectionEntity section){
       return TimeTableEntity.builder()
               .academicYear(ACADEMIC_YEAR)
               .day(day)
               .period(period)
               .section(section)
               .teacher(teacher)
               .build();
   }
}
