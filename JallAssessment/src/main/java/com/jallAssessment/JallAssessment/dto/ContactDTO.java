package com.jallAssessment.JallAssessment.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ContactDTO {
    public String userId;
    private String name;
    private String surname;
    private String birthday;
    private List<PhoneDTO> phones;
    private String relative;
}
