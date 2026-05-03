package com.project1.sms.requestDTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@JsonIgnoreProperties(ignoreUnknown = true)
@Setter
@Getter
@ToString
public class CourseOfferingRequest {
    private String courseCode;
    private String department;
    private String program;
    private Integer year;
    private Integer semester;
    private Long sectionId;

}
