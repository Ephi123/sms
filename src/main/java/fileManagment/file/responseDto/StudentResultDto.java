package fileManagment.file.responseDto;

import lombok.*;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
public class StudentResultDto {
 private String userId;
 private String fName;
 private String lName;
 private String sec;
 private Integer sem;
 private Integer year;
 private Double average;
 private Long std_rank;
 private Double cumulativeAvg;
 private Long cumulativeRank;
 private Long rankFromAll;
}