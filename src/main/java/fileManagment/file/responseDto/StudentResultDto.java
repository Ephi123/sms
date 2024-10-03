package fileManagment.file.responseDto;

import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class StudentResultDto {
 private String userId;
 private String fName;
 private String lName;
 private String sec;
 private Integer sem;
 private Integer year;
 private Double average;
 private Long std_rank;

}
