package com.jallAssessment.JallAssessment.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PhoneDTO {
    private String ddd;
    private String number;
}
