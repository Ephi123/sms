package com.project1.sms.requestDTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@JsonIgnoreProperties(ignoreUnknown = true)
@Setter
@Getter
@ToString
public class AssessmentRequest {
    private Long offeringId;
    private String title;
    private Integer weight;

}
