package fileManagment.file.responseDto;

import lombok.*;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
public class ResultResponse {
    private String firstName;
    private String lastName;
    private String userId;
    private Double mark;
    private Long id;
    private Long assessmentId;
    private String assessmentName;
    private Integer assessmentWight;


}